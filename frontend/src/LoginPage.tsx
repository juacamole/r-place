import Logo from "./assets/coop place logo.png"
import {useState} from "react";
import {UserDataType} from "./App.tsx";
import axios from "axios";



export default function LoginPage(){

    const [userData, setUserData] = useState<UserDataType>({
        "username": "",
        "password": "",
        "email": ""
    })

    const saveUserDataOnChange = (name: string, value: string) => {
        const newUserData = {
            ...userData,
            [name]: value
        }
        setUserData(newUserData)
    }

    return <>
    <img src={Logo} className={"logo"}/>
        <div id={"login-tag"}>Register</div>
        <form onSubmit={(e) => {axios.post("/place/home",
            userData);
            e.preventDefault();
        }}>
            <input placeholder={"e-mail"} type={"email"} name={"email"} className={"email-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input placeholder={"username"} name={"username"} className={"username-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input placeholder={"password"} type={"password"} name={"password"} className={"password-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <button type={"submit"}>Login</button>
        </form>

    </>

}