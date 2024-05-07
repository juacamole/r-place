import './components/styling/App.css'
import {Route, Routes, useNavigate} from "react-router-dom";
import RegisterPage from "./components/pages/RegisterPage.tsx";
import {useState} from "react";
import RegisterPage2 from "./components/pages/RegisterPage2.tsx";
import LoginPage from "./components/pages/LoginPage.tsx";
import 'bootstrap/dist/css/bootstrap.min.css';
import HomePage from "./components/pages/HomePage.tsx";
import {UserDataType} from "./components/models/model.tsx";
import Settings from "./components/pages/Settings.tsx";

export type MessageType = {
    "token": string;
    "canvas": string;
}

function App() {

    const navigate = useNavigate()

    const [userData, setUserData] = useState<UserDataType>({
        "username": "",
        "password": "",
        "email": "",
        "role": "",
        "biography": "",
        "placedpixels": 0
    })

    return (
        <><Routes>
            <Route path={"/settings"} element={<Settings/>}/>
            <Route path={"/home"}
                   element={<HomePage navigate={navigate} setUserData={setUserData} userData={userData}/>}/>
            <Route path={"/"} element={<LoginPage setUserData={setUserData} userData={userData} navigate={navigate}/>}/>
            <Route path={"/register"}
                   element={<RegisterPage userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
            <Route path={"/register/2"}
                   element={<RegisterPage2 userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
        </Routes>

        </>
    )
}

export default App
