import Logo from "../../assets/coop place logo.png";
import axios from "axios";
import {UserDataType} from "../models/model.tsx";


type LoginPageProps = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
}

export default function LoginPage({setUserData, userData}: LoginPageProps){

    const saveUserDataOnChange = (name: string, value: string) => {
        const newUserData = {
            ...userData,
            [name]: value
        }
        setUserData(newUserData)
    }

    return <>
        <img src={Logo} className={"logo"}/>
        <div className={"login-tag"}>Login</div>

        <form onSubmit={(e) => {axios.post("/place/login",
            userData);
            e.preventDefault();
        }}>
            <input id={"login-username-input"} placeholder={"username"} name={"username"} className={"username-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input id={"login-password-input"} placeholder={"password"} type={"password"} name={"password"} className={"password-input"} onChange={(e) => {
            saveUserDataOnChange(e.target.name, e.target.value)
        }}/>
            <button id={"login-button"} type={"submit"}>Login</button>
        </form>
    </>
}