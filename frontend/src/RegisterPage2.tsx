import Logo from "./assets/coop place logo.png";
import axios, {AxiosResponse} from "axios";
import {UserDataType} from "./App.tsx";
import CircleDesign from "./CircleDesign.tsx";

type RegisterPage2Props = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
}

export default function RegisterPage2({setUserData, userData}: RegisterPage2Props){


    const saveUserDataOnChange = (name: string, value: string) => {
        const newUserData = {
            ...userData,
            [name]: value
        }
        setUserData(newUserData)
    }

    return <>
        <img src={Logo} className={"logo"}/>
        <div className={"register-tag"}>Register</div>
        <form onSubmit={(e) => {axios.post("/place/register",
            userData).then((u: AxiosResponse<UserDataType>) => {
                const newUser: UserDataType = u.data;
                setUserData(newUser);
        });
            e.preventDefault();
        }}>
            <input placeholder={"username"} name={"username"} className={"username-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input placeholder={"password"} type={"password"} name={"password"} className={"password-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <button id={"next-button2"} type={"submit"}>Next</button>
        </form>
        <CircleDesign/>
    </>
}