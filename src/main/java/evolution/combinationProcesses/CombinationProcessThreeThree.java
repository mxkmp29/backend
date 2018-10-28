package evolution.combinationProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class CombinationProcessThreeThree<T> implements CombinationProcessInterface {

    private Chromosome<T> chromosome1;
    private Chromosome<T> chromosome2;

    public CombinationProcessThreeThree(Chromosome<T> chromosome1, Chromosome<T> chromosome2) {
        this.chromosome1 = chromosome1;
        this.chromosome2 = chromosome2;
    }

    @Override
    public Chromosome crossOver() {

        int idx = 0, idxR = 0;
        int length = chromosome1.getAttributes().size();
        List<T> crossedChromosome = new ArrayList<>();
        T t;
        int n = 3;

        // Try to cross three elements in row from each chromosome
        while(idxR < length) {
            for (int i = idx; i < idx + n && i < length; i++) {
                t = chromosome1.getAttribute(i);
                if (!crossedChromosome.contains(t)) {
                    crossedChromosome.add(t);
                    idxR++;
                }
            }
            for (int i = idx; i < idx + n && i < length; i++) {
                t = chromosome2.getAttribute(i);
                if (!crossedChromosome.contains(t)) {
                    crossedChromosome.add(t);
                    idxR++;
                }
            }
            idx = idx + n;
        }

        return new Chromosome<T>(crossedChromosome);
    }
}
