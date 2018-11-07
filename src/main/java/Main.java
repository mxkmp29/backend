import Reader.CSVReader;
import evolution.Evolution;
import evolution.enums.CombinationProcess;
import evolution.enums.SelectionProcess;
import models.Data;
import models.Generation;
import models.Point;

public class Main {

    public static void main(String[] args) throws Exception {

        CSVReader reader = new CSVReader("./Examples/Test_03.csv");
        reader.readCsv();

        Evolution evolution = new Evolution(10000, 2, 0.05, 0.3f, 0, 0);
        evolution.setCombinationProcess(CombinationProcess.KEEP_FIRST_PERC);
        evolution.setSelectionProcess(SelectionProcess.TOPN);

        evolution.runEvolution();

    }
}
