package evolution.combinationProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class CombinationProcessCrossFourParts<T> implements CombinationProcessInterface {

    private Chromosome<T> chromosome1;
    private Chromosome<T> chromosome2;

    public CombinationProcessCrossFourParts(Chromosome<T> chromosome1, Chromosome<T> chromosome2) {
        this.chromosome1 = chromosome1;
        this.chromosome2 = chromosome2;
    }

    @Override
    public Chromosome crossOver() {
        List<T> crossedChromosome = new ArrayList<>();
        int length = chromosome1.getAttributes().size();
        int idxP = (int)(length / 2);

        for(int i = 0; i + idxP < length; i++) {
            crossedChromosome.add(chromosome1.getAttribute(i + idxP));
        }
        for(int i = 0; i < length; i++) {
            T t = chromosome2.getAttribute(i);
            if(!crossedChromosome.contains(t) {
                crossedChromosome.add(t);
            }
        }
        return new Chromosome<T>(crossedChromosome);
    }
}
