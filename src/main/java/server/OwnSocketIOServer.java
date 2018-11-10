package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.gson.Gson;
import models.Data;
import models.Point2D;
import models.PointFile;
import reader.Point2DReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class OwnSocketIOServer {

    SocketIOServer server = null;
    private final static String rootPath = "./Examples/2D/";

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
                sendEvent(socketIOClient, ServerEvents.CONFIG, data);
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

        server.addEventListener(ServerEvents.FILE, Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object data, AckRequest ackRequest) throws Exception {
                System.out.println("OwnSocketIOServer: " + ServerEvents.FILE + " " + data);

                File folder = new File(rootPath);
                File[] listOfFiles = folder.listFiles();
                ArrayList<PointFile<Point2D>> pointFileArrayList = new ArrayList<>();

                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        try {
                            pointFileArrayList.add(readFile(file.getName()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                socketIOClient.sendEvent(ServerEvents.FILE, new Gson().toJson(pointFileArrayList));

            }
        });
    }

    private static PointFile<Point2D> readFile(String filename) throws IOException {
        Point2DReader reader = new Point2DReader(rootPath + filename);
        reader.readCsv();

        return reader.getPointFile();
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

    private void sendEvent(SocketIOClient senderClient, String event, Object data) {
        System.out.println("OwnSocketIOServer: " + event + " " + data);
        for (SocketIOClient client : server.getAllClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(event, data);
            }
        }
    }
}
