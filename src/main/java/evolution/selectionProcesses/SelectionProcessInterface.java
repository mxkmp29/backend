package evolution.selectionProcesses;

import models.Chromosome;

import java.util.List;

public interface SelectionProcessInterface<T> {

    public List<Chromosome<T>> select();

}
