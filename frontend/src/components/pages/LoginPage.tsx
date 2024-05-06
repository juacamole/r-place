import Logo from "../../assets/coop place logo.png";
import axios, {AxiosResponse} from "axios";
import {jwtResponseType, UserDataType} from "../models/model.tsx";


type LoginPageProps = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
}

export default function LoginPage({setUserData, userData}: LoginPageProps) {

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

        <form onSubmit={(e) => {
            axios.post("/place/authenticate",
                userData).then((e: AxiosResponse<jwtResponseType>) => {
                localStorage.setItem("jwt", e.data.token);
            })
            e.preventDefault();
        }}>
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
    </>
}