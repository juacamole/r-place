import './App.css'
import {Route, Routes, useNavigate} from "react-router-dom";
import RegisterPage from "./RegisterPage.tsx";
import {useState} from "react";
import RegisterPage2 from "./RegisterPage2.tsx";
import LoginPage from "./LoginPage.tsx";
import 'bootstrap/dist/css/bootstrap.min.css';
import HomePage from "./HomePage.tsx";
import CanvasTestComponent from "./CanvasTestComponent.tsx";
import FractionParser from "./FractionParser.tsx";




export type UserDataType = {
    "username": string;
    "password": string;
    "email": string;
    "role": string;
    "biography": string;
    "placedpixels": number;
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
        <Route path={"/fractest"} element={<FractionParser/>}/>
        <Route path={"/ctest"} element={<CanvasTestComponent/>}/>
        <Route path={"/home"} element={<HomePage navigate={navigate} setUserData={setUserData} userData={userData}/>}/>
        <Route path={"/"} element={<LoginPage setUserData={setUserData} userData={userData}/>}/>
        <Route path={"/register"} element={<RegisterPage userData={userData} setUserData={setUserData} navigate={navigate}/>}/>
        <Route path={"/register/2"} element={<RegisterPage2 userData={userData} setUserData={setUserData}/>}/>
    </Routes>

    </>
  )
}

export default App
