import CircleDesign from "./CircleDesign.tsx";
import { UserDataType } from "./App.tsx";
import { NavigateFunction } from "react-router-dom";
import React, { useCallback, useEffect, useRef, useState } from "react";
import Logo from "./assets/coop place logo.png";
import axios from "axios";

type HomePageProps = {
    userData: UserDataType;
    setUserData: React.Dispatch<React.SetStateAction<UserDataType>>;
    navigate: NavigateFunction;
};

export default function HomePage({ userData, navigate }: HomePageProps) {
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const [currentColor, setCurrentColor] = useState<string>("#000000");
    const textAreaRef = useRef<HTMLTextAreaElement>(null);
    const [scale] = useState<number>(4); //4 for 25/25
    const [position] = useState({ x: 0, y: 0 });
    const [consoleValue, setConsoleValue] = useState<number[]>([]);

    const draw = useCallback((ctx: CanvasRenderingContext2D) => {
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
        ctx.setTransform(scale, 0, 0, scale, 0, 0);
        ctx.fillStyle = "white";
        ctx.fillRect(position.x, position.y, 100, 100);
    }, [scale, position]);

    const drawRequest = useCallback( (ctx: CanvasRenderingContext2D) => {
        ctx.fillStyle = currentColor;
        ctx.fillRect(consoleValue[0], consoleValue[1], 1, 1 )
    }, [currentColor, consoleValue, scale])

    useEffect(() => {
        const canvas = canvasRef.current;
        if (canvas) {
            const ctx = canvas.getContext("2d");
            if (ctx) {
                draw(ctx);
            }
        }
    }, [draw]);

    const importCanvas = async () => {
        axios.get("/place/canvas")
            .then(res => {
                if (res.data) {
                    console.log("test")
                    const image = new Image();
                    console.log("test2")
                    image.src = decodeURIComponent(res.data);
                    /*image.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==";
                    */console.log(image.src);
                    image.onload = () => {
                        const canvas = canvasRef.current;
                        if (canvas) {
                            const ctx = canvas.getContext("2d");
                            if (ctx) {
                                ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
                                ctx.drawImage(image, 0, 0);
                            }
                        }
                    };
                    image.onerror = (error) => {
                        console.error('Error loading image:', error);
                    };

                }
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    };



    return (
        <>
            <img src={Logo} className={"logo"} alt={undefined} />
            <div id={"home-background"} />

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
                    id={"color-picker"}
                    type={"color"}
                    onChange={(e) => setCurrentColor(e.target.value)}
                />
                <textarea ref={textAreaRef} id={"hex-display"} value={currentColor} />
            </div>
            <div id={"test-display"}>{consoleValue}</div>

            <form
                onSubmit={(e) => {
                    e.preventDefault();
                    const canvas = canvasRef.current;
                    if (canvas) {
                        const ctx = canvas.getContext("2d");
                        if (ctx) {
                            drawRequest(ctx)
                            const dataUrl = canvas.toDataURL();
                            console.log(dataUrl);
                            axios.post("/place/canvas/save", dataUrl);
                        }
                    }
                }}
            >
                <input
                    id={"console"}
                    onChange={(e) => {
                        importCanvas(); // Call importCanvas function when console input changes
                        const parts = e.target.value.replace(/[()]/g, "").split("/");
                        const parsedValues = parts.map((part) => parseInt(part, 10));
                        if (
                            parsedValues.length === 2 &&
                            !isNaN(parsedValues[0]) &&
                            !isNaN(parsedValues[1])
                        ) {
                            setConsoleValue(parsedValues);
                        }
                    }}
                />
            </form>
            <canvas ref={canvasRef} width="400" height="400" id={"canvas"} />

            <div id={"home-circle-design-parent"}>
                <CircleDesign/>
            </div>
        </>
    );
}
