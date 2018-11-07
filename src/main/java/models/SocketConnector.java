package models;

import io.socket.client.IO;
import io.socket.client.Socket;
import server.ServerEvents;

import java.net.URISyntaxException;

public class SocketConnector {

    private Socket socket;

    public SocketConnector() {
        try {
            socket = IO.socket(String.format("http://%s:%s", Data.SOCKET_URL, Data.SOCKET_PORT));
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendMessage(String event, Object data) {
        this.socket.emit(ServerEvents.DATA, data);
    }

}
