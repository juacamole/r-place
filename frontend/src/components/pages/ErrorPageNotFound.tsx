import {useEffect} from "react";
import {useNavigate} from "react-router-dom";

export default function ErrorPageNotFound() {
    const navigate = useNavigate()
    useEffect(() => {
        console.log("Page not found")
        navigate("/home");
    }, []);
    return <div>
    </div>
}