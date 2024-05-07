import CircleDesign from "../design-components/CircleDesign.tsx";
import Logo from "../../assets/coop place logo.png";

export default function Settings() {
    return <>
        <img src={Logo} className={"logo"} alt={""}/>
        <CircleDesign></CircleDesign>
    </>
}