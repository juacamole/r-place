import './App.css'
import {Route, Routes} from "react-router-dom";
import LoginPage from "./LoginPage.tsx";


export type UserDataType = {
    "username": string;
    "password": string;
    "email": string;
}
function App() {
  return (
    <><Routes>
      <Route path={"/"} element={<LoginPage/>}/>
    </Routes>

    </>
  )
}

export default App
