import './components/styling/Global.css'
import './components/styling/LoginPage.css'
import './components/styling/RegisterPages.css'
import './components/styling/HomePage.css'
import './components/styling/Settings.css'


import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import RegisterPage from "./components/pages/RegisterPage.tsx";
import {useState} from "react";
import RegisterPage2 from "./components/pages/RegisterPage2.tsx";
import LoginPage from "./components/pages/LoginPage.tsx";
import 'bootstrap/dist/css/bootstrap.min.css';
import HomePage from "./components/pages/HomePage.tsx";
import {UserDataType} from "./components/models/model.tsx";
import Settings, {Role} from "./components/pages/Settings.tsx";

export type MessageType = {
    "token": string;
    "canvas": string;
}


export const ProtectedRoute = ({children}: {
    children: JSX.Element;
}) => {
    const jwt = localStorage.getItem('jwt');

    if (!jwt) {
        return <Navigate to="/" replace/>;
    }

    return children;
};

function App() {

    const navigate = useNavigate()
    const [ColorPickerDraggable, setColorPickerDraggable] = useState<boolean>(true);

    const [userData, setUserData] = useState<UserDataType>({
        "username": "",
        "password": "",
        "email": "",
        "role": Role.USER,
        "biography": "",
        "placedPixels": 0,
        "cpx": 20,
        "cpy": 20
    })

    return (
        <><Routes>
            <Route path={"/settings"} element={<ProtectedRoute
                children={<Settings ColorPickerDraggable={ColorPickerDraggable}
                                    setColorPickerDraggable={setColorPickerDraggable}/>}/>}/>
            <Route path={"/home"} element={<ProtectedRoute
                children={<HomePage navigate={navigate} userData={userData} ColorPickerDraggable={ColorPickerDraggable}
                                    setColorPickerDraggable={setColorPickerDraggable}/>}/>}/>
            <Route path={"/"} element={<LoginPage navigate={navigate}/>}/>
            <Route path={"/register"}
                   element={<RegisterPage userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
            <Route path={"/register/2"}
                   element={<RegisterPage2 userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
        </Routes>

        </>
    )
}

export default App
