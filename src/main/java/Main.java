import Reader.CSVReader;
import evolution.Evolution;
import models.Data;
import models.Generation;
import models.Point;

public class Main {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader("./Examples/Test_01.csv");
        reader.readCsv();

        Generation<Point> generation = new Generation<>(100, Data.cities);

        Evolution evolution = new Evolution(100, 10, 0, 0.3f);
        evolution.fitness();
        evolution.select();
        evolution.combine();

    }
}
