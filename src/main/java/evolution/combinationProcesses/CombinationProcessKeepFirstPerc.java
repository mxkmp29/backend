package evolution.combinationProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.List;

/**
 * Prozentualteil vom ersten behalten
 *
 * @param <T>
 */
public class CombinationProcessKeepFirstPerc<T> implements CombinationProcessInterface<T> {

    private Chromosome<T> chromosome1;
    private Chromosome<T> chromosome2;

    public CombinationProcessKeepFirstPerc(Chromosome<T> chromosome1, Chromosome<T> chromosome2) {
        this.chromosome1 = chromosome1;
        this.chromosome2 = chromosome2;
    }

    @Override
    public Chromosome<T> crossOver() {
        int idx = (int) (Math.random() * chromosome1.getAttributes().size());
        int length = (int) (Math.random() * (chromosome1.getAttributes().size() - idx));
        if (length == 0) {
            length = 1;
        }

        List<T> crossedChromosome = new ArrayList<>();
        //initialize array size
        for (int i = 0; i < chromosome1.getAttributes().size(); i++) {
            crossedChromosome.add(i, null);
        }

        int listIdx = idx;
        for (int i = idx; i < idx + length; i++) { //copy first chromosome randomly
            crossedChromosome.set(listIdx, chromosome1.getAttribute(i));
            listIdx++;
        }

        for (int i = idx + length; i < chromosome2.getAttributes().size(); i++) { //copy after index
            T attrib = chromosome2.getAttribute(i);
            if (!crossedChromosome.contains(attrib)) {
                crossedChromosome.set(listIdx, attrib);
                listIdx++;
            }
        }

        for (int i = 0; i < idx + length; i++) { //copy the rest
            T attrib = chromosome2.getAttribute(i);
            if (listIdx >= chromosome2.getAttributes().size()) {
                listIdx = 0;
            }
            if (!crossedChromosome.contains(attrib)) {
                crossedChromosome.set(listIdx, attrib);
                listIdx++;
            }
        }

        //System.out.println(String.format("crossOver() Result: %s\nChromosom1: %s\nChromosom2: %s", crossedChromosome, chromosome1, chromosome2));
        return new Chromosome<T>(crossedChromosome);
    }
}
