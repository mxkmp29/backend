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


    public static void main(String[] args) throws Exception {

        Data.socketConnector.getSocket().on(ServerEvents.START, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Configuration config = new Configuration();
                config.jsonParse(Arrays.toString(objects));
                config.setPopulationToSimulate(1000);
                config.setCancelCriteria(CancelCriteria.NOT_CHANGED);
                config.setSelectFromMatingPool(true);

                CSVReaderInterface reader = new Point2DReader("./Examples/2D_Test_Circle4.csv");
                reader.readCsv();

                Evolution evolution = new Evolution(config);
                evolution.setCombinationProcess(CombinationProcess.KEEP_FIRST_PERC); //TODO: Config
                evolution.setSelectionProcess(SelectionProcess.TOPN); //TODO: Config

                evolution.runEvolution();
            }
        });
    }
}
