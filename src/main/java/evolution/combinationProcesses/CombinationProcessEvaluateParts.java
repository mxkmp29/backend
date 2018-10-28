package evolution.combinationProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class CombinationProcessEvaluateParts<T> implements CombinationProcessInterface {

    private Chromosome<T> chromosome1;
    private Chromosome<T> chromosome2;

    public CombinationProcessEvaluateParts(Chromosome<T> chromosome1, Chromosome<T> chromosome2) {
        this.chromosome1 = chromosome1;
        this.chromosome2 = chromosome2;
    }

    @Override
    public Chromosome crossOver() {
        List<T> crossedChromosome = new ArrayList<>();


        return new Chromosome<T>(crossedChromosome);
    }
}
