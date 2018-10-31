package evolution.selectionProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Best N Elements
 *
 * @param <T>
 */
public class SelectionProcessBestN<T> implements SelectionProcessInterface<T> {

    private List<Chromosome<T>> selectList = new ArrayList<>();
    private double N = 0;
    private int popSize = 0;

    public SelectionProcessBestN(List<Chromosome<T>> selectList, int popSize, double n) {
        this.selectList = selectList;
        N = n;
        this.popSize = popSize;
    }

    @Override
    public List<Chromosome<T>> select() {
        Collections.sort(this.selectList, new Comparator<Chromosome<T>>() {
            @Override
            public int compare(Chromosome<T> o1, Chromosome<T> o2) {
                return Float.compare(o1.getFitness(), o2.getFitness());
            }
        });
        List<Chromosome<T>> topNList = new ArrayList<>();

        for (int i = 0; i < (N * popSize); i++) {
            topNList.add(this.selectList.get(i));
        }

        return topNList;
    }
}
