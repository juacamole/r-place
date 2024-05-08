import Logo from "../../assets/coop place logo.png";
import axios, {AxiosResponse} from "axios";
import {jwtResponseType} from "../models/model.tsx";
import CircleDesign from "../design-components/CircleDesign.tsx";
import {NavigateFunction} from "react-router-dom";
import {useState} from "react";


type LoginPageProps = {
    navigate: NavigateFunction
}

type LoginType = {
    email: string;
    username: string;
    password: string;
}

export default function LoginPage({navigate}: LoginPageProps) {

    const [loginData, setLoginData] = useState<LoginType>({
        "email": "",
        "username": "",
        "password": ""
    })

    const saveUserDataOnChange = (name: string, value: string) => {
        const newLoginData = {
            ...loginData,
            [name]: value
        }
        setLoginData(newLoginData)
    }

    return <>
        <img src={Logo} className={"logo"}/>
        <div className={"login-tag"}>Login</div>

        <form onSubmit={(e) => {
            axios.post("/place/authenticate",
                loginData).then((res: AxiosResponse<jwtResponseType>) => {
                if (res) {
                    localStorage.setItem("jwt", res.data.token);
                    navigate("/home")
                }
            })
            e.preventDefault();
        }}>
            <input id={"login-email-input"} required={true} placeholder={"email"} name={"email"}
                   className={"email-input"} type={"email"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input id={"login-username-input"} required={true} placeholder={"username"} name={"username"}
                   className={"username-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input id={"login-password-input"} required={true} placeholder={"password"} type={"password"}
                   name={"password"} className={"password-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <button id={"login-button"} type={"submit"}>Login</button>
        </form>
        <CircleDesign/>
    </>
}