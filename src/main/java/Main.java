import evolution.Evolution;
import evolution.enums.CancelCriteria;
import evolution.enums.CombinationProcess;
import evolution.enums.SelectionProcess;
import io.socket.emitter.Emitter;
import models.Configuration;
import models.Data;
import models.SocketProcessConfig;
import reader.CSVReaderInterface;
import reader.Point2DReader;
import server.ServerEvents;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Data.socketConnector.getSocket().on(ServerEvents.CONFIG, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                Data.socketConnector.sendMessage(ServerEvents.CONFIG, loadConfiguration().toString());
            }
        });

        Data.socketConnector.getSocket().on(ServerEvents.START, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                new Thread(() -> {
                    Configuration config = new Configuration();
                    config.jsonParse(Arrays.toString(objects));
                    System.out.println("Config " + config.toString());
                    CSVReaderInterface reader = new Point2DReader("./Examples/2D_Test_Circle2.csv");
                    reader.readCsv();

                    Evolution evolution = new Evolution(config);
                    evolution.runEvolution();
                }).start();
            }
        });
    }

    /**
     * Configurations
     *
     * @return
     */
    private static ArrayList<SocketProcessConfig> loadConfiguration() {
        ArrayList<SocketProcessConfig> configList = new ArrayList<>();

        String selectionProcess = "SelectionProcess";
        String combinationProcess = "CombinationProcess";
        String criteria = "Criteria";

        configList.add(new SocketProcessConfig(SelectionProcess.TOPN.ordinal(), "Selects the first N Elements", "TOP N", selectionProcess));
        configList.add(new SocketProcessConfig(SelectionProcess.FIRSTN.ordinal(), "Selects the first N Elements", "FIRST N", selectionProcess));
        configList.add(new SocketProcessConfig(SelectionProcess.SURIVAL.ordinal(), "Selects the first N Elements", "FIRST N", selectionProcess));

        configList.add(new SocketProcessConfig(CombinationProcess.KEEP_FIRST_PERC.ordinal(), "Combines the first percent of the generation", "KEEP FIRST PERCENT", combinationProcess));
        configList.add(new SocketProcessConfig(CombinationProcess.THREE_THREE.ordinal(), "TODO", "THREE THREE", combinationProcess));

        configList.add(new SocketProcessConfig(CancelCriteria.NOT_CHANGED.ordinal(), "Breaks off when N generations the best Chromsome has not changed", "NOT CHANGED", criteria));
        configList.add(new SocketProcessConfig(CancelCriteria.SIMULATE_N_GENERATION.ordinal(), "Simulates N Generations", "N GENERATION", criteria));
        return configList;
    }
}
