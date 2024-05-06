export type WSServiceType = {
    send: (message: string) => void;
    updateCanvas: (canvas: string) => void;
    onMessage: (listener: (message: MessageEvent) => void) => void;
}

export function WSService(): WSServiceType {

    const ws = new WebSocket(`ws://localhost:8080/websocket-endpoint`, ["access_token", (localStorage.getItem("jwt") + "")]);

    const queue: string[] = [];
    const listenerQueue: ((message: MessageEvent) => void)[] = [];


    ws.addEventListener("message", (m) => {
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


    const updateCanvas = (canvas: string) => {
        sendMessage(canvas);
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

