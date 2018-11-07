package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import models.Data;

public class OwnSocketIOServer {

    SocketIOServer server = null;

    public OwnSocketIOServer(String host, int port){
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        server = new SocketIOServer(config);

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("OwnSocketIOServer: Client connected" + client);
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                System.out.println("OwnSocketIOServer: disconnected" + socketIOClient);
            }
        });

        server.addEventListener(ServerEvents.CONFIG, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object data, AckRequest ackSender) throws Exception {
                sendEvent(socketIOClient, ServerEvents.START, data);

            }
        });

        server.addEventListener(ServerEvents.START, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object data, AckRequest ackSender) throws Exception {
                sendEvent(socketIOClient, ServerEvents.START, data);

            }
        });

        server.addEventListener(ServerEvents.DATA, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object data, AckRequest ackRequest) throws Exception {
                sendEvent(socketIOClient, ServerEvents.DATA, data);

            }
        });

        server.addEventListener(ServerEvents.STOP, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object data, AckRequest ackRequest) throws Exception {
                sendEvent(socketIOClient, ServerEvents.STOP, data);
            }
        });
    }

    private void sendEvent(SocketIOClient senderClient, String event, Object data) {
        System.out.println("OwnSocketIOServer:" + event + " " + data);
        for (SocketIOClient client : server.getAllClients()) {
            if (client.getSessionId().equals(senderClient.getSessionId()) == false) {
                client.sendEvent(event, data);
            }
        }
    }

    public void startServer(){
        this.server.start();
    }


    public void stopServer(){
        this.server.stop();
    }


    public SocketIOServer getServer() {
        return server;
    }

    @Override
    public String toString() {
        return "OwnSocketIOServer{" +
                ", server=" + server +
                '}';
    }

    public static void main(String[] args){
        new OwnSocketIOServer(Data.SOCKET_URL, Data.SOCKET_PORT).startServer();
    }
}
