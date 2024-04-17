import CircleDesign from "./CircleDesign.tsx";
import {UserDataType} from "./App.tsx";
import {NavigateFunction} from "react-router-dom";
import React, {useRef, useState} from "react";
import Logo from "./assets/coop place logo.png";


type HomePageProps = {
    userData: UserDataType
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>
    navigate: NavigateFunction
}
export default function HomePage({userData, navigate}: HomePageProps) {
    
    const [currentColor, setCurrentColor] = useState<string>("")
    const textAreaRef = useRef<HTMLTextAreaElement>(null);

    const copyCurrentColor = () => {
        const textarea = textAreaRef.current;
        if (textarea){
        textarea.select();
        document.execCommand('copy');
        textarea.blur();
        }
    }

    return<>
        <img src={Logo} className={"logo"} alt={undefined}/>

        <div id={"home-background"}/>

        <div id={"user-profile"}>
            <div>{userData.username} {userData.role}</div>
            <p id={"user-biography"}>{userData.biography}</p>
            <button id={"user-settings"} onClick={() => {navigate("/settings")}}>Settings</button>
        </div>
        
        <div id={"color-picker-parent"}>
            <input id={"color-picker"} type={"color"} onChange={(e) => {
                setCurrentColor(e.target.value)
            }}/>
            <textarea ref={textAreaRef} id={"hex-display"} value={currentColor}></textarea>
            <button id={"copy-button"} onClick={() => {copyCurrentColor()}}>copy</button>
        </div>

            <div id={"home-circle-design-parent"}>
            <CircleDesign/>
            </div>
    </>
        }