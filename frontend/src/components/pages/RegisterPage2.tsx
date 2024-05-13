import Logo from "../../assets/coop place logo.png";
import axios, {AxiosResponse} from "axios";
import CircleDesign from "../design-components/CircleDesign.tsx";
import {jwtResponseType, UserDataType} from "../models/model.tsx";
import {NavigateFunction} from "react-router-dom";

type RegisterPage2Props = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
    navigate: NavigateFunction
}

export default function RegisterPage2({setUserData, userData, navigate}: RegisterPage2Props) {


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
        <form onSubmit={(e) => {
            axios.post("/place/register",
                userData).then((u: AxiosResponse<jwtResponseType>) => {
                localStorage.setItem('jwt', u.data.token);
                navigate("/home");
            });
            e.preventDefault();
        }}>
            <input placeholder={"username"} maxLength={16} name={"username"} className={"username-input"}
                   onChange={(e) => {
                       saveUserDataOnChange(e.target.name, e.target.value)
                   }}/>
            <input placeholder={"password"} type={"password"} maxLength={40} name={"password"}
                   className={"password-input"}
                   onChange={(e) => {
                       saveUserDataOnChange(e.target.name, e.target.value)
                   }}/>
            <button id={"next-button2"} type={"submit"}>Next</button>
        </form>
        <CircleDesign/>
    </>
}