import {MessageType} from "./App.tsx";

export type WSServiceType = {
    send: (message: string) => void;
    updateCanvas: (message: MessageType) => void;
    onMessage: (listener: (message: MessageEvent) => void) => void;
}

export function WSService(): WSServiceType {

    const ws = new WebSocket(`wss://coop-place.onrender.com/ws`);

    const queue: string[] = [];
    const listenerQueue: ((message: MessageEvent) => void)[] = [];


    ws.addEventListener("message", (m) => {
        console.error()
        listenerQueue.forEach(listener => listener(m));
    });

    const sendMessage = (message: string) => {
        if (ws.readyState == WebSocket.OPEN) {
            ws.send(message);
        } else {
            queue.push(message);
        }
    }

    ws.addEventListener("open", () => {
        queue.forEach(sendMessage);
    });


    const updateCanvas = (message: MessageType) => {
        sendMessage(JSON.stringify(message));
    }

    const registerEventListener = (listener: (message: MessageEvent) => void) => {
        listenerQueue.push(listener);
    }


    return {
        send: sendMessage,
        updateCanvas: updateCanvas,
        onMessage: registerEventListener
    };
}

