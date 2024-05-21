import './components/styling/Global.css'
import './components/styling/LoginPage.css'
import './components/styling/RegisterPages.css'
import './components/styling/HomePage.css'
import './components/styling/Settings.css'
import './components/styling/ErrorPages.css'


import {Navigate, Route, Routes, useNavigate} from "react-router-dom";
import RegisterPage from "./components/pages/RegisterPage.tsx";
import {useState} from "react";
import RegisterPage2 from "./components/pages/RegisterPage2.tsx";
import LoginPage from "./components/pages/LoginPage.tsx";
import 'bootstrap/dist/css/bootstrap.min.css';
import HomePage from "./components/pages/HomePage.tsx";
import {UserDataType} from "./components/models/model.tsx";
import Settings, {Role} from "./components/pages/Settings.tsx";
import ErrorPageNotFound from "./components/pages/ErrorPageNotFound.tsx";
import ErrorPageNotAllowed from "./components/pages/ErrorPageNotAllowed.tsx";
import axios from "axios";

export type MessageType = {
    "token": string;
    "canvas": string;
}


export const ProtectedRoute = ({children}: {
    children: JSX.Element;
}) => {
    const jwt = localStorage.getItem('jwt');
    const navigate = useNavigate()
    if (!jwt) {
        return <Navigate to="/403" replace/>;
    } else {
        axios.get("place/checktokenexpired", {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        })
            .then()
            .catch(error => {
                if (error.response && error.response.status === 403) {
                    localStorage.removeItem("jwt");
                    console.log("Token expired. JWT removed from local storage.");
                    navigate("/");
                } else {
                    console.error("An error occurred:", error);
                }
            });
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
        "cpy": 20,
        "cpd": false
    })

    return (
        <><Routes>
            <Route path={"*"} element={<ErrorPageNotFound/>}/>
            <Route path={"/settings"} element={<ProtectedRoute
                children={<Settings ColorPickerDraggable={ColorPickerDraggable}
                                    setColorPickerDraggable={setColorPickerDraggable}/>}/>}/>
            <Route path={"/home"} element={<ProtectedRoute
                children={<HomePage navigate={navigate} ColorPickerDraggable={ColorPickerDraggable}
                                    setColorPickerDraggable={setColorPickerDraggable}/>}/>}/>
            <Route path={"/"} element={<LoginPage navigate={navigate}/>}/>
            <Route path={"/register"}
                   element={<RegisterPage userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
            <Route path={"/register/2"}
                   element={<RegisterPage2 userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
            <Route path={"/403"} element={<ErrorPageNotAllowed/>}/>
        </Routes>

        </>
    )
}

export default App
