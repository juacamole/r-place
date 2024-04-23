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
    const [scale] = useState<number>(10); //4 for 25/25
    const [position] = useState({ x: 0, y: 0 });
    const [consoleValue, setConsoleValue] = useState<number[]>([]);

    const draw = useCallback((ctx: CanvasRenderingContext2D) => {
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
        ctx.setTransform(scale, 0, 0, scale, 0, 0);
        ctx.fillStyle = "white";
        ctx.fillRect(position.x, position.y, 100, 100);
    }, [scale, position]);

    const drawRequest = useCallback((ctx: CanvasRenderingContext2D) => {
        const pixelSize = 10;
        ctx.resetTransform();
        ctx.fillStyle = currentColor;
        ctx.translate(consoleValue[0] * pixelSize, consoleValue[1] * pixelSize);
        ctx.scale(scale, scale);
        ctx.fillRect(0, 0, 1, 1);
        ctx.resetTransform();
    }, [currentColor, consoleValue, scale]);



    useEffect(() => {
        importCanvas();
        const canvas = canvasRef.current;
        if (canvas) {
            const ctx = canvas.getContext("2d");
            if (ctx) {
                draw(ctx);
            }
        }
    }, );



    const importCanvas = async () => {
        axios.get("/place/canvas", { responseType: 'json' })
            .then(res => {
                try {
                    const canvasData = JSON.parse(res.data.canvasData);
                    if (canvasData && canvasData.image) {
                        const imageSrc = canvasData.image;
                        const image = new Image();
                        image.src = imageSrc;

                        image.onload = () => {
                            const canvas = canvasRef.current;
                            if (canvas) {
                                const ctx = canvas.getContext("2d");
                                if (ctx) { // Check if context is not null
                                    canvas.width = image.width; // Adjust canvas size to image size
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
                    id="color-picker"
                    type="color"
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
                            drawRequest(ctx);
                            const dataUrl = canvas.toDataURL();
                            axios.post("/place/canvas/save", { image: dataUrl }).catch(console.error);
                        }
                    }
                }}
            >
                <input
                    id="console"
                    onChange={(e) => {
                        importCanvas();  // Ensure this is the intended behavior to reload the canvas on each input change
                        const parts = e.target.value.replace(/[()]/g, "").split("/");
                        const parsedValues = parts.map(part => parseInt(part, 10));
                        if (parsedValues.length === 2 && !isNaN(parsedValues[0]) && !isNaN(parsedValues[1])) {
                            setConsoleValue(parsedValues);
                        }
                    }}
                />
                <button type="submit">Save Canvas</button>
            </form>
            <canvas ref={canvasRef} width="400" height="400" id={"canvas"} />

            <div id={"home-circle-design-parent"}>
                <CircleDesign/>
            </div>
        </>
    );
}
