package evolution.selectionProcesses;

import models.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectSurvivalOfTheFittest<T> implements SelectionProcessInterface<T> {

    private List<Chromosome<T>> selectList = new ArrayList<>();
    private double percent = 0.0;
    private double overAllFitness = 0.0;
    private int count;


    public SelectSurvivalOfTheFittest(List<Chromosome<T>> selectList, double overAllFitness, int count) {
        this.selectList = selectList;
        this.overAllFitness = overAllFitness;
        this.count = count;
    }

    @Override
    public List<Chromosome<T>> select() {
        this.calcSurvivalProbability();
        List<Chromosome<T>> list = new ArrayList<>();

        for (int k = 0; k < this.count; k++) {
            for (int i = 0; i < this.selectList.size(); i++) {
                boolean flagged = false;
                double r = Math.random();
                double sum = 0.0;
                for (int j = 0; j < this.selectList.size(); j++) {
                    Chromosome<T> chromosome = this.selectList.get(j);

                    if (sum <= r) {
                        sum += chromosome.getSurvivalProb();
                        if (r < sum) {
                            list.add(chromosome);
                            flagged = true;
                            break;
                        }
                    }
                }
                if (flagged) {
                    break;
                }
            }
        }
        return list;
    }

    /**
     * d = sum(1-p) - 1
     * N = Anzahl Chromosome
     * Pn = 1 - P - d/N
     *
     * @return
     */
    private void calcSurvivalProbability() {
        double d = 0.0f;
        int N = this.selectList.size();

        for (Chromosome<T> chromosome : this.selectList) {
            double p = chromosome.getFitness() / this.overAllFitness;
            d += 1 - p;
            chromosome.setSurvivalProb(p);

        }
        d = d - 1;
        d = d / N;

        for (Chromosome<T> chromosome : this.selectList) {
            chromosome.setSurvivalProb(1 - chromosome.getSurvivalProb() - d);
        }
    }
}
