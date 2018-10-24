package evolution.CombinationProcesses;

import models.Chromosome;

public interface CombinationProcessInterface<T> {

    public Chromosome<T> crossOver();

}
