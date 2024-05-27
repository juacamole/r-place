import CircleDesign from "../design-components/CircleDesign.tsx";
import Logo from "../../assets/coop place logo.png";
import React, {useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios";
import {jwtResponseType, UserDataType} from "../models/model.tsx";
import {useNavigate} from "react-router-dom";

// eslint-disable-next-line react-refresh/only-export-components
export enum Role {
    ADMIN = "ADMIN",
    USER = "USER",
}

export type ExpectedResponseType = {
    "id": number;
    "username": string;
    "password": string;
    "email": string;
    "role": Role;
    "biography": string;
    "placedPixels": number;
    "isAccountNonExpired": boolean;
    "isAccountNonLocked": boolean;
    "isCredentialsNonExpired": boolean;
    "isEnabled": boolean;
    "cpx": number;
    "cpy": number;
    "cpd": boolean;
}

type SettingsProps = {
    ColorPickerDraggable: boolean;
    setColorPickerDraggable: React.Dispatch<React.SetStateAction<boolean>>;
}


export default function Settings({ColorPickerDraggable, setColorPickerDraggable}: SettingsProps) {
    const [advancedDel, setAdvancedDel] = useState<boolean>(false)
    const [password, setPassword] = useState<string>("");
    const navigate = useNavigate();
    const [updateStatus, setUpdateStatus] = useState<boolean>(true)
    const [userdata, setUserData] = useState<UserDataType>({
        "username": "",
        "password": "",
        "email": "",
        "role": Role.USER,
        "biography": "",
        "placedPixels": 0,
        "cpx": 0,
        "cpy": 0,
        "cpd": false
    })

    useEffect(() => {
            handleGetUser();
        }, /* eslint-disable */
        []);
    /* eslint-enable */

    const handleGetUser = () => {
        axios.get("/user/getuser", {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
            }
        ).then((res: AxiosResponse<ExpectedResponseType>) => {
            setColorPickerDraggable(res.data.cpd);
            setUserData({
                "username": res.data.username,
                "password": "",
                "email": res.data.email,
                "role": res.data.role,
                "biography": res.data.biography,
                "placedPixels": res.data.placedPixels,
                "cpx": res.data.cpx,
                "cpy": res.data.cpy,
                "cpd": res.data.cpd
            });
        })
    }

    const handleSubmit = () => {
        axios.put("/user/update", userdata, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        })
            .then((res: AxiosResponse<jwtResponseType>) => {
                localStorage.setItem("jwt", res.data.token)
                console.log(res.data.token)
                navigate("/home");
            }).catch(() => {
            setUpdateStatus(false)
        });
    }

    const handleChange = (name: string, value: string | boolean, e: React.ChangeEvent) => {
        e.preventDefault();
        setUserData(prevState => ({
            ...prevState,
            [name]: value
        }))
    }

    const handleClick = () => {
        navigate("/home");
    }

    const handleDeleteUser = () => {
        setAdvancedDel(true);
    }

    const handleDeleteUserAccepted = () => {
        axios.post("/user/testpw", password, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        }).then((res) => {
            if (res.data) {
                axios.delete("/user/delete", {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                    }
                }).then(() => {
                    localStorage.removeItem("jwt");
                    navigate("/");
                })
            }
        })
    }

    return <>
        <img src={Logo} className={"logo"} alt={""}/>
        <div id={"setting-main-panel"}>
            <div id={"setting-sub-panel"}>
                <form onSubmit={(e) => {
                    e.preventDefault();
                    handleSubmit()
                }}>
                    <input name={"username"} maxLength={16} id={"edit-username"}
                           placeholder={"new username"}
                           value={userdata.username}
                           onChange={(e) => {
                               handleChange(e.target.name, e.target.value, e)
                           }}/>
                    <input name={"email"} id={"email-display"} placeholder={"email"} type={"email"} disabled={true}
                           value={userdata.email}
                           onChange={(e) => {
                               handleChange(e.target.name, e.target.value, e)
                           }}/>
                    <textarea maxLength={30} name={"biography"} id={"edit-biography"} data-limit-rows="true" cols={1}
                              rows={2} placeholder={"edit biography"}
                              defaultValue={userdata.biography}
                              onChange={(e) => {
                                  handleChange(e.target.name, e.target.value, e)
                              }}/>
                    <input disabled={true} id={"placed-pixel-display"}
                           value={"placed Pixels: " + userdata.placedPixels}/>
                    <button id={"save-changes"} type={"submit"}>Save Changes</button>
                </form>
                <button id={"back-button"} onClick={handleClick}>Back</button>
                <div id={"cp-draggable-switch-parent"}>
                    <label className={"switch"}>
                        <input type={"checkbox"} checked={ColorPickerDraggable} onChange={() => {
                            setUserData(prevState => ({
                                ...prevState,
                                "cpd": !ColorPickerDraggable
                            }))
                            setColorPickerDraggable(!ColorPickerDraggable);
                        }}/>
                        <span className={"slider round"}></span>
                    </label>
                    <span id={"cp-draggable-span"}>Color Picker Draggable</span>
                </div>
                {!updateStatus && <p id={"error-message"}>Update Failed</p>}
                <button id={"delete-user-button"} onClick={(e) => {
                    e.preventDefault();
                    handleDeleteUser();
                }}>Delete user
                </button>
            </div>
        </div>
        {advancedDel && <div id={"delete-confirm-panel"}>
            <form onSubmit={(e) => {
                e.preventDefault();
                handleDeleteUserAccepted()
            }}>
                <input type={"password"} placeholder={"password"} onChange={(e) => {
                    setPassword(e.target.value);
                }}/>
            </form>
            <button onClick={() => {
                setAdvancedDel(false)
            }}>x
            </button>
        </div>}
        <CircleDesign></CircleDesign>
    </>
}