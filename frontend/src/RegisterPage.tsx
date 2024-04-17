import Logo from "./assets/coop place logo.png"

import {UserDataType} from "./App.tsx";
import CircleDesign from "./CircleDesign.tsx";
import {NavigateFunction} from "react-router-dom";

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
            navigate("/register/2");
        }}>
            <input placeholder={"E-Mail Adresse"} type={"email"} name={"email"} className={"email-input"} onChange={(e) => {
                saveUserDataOnChange(e.target.name, e.target.value)
            }}/>
            <button id={"next-button"} type={"submit"}>Next</button>
        </form>
        <CircleDesign/>
    </div>

}