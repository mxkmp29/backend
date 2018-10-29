package evolution.selectionProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.List;

/**
 * First N Elements
 *
 * @param <T>
 */
public class SelectionProcessFirstN<T> implements SelectionProcessInterface<T> {

    private List<Chromosome<T>> selectList = new ArrayList<>();
    private float N = 0;
    private int popSize = 0;

    public SelectionProcessFirstN(List<Chromosome<T>> selectList, int popSize, float n) {
        this.selectList = selectList;
        N = n;
        this.popSize = popSize;
    }

    @Override
    public List<Chromosome<T>> select() {
        List<Chromosome<T>> topNList = new ArrayList<>();

        for (int i = 0; i < (N * popSize); i++) {
            topNList.add(this.selectList.get(i));
        }

        return topNList;
    }
}
