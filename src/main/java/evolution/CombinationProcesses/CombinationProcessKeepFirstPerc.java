package evolution.CombinationProcesses;

import models.Chromosome;

/**
 * Prozentualteil vom ersten behalten
 *
 * @param <T>
 */
public class CombinationProcessKeepFirstPerc<T> implements CombinationProcessInterface<T> {

    Chromosome<T> chromosome1;
    Chromosome<T> chromosome2;

    public CombinationProcessKeepFirstPerc(Chromosome<T> chromosome1, Chromosome<T> chromosome2) {
        this.chromosome1 = chromosome1;
        this.chromosome2 = chromosome2;
    }

    @Override
    public Chromosome<T> crossOver() {

        return null;
    }
}
