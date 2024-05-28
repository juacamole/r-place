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
        <img src={Logo} className={"logo"} alt={""}/>
        <div className={"login-tag"}>Login</div>

        <form onSubmit={(e) => {
            e.preventDefault();

            axios.post("/place/authenticate",
                loginData).then((res: AxiosResponse<jwtResponseType>) => {
                console.log(res)
                console.log("gud cred")
                if (res) {
                    localStorage.setItem("jwt", res.data.token);
                    navigate("/home")
                }

            }).catch((error) => {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log("Wrong credentials");
                alert("Wrong Credentials");
            });
        }}>
            <input id={"login-email-input"} required={true} placeholder={"email"} value={loginData.email} name={"email"}
                   className={"email-input"} type={"email"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input id={"login-username-input"} required={true} value={loginData.username} placeholder={"username"}
                   name={"username"}
                   className={"username-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <input id={"login-password-input"} required={true} placeholder={"password"} type={"password"}
                   name={"password"} className={"password-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <button id={"login-button"} type={"submit"}>Login</button>
        </form>
        <div id={"switch-to-register-button"}/>
        <div id={"switch-to-register-button-hitbox"} onClick={() => navigate("/register")}/>
        <p id={"switch-to-register-button-tag"}>Register</p>
        <CircleDesign/>
    </>
}