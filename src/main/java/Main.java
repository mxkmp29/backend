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

        Evolution evolution = new Evolution(100, 10000, 10, 0, 0.3f);
        while(evolution.getGenNumber() <= evolution.getGenerationsToSimulate()) {
            evolution.evaluate();
            evolution.select();
            evolution.newGeneration(evolution.combine());
            System.out.println("Generation number: " + evolution.getGenNumber());
        }
        System.out.println("Simulation finished.");
        System.out.println("Best candidate: " + evolution.getBestCandidate().toString());
    }
}
