import Logo from "../../assets/coop place logo.png"

import CircleDesign from "../design-components/CircleDesign.tsx";
import {NavigateFunction} from "react-router-dom";
import {UserDataType} from "../models/model.tsx";
import React from "react";
import axios from "axios";

type RegisterPageProps = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
    navigate: NavigateFunction
}

export default function RegisterPage({setUserData, userData, navigate}: RegisterPageProps){

    const saveUserDataOnChange = (name: string, value: string) => {
        const newUserData = {
            ...userData,
            [name]: value
        }
        setUserData(newUserData)
    }

    return <div className={"background"}>
    <img src={Logo} className={"logo"}/>
        <div className={"register-tag"}>Register</div>
        <form onSubmit={(e) => {
            e.preventDefault();
            console.log(userData.email)
            axios.post("/place/mailcheck", userData).then((e) => {

                if (e.data){
                    alert("Account with Email already exists")
                } else {
                    navigate("/register/2");
                }
            }

            );
        }}>
            <input placeholder={"E-Mail Adresse"} required={true} type={"email"} name={"email"} className={"email-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <button id={"next-button"} type={"submit"}>Next</button>
        </form>
        <CircleDesign/>
    </div>

}