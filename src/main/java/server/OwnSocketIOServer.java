package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
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

        server.addEventListener(ServerEvents.CONFIG, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient client, Object data, AckRequest ackSender) throws Exception {
                System.out.println("OwnSocketIOServer:" + ServerEvents.CONFIG + " " + data);
                server.getBroadcastOperations().sendEvent(ServerEvents.CONFIG, data);
            }
        });

        server.addEventListener(ServerEvents.START, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient client, Object data, AckRequest ackSender) throws Exception {
                System.out.println("OwnSocketIOServer:" + ServerEvents.START + " " + data);
                server.getBroadcastOperations().sendEvent(ServerEvents.START, data);
            }
        });
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
