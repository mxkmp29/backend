import Reader.CSVReader;
import evolution.Evolution;
import evolution.enums.CombinationProcess;
import evolution.enums.SelectionProcess;
import httpserver.SimpleHttpServer;
import models.Chromosome;
import models.Data;
import models.Generation;
import models.Point;

public class Main {

    public static void main(String[] args) throws Exception {
        SimpleHttpServer server = new SimpleHttpServer();
        server.createServer();

        //while(true);

        /*CSVReader reader = new CSVReader("./Examples/Test_01.csv");
        reader.readCsv();

        Generation<Point> generation = new Generation<>(100, Data.cities);

        Evolution evolution = new Evolution(10000, 1000, 0.05, 0.3f);
        evolution.setCombinationProcess(CombinationProcess.KEEP_FIRST_PERC);
        evolution.setSelectionProcess(SelectionProcess.TOPN);

        evolution.runEvolution();*/

    }
}
