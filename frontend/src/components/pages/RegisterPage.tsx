import Logo from "../../assets/coop place logo.png"
import Red_X from "../../assets/red_fn.png"
import Green_Checkmark from "../../assets/green_fn.png";

import CircleDesign from "../design-components/CircleDesign.tsx";
import {NavigateFunction} from "react-router-dom";
import {UserDataType} from "../models/model.tsx";
import React, {useState} from "react";
import axios from "axios";
import {Role} from "./Settings.tsx";

type RegisterPageProps = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
    navigate: NavigateFunction
}

export default function RegisterPage({setUserData, userData, navigate}: RegisterPageProps) {
    const [error, setError] = useState<boolean>(false);
    const saveUserDataOnChange = (name: string, value: string) => {
        const newUserData = {
            ...userData,
            [name]: value
        }
        setUserData(newUserData)
    }


    const handleCheckEmail = (value: string) => {
        const user: UserDataType = {
            "username": "",
            "password": "",
            "email": value,
            "role": Role.USER,
            "biography": "",
            "placedPixels": 0,
            "cpx": 0,
            "cpy": 0,
            "cpd": false
        }
        axios.post("/place/mailcheck", user).then((e) => {
                if (e.data) {
                    setError(true)
                } else {
                    setError(false)
                }
            }
        );
    }

    return <div className={"background"}>
        <img src={Logo} className={"logo"} alt={""}/>
        <div className={"register-tag"}>Register</div>
        <form onSubmit={() => {
            axios.post("/place/mailcheck", userData).then((e) => {

                    if (e.data) {
                        alert("Account with Email already exists")
                        setError(true)
                    } else {
                        navigate("/register/2");
                    }
                }
            );
        }}>
            <input placeholder={"E-Mail Adresse"} required={true} type={"email"} name={"email"}
                   className={"email-input"} onChange={(e) => {
                e.preventDefault();
                saveUserDataOnChange(e.target.name, e.target.value)
                handleCheckEmail(e.target.value)
            }}/>
            <button id={"next-button"} type={"submit"}>Next</button>
        </form>
        <div id={"switch-to-login-button"}/>
        <p id={"switch-to-login-button-tag"}>Login</p>
        <div id={"switch-to-login-button-hitbox"} onClick={() => navigate("/")}></div>
        {error && <img id={"red-x"} src={Red_X} alt={""}/>}
        {!error && userData.email.length > 0 && <img id={"green-checkmark1"} src={Green_Checkmark} alt={""}/>}
        <CircleDesign/>
    </div>

}