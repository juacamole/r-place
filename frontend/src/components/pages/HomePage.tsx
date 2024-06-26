import CircleDesign from "../design-components/CircleDesign.tsx";
import {NavigateFunction} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import Logo from "../../assets/coop place logo.png";
import Settings from "../../assets/settings-icon.png";
import Logout from "../../assets/logout-icon.png";
import {WSService, WSServiceType} from "../../WSService.tsx";
import axios, {AxiosResponse} from "axios";
import {ExpectedResponseType, Role} from "./Settings.tsx";
import {UserDataType} from "../models/model.tsx";

type HomePageProps = {
    navigate: NavigateFunction;
    ColorPickerDraggable: boolean;
    setColorPickerDraggable: (state: boolean) => void;
};
const SCALE = 10
const DEFAULT_COOLDOWN = 15;
const DEFAULT_COLOR = "#000000"
const DEFAULT_OBJPOS = [30, 200]

export default function HomePage({navigate, ColorPickerDraggable, setColorPickerDraggable}: HomePageProps) {
    const [ws, setWs] = useState<WSServiceType>();
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const [currentColor, setCurrentColor] = useState<string>(DEFAULT_COLOR);
    const [consoleValue, setConsoleValue] = useState<number[]>([]);
    const [consoleTextValue, setConsoleTextValue] = useState<string>("")
    let cursorPos: number[] = [];
    const [objPos, setObjPos] = useState<number[]>(DEFAULT_OBJPOS);
    let ObjectPosition: number[] = [];
    const [draggable, setDraggable] = useState<boolean>(true);
    const [cooldown, setCooldown] = useState<number>(DEFAULT_COOLDOWN);
    const drawRequest = (ctx: CanvasRenderingContext2D, pixelX: number, pixelY: number) => {
        if (cooldown > 0 && userData.role == Role.USER) {
            return;
        }
        setCooldown(DEFAULT_COOLDOWN);
        const pixelSize = SCALE;
        ctx.fillStyle = currentColor;
        ctx.fillRect(pixelX * pixelSize, pixelY * pixelSize, pixelSize, pixelSize);
        canvasRef.current != null && ws && ws.updateCanvas({
            "token": localStorage.getItem("jwt") + "".toString(),
            "canvas": (canvasRef.current).toDataURL()
        });
    };

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

    useEffect(() => {
        const interval = setInterval(() => {
            setCooldown(prevCooldown => {
                return prevCooldown > 0 ? prevCooldown - 1 : 0;
            });
        }, 1000);
        return () => clearInterval(interval);
    }, []);


    useEffect(() => {
            if (localStorage.getItem("color")) {
                setCurrentColor(localStorage.getItem("color") + "")
            }
            getCurrentCooldown();
            setWs(WSService());
            getUser();
        }, /* eslint-disable */
        []);
    /* eslint-enable */

    const handleNewPos = () => {
        if (!ColorPickerDraggable) return;
        axios.post("/user/objpos", ObjectPosition, {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        }).then()
    }

    const getUser = () => {
        axios.get("/user/getuser", {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        }).then((res: AxiosResponse<ExpectedResponseType>) => {
                setUserData(res.data)
                setColorPickerDraggable(res.data.cpd);
                ObjectPosition = [res.data.cpx, res.data.cpy];
                setObjPos([res.data.cpx, res.data.cpy]);
                if (ColorPickerDraggable == res.data.cpd) {
                    setupEventListeners();
                }
            }
        )
    }

    const setupEventListeners = () => {
        const dragBtn = document.querySelector("#color-picker-parent");
        if (!dragBtn) return;

        const handleMouseMove = (e: MouseEvent) => {
            if (!ColorPickerDraggable) return;
            // eslint-disable-next-line react-hooks/exhaustive-deps
            cursorPos = [e.pageX, e.pageY];
            // eslint-disable-next-line react-hooks/exhaustive-deps
            ObjectPosition = cursorPos;
            setObjPos(cursorPos)
        }

        const registerMoveListener = () => {
            if (!ColorPickerDraggable) return;
            window.addEventListener("mousemove", handleMouseMove);
            window.addEventListener("mouseup", removeListener);
        };


        const removeListener = () => {
            if (!ColorPickerDraggable) return;
            handleNewPos();
            window.removeEventListener("mousemove", handleMouseMove);
            window.removeEventListener("mouseup", removeListener);
        }

        dragBtn.addEventListener("mousedown", registerMoveListener);


    }

    useEffect(() => {
        ws?.updateCanvas({
            "token": localStorage.getItem("jwt") + "".toString(),
            "canvas": ""
        })
    }, [ws]);


    const handleCanvasClick = (event: React.MouseEvent<HTMLCanvasElement>) => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const rect = canvas.getBoundingClientRect();
        const scaleX = canvas.width / rect.width;
        const scaleY = canvas.height / rect.height;

        const x = (event.clientX - rect.left) * scaleX;
        const y = (event.clientY - rect.top) * scaleY;

        const pixelX = Math.floor(x / SCALE);
        const pixelY = Math.floor(y / SCALE);

        setConsoleValue([pixelX, pixelY]);

        const ctx = canvas.getContext('2d');
        if (!ctx) return;

        drawRequest(ctx, pixelX, pixelY);
    };

    ws && ws.onMessage((message: MessageEvent) => {
        const image = new Image();
        image.src = message.data;

        image.onload = () => {
            const canvas = canvasRef.current;
            if (!canvas) return;
            const ctx = canvas.getContext('2d')
            if (!ctx) return;
            canvas.width = image.width;
            canvas.height = image.height;
            ctx.drawImage(image, 0, 0);
        }
    });

    const handleLogout = () => {
        localStorage.removeItem("jwt");
        navigate("/");
    }

    const getCurrentCooldown = () => {
        axios.get("/user/cooldown", {
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("jwt")}`
            }
        }).then((res: AxiosResponse<number>) => {
                setCooldown(res.data);
            }
        )
    }


    return (
        <>
            <img src={Logo} className={"logo"} alt={undefined}/>
            <div id={"home-background"}/>

            <div id={"user-profile"}>
                <div id={"username-and-role-display"}>
                    {userData.username + ", " + userData.role}
                </div>
                <textarea id={"biography-display"} data-limit-rows="true" cols={1} rows={2}
                          defaultValue={userData.biography} disabled={true}/>
                <img id={"settings-image"} onClick={() => navigate("/settings")} src={Settings} alt={""}/>
                <img id={"logout-image"} onClick={handleLogout} src={Logout} alt={""}/>
            </div>

            {userData.role == Role.USER && <div id={"cooldown-counter"}>Cooldown: {cooldown}</div>}


            <div id={"color-picker-parent"} style={{
                "top": objPos[1] - 10,
                "left": objPos[0] - 10,
                "zIndex": 4
            }} onClick={() => {
                setDraggable(!draggable)
            }}>
                <input
                    value={localStorage.getItem("color") + ""}
                    id="color-picker"
                    type="color"
                    onChange={(e) => {
                        setCurrentColor(e.target.value)
                        localStorage.setItem("color", e.target.value)
                    }}
                />


            </div>

            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    const canvas = canvasRef.current;
                    if (canvas) {
                        const ctx = canvas.getContext("2d");
                        if (ctx) {
                            drawRequest(ctx, consoleValue[0], consoleValue[1]);
                        }
                    }
                }}
            >
                {userData.role == Role.ADMIN && <input
                    id="console"
                    onChange={(e) => {
                        setConsoleTextValue(e.target.value)
                        const parts = e.target.value.replace(/[()]/g, "").split("/");
                        const parsedValues = parts.map(part => parseInt(part, 10));
                        if (parsedValues.length === 2 && !isNaN(parsedValues[0]) && !isNaN(parsedValues[1])) {
                            setConsoleValue(parsedValues);
                        }
                    }}
                    value={consoleTextValue}
                />}
            </form>
            <canvas
                ref={canvasRef}
                width="400"
                height="400"
                id={"canvas"}
                onClick={handleCanvasClick}
            />


            <div id={"home-circle-design-parent"}>
                <CircleDesign/>
            </div>
        </>
    );
}
