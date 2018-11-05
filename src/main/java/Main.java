import io.socket.client.Socket;
import models.Chromosome;
import models.Data;
import models.Point2D;
import models.SocketConnector;
import reader.CSVReaderInterface;
import reader.Point2DReader;
import server.ServerEvents;

public class Main {

    public static void main(String[] args) throws Exception {

        /*new Thread(() -> {
            try{
                SimpleHttpServer server = new SimpleHttpServer(8000);
                server.createServer();
            }catch(Exception e){
                System.err.println(e);
            }
        }).start();

        OwnSocketIOServer server = new OwnSocketIOServer(Data.SOCKET_URL, Data.SOCKET_PORT);
        server.startServer();*/

        /**
        PointReader reader = new PointReader("./Examples/Test_01.csv");
        reader.readCsv();

        Evolution evolution = new Evolution(10000, 10000, 0.05, 0.3f);
        evolution.setCombinationProcess(CombinationProcess.KEEP_FIRST_PERC);
        evolution.setSelectionProcess(SelectionProcess.SURIVAL);

        evolution.runEvolution();
         */

        CSVReaderInterface reader = new Point2DReader("./Examples/2D_Test_Circle.csv");
        reader.readCsv();
        Chromosome<Point2D> chromosome = new Chromosome<>(Data.cities2d);
        SocketConnector socketConnector = new SocketConnector();
        Socket socket = socketConnector.getSocket();

        socket.emit(ServerEvents.START, chromosome.toJSON());
        System.out.println(chromosome.toJSON());

    }
}
