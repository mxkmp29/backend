package models;

import java.util.ArrayList;
import java.util.List;

public class Data {
    enum CombinationProcess {
        //TODO:
    }

    enum SelectionProcess {
        //TODO:
    }

    public static List<Point> cities = new ArrayList<>();
    public static Point startingPoint;
    public static int populationSize;
    public static CombinationProcess chosenCombinationProcess;
    public static SelectionProcess chosenSelectionProcess ;
    public static int numbersToMutate;
    public static int mutationProb; // Mutationswahrscheinlichkeit
    public static int combinationProb; //Kombinationswahrscheinlichkeit
}
