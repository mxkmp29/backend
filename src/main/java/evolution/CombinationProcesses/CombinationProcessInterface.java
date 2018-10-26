package evolution.combinationProcesses;

import models.Chromosome;

public interface CombinationProcessInterface<T> {

    public Chromosome<T> crossOver();

}
