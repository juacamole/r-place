import CircleDesign from "../design-components/CircleDesign.tsx";
import Logo from "../../assets/coop place logo.png";
import React, {useEffect, useState} from "react";
import axios, {AxiosResponse} from "axios";
import {UserDataType} from "../models/model.tsx";
import {useNavigate} from "react-router-dom";

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
    "placedpixels": number;
    "isAccountNonExpired": boolean;
    "isAccountNonLocked": boolean;
    "isCredentialsNonExpired": boolean;
    "isEnabled": boolean;
    "cpx": number;
    "cpy": number;
}

type SettingsProps = {
    ColorPickerDraggable: boolean;
    setColorPickerDraggable: React.Dispatch<React.SetStateAction<boolean>>;
}


export default function Settings({ColorPickerDraggable, setColorPickerDraggable}: SettingsProps) {
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
    })

    useEffect(() => {
        handleGetUser();
    }, []);

    const handleGetUser = () => {
        axios.get("/user/getuser", {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem("jwt")}`
                }
            }
        ).then((res: AxiosResponse<ExpectedResponseType>) => {
            setUserData({
                "username": res.data.username,
                "password": "",
                "email": res.data.email,
                "role": res.data.role,
                "biography": res.data.biography,
                "placedPixels": res.data.placedpixels,
                "cpx": res.data.cpx,
                "cpy": res.data.cpy
            });
        })
    }

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        axios.post("/user/update", userdata, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        })
            .then((res: AxiosResponse<boolean>) => {
                setUpdateStatus(res.data);
                if (res.data) {
                    navigate("/home")
                }
            });
    }

    const handleChange = (name: string, value: string, e: React.ChangeEvent) => {
        e.preventDefault();
        setUserData(prevState => ({
            ...prevState,
            [name]: value
        }))
    }

    const handleClick = () => {
        navigate("/home");
    }

    return <>
        <img src={Logo} className={"logo"} alt={""}/>
        <div id={"setting-main-panel"}>
            <form onSubmit={handleSubmit}>
                <input name={"username"} maxLength={16} id={"edit-username"} disabled={true}
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
                <textarea maxLength={40} name={"biography"} id={"edit-biography"} placeholder={"edit biography"}
                          defaultValue={userdata.biography}
                          onChange={(e) => {
                              handleChange(e.target.name, e.target.value, e)
                          }}/>
                <input disabled={true} id={"placedpixel-display"}
                       value={"placed Pixels: " + userdata.placedPixels}/>
                <button id={"save-changes"} type={"submit"}>Save Changes</button>
            </form>
            <button id={"back-button"} onClick={handleClick}>Back</button>
            <input type={"checkbox"} checked={ColorPickerDraggable} onChange={() => {
                setColorPickerDraggable(!ColorPickerDraggable);
            }}/>
            {!updateStatus && <p id={"error-message"}>Update Failed</p>}
        </div>
        <CircleDesign></CircleDesign>
    </>
}