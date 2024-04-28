import React, { useRef, useEffect, useState, useCallback } from 'react';

const CanvasTestComponent: React.FC = () => {
    const canvasRef = useRef<HTMLCanvasElement>(null);
    const [scale, setScale] = useState<number>(10); // Set the initial scale with a type
    const [position, setPosition] = useState({ x: 0, y: 0 }); // Position of the red square
    const [isDragging, setIsDragging] = useState(false); // Dragging state
    const [mousePosition, setMousePosition] = useState<{ x: number; y: number } | undefined>(undefined);

    // Function to update the canvas drawing
    const draw = useCallback((ctx: CanvasRenderingContext2D) => {
        ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height); // Clear canvas
        ctx.setTransform(scale, 0, 0, scale, 0, 0); // Reset transform for scaling
        ctx.fillStyle = 'red';
        ctx.fillRect(position.x, position.y, 40, 40); // Draw red square at the position
    }, [scale, position]);

    // Effect to draw the red square
    useEffect(() => {
        const canvas = canvasRef.current;
        if (canvas) {
            const ctx = canvas.getContext('2d');
            if (ctx) {
                draw(ctx);
            }
        }
    }, [draw]);

    // Mouse down event
    const handleMouseDown = (event: React.MouseEvent) => {
        const canvas = canvasRef.current;
        if (canvas) {
            setIsDragging(true);
            setMousePosition({
                x: event.clientX - canvas.offsetLeft,
                y: event.clientY - canvas.offsetTop,
            });
        }
    };

    // Mouse move event
    const handleMouseMove = (event: React.MouseEvent) => {
        const canvas = canvasRef.current;
        if (isDragging && mousePosition && canvas) {
            const dx = (event.clientX - canvas.offsetLeft) - mousePosition.x;
            const dy = (event.clientY - canvas.offsetTop) - mousePosition.y;
            setPosition(prevPosition => ({
                x: prevPosition.x + dx / scale,
                y: prevPosition.y + dy / scale,
            }));
            setMousePosition({
                x: event.clientX - canvas.offsetLeft,
                y: event.clientY - canvas.offsetTop,
            });
        }
    };

    // Mouse up event
    const handleMouseUp = () => {
        setIsDragging(false);
        setMousePosition(undefined);
    };

    return (
        <div>
            <button onClick={() => setScale(scale => scale * 1.1)}>Zoom In</button>
            <button onClick={() => setScale(scale => Math.max(scale / 1.1, 1))}>Zoom Out</button>
            <canvas
                ref={canvasRef}
                width="400" // Fixed size for canvas element
                height="400"
                style={{ width: '400px', height: '400px', border: '1px solid black' }}
                onMouseDown={handleMouseDown}
                onMouseMove={handleMouseMove}
                onMouseUp={handleMouseUp}
                onMouseOut={handleMouseUp} // Handle the mouse going out of the canvas bounds
            />
        </div>
    );
};

export default CanvasTestComponent;
