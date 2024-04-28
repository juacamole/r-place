import CircleDesign from "../design-components/CircleDesign.tsx";
import {NavigateFunction} from "react-router-dom";
import React, {useEffect, useRef, useState} from "react";
import Logo from "../../assets/coop place logo.png";
import axios from "axios";
import {WSService, WSServiceType} from "../../WSService.tsx";
import {UserDataType} from "../models/model.tsx";

type HomePageProps = {
    userData: UserDataType;
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>;
    navigate: NavigateFunction;
};

export default function HomePage({userData, navigate}: HomePageProps) {

    const [ws, setWs] = useState<WSServiceType>();
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const [currentColor, setCurrentColor] = useState<string>("#000000");
    const [scale] = useState<number>(10);
    const [consoleValue, setConsoleValue] = useState<number[]>([]);
    const [consoleTextValue, setConsoleTextValue] = useState<string>("")

    const drawRequest = (ctx: CanvasRenderingContext2D, pixelX: number, pixelY: number) => {
        const pixelSize = 10;
        ctx.fillStyle = currentColor;
        ctx.fillRect(pixelX * pixelSize, pixelY * pixelSize, pixelSize, pixelSize);
        canvasRef.current != null && ws && ws.updateCanvas((canvasRef.current).toDataURL())
    };


    useEffect(() => {
        setWs(WSService());
        importCanvas();
    }, []);


    const importCanvas = async () => {
        axios.get("/place/canvas", {responseType: 'json'})
            .then((res) => {
                try {
                    const canvasData = res.data.canvasData;
                    if (canvasData) {
                        const image = new Image();
                        image.src = canvasData;

                        image.onload = () => {
                            const canvas = canvasRef.current;
                            if (canvas) {
                                const ctx = canvas.getContext("2d");
                                if (ctx) {
                                    canvas.width = image.width;
                                    canvas.height = image.height;
                                    ctx.drawImage(image, 0, 0);
                                } else {
                                    console.error("Canvas context is null.");
                                }
                            }
                        };

                        image.onerror = () => {
                            console.error("Failed to load the image with provided Base64 data.");
                        };
                    } else {
                        console.error("The 'canvasData' key does not contain a valid 'image' key.");
                    }
                } catch (error) {
                    console.error("Error parsing 'canvasData' or loading the image: ", error);
                }
            })
            .catch(error => {
                console.error("Error fetching or processing canvas data: ", error);
            });
    };

    const handleCanvasClick = (event: React.MouseEvent<HTMLCanvasElement>) => {
        const canvas = canvasRef.current;
        if (!canvas) return;

        const rect = canvas.getBoundingClientRect();
        const scaleX = canvas.width / rect.width;
        const scaleY = canvas.height / rect.height;

        const x = (event.clientX - rect.left) * scaleX;
        const y = (event.clientY - rect.top) * scaleY;

        const pixelX = Math.floor(x / scale);
        const pixelY = Math.floor(y / scale);

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


    return (
        <>
            <img src={Logo} className={"logo"} alt={undefined}/>
            <div id={"home-background"}/>

            <div id={"user-profile"}>
                <div>
                    {userData.username} {userData.role}
                </div>
                <p id={"user-biography"}>{userData.biography}</p>
                <button id={"user-settings"} onClick={() => navigate("/settings")}>
                    Settings
                </button>
            </div>

            <div id={"color-picker-parent"}>
                <input
                    id="color-picker"
                    type="color"
                    onChange={(e) => setCurrentColor(e.target.value)}
                />


            </div>
            <div id={"test-display"}>{consoleValue}</div>

            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    const canvas = canvasRef.current;
                    if (canvas) {
                        const ctx = canvas.getContext("2d");
                        if (ctx) {
                            drawRequest(ctx, consoleValue[0], consoleValue[1]);
                            const dataUrl = canvas.toDataURL();
                            axios.put("/place/canvas/save", {image: dataUrl}).catch(console.error);
                        }
                    }
                }}
            >
                <input
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
                />
                <button type="submit">Save Canvas</button>
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
