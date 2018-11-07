import evolution.Evolution;
import evolution.enums.CancelCriteria;
import evolution.enums.CombinationProcess;
import evolution.enums.SelectionProcess;
import io.socket.emitter.Emitter;
import models.Configuration;
import models.Data;
import reader.CSVReaderInterface;
import reader.Point2DReader;
import server.ServerEvents;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        Data.socketConnector.getSocket().on(ServerEvents.START, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                new Thread(() -> {
                    Configuration config = new Configuration();
                    config.jsonParse(Arrays.toString(objects));
                    config.setPopulationToSimulate(100);
                    config.setCancelCriteria(CancelCriteria.NOT_CHANGED);
                    config.setSelectFromMatingPool(true);
                    config.setCombinationProcess(CombinationProcess.KEEP_FIRST_PERC);
                    config.setSelectionProcess(SelectionProcess.TOPN);

                    CSVReaderInterface reader = new Point2DReader("./Examples/2D_Test_Circle3.csv");
                    reader.readCsv();

                    Evolution evolution = new Evolution(config);
                    evolution.runEvolution();
                }).start();
            }
        });
    }
}
