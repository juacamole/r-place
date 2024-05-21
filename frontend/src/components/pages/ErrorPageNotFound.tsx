import CircleDesign from "../design-components/CircleDesign.tsx";
import Logo from "../../assets/coop place logo.png";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

export default function ErrorPageNotFound() {

    const navigate = useNavigate();
    const [counter, setCounter] = useState<number>(5);

    useEffect(() => {
        const interval = setInterval(() => {
            setCounter((prevCounter) => {
                if (prevCounter > 1) {
                    return prevCounter - 1;
                } else {
                    navigate("/home");
                    clearInterval(interval);
                    return 0;
                }
            });
        }, 1000);

        return () => clearInterval(interval);
    }, [navigate]);

    return <div>
        <img src={Logo} className={"logo"} alt={undefined}/>
        <div className={"main-panel"}>
            <h1>Page Not Found</h1>
            <h2 className={"return-paragraph"}>You will be Redirected to our main page in {counter} seconds</h2>
        </div>
        <CircleDesign/>
    </div>
}