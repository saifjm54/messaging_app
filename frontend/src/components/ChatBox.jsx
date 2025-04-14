import React, { useEffect, useState } from "react";

const ChatBox = ({ userId }) => {


    const [socket, setSocket] = useState(null);
    const [msg,setMsg] = useState('');
    const [logs, setLogs] = useState([]);

    useEffect(() => {
        const ws = new WebSocket(`ws://localhost:8080/ws/chat?userId=${userId}`);
        ws.onmessage = (event) => {
            setLogs(prev => [...prev, `📥 ${event.data}`])
        };
        setSocket(ws);

        return () => ws.close();
    }, [userId]);

    const sendMessage = () => {
        if (socket && socket.readyState === WebSocket.OPEN){
                 const payload = {
                    to: 'bob',
                    message: msg,
                 };
                
                socket.send(JSON.stringify(payload));
                setLogs(prev => [...prev, `📤 ${msg}`])
                setMsg('');
        };
        console.log(logs)
    };

    return(
        <div>
            <h3>Chat as {userId}</h3>
            <div>
                <input value={msg} onChange={(e) => setMsg(e.target.value)} />
                <button onClick={sendMessage}>Send</button>
            </div>

        </div>
    );
};

export default ChatBox;