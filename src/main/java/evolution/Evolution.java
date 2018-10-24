package evolution;


import evolution.CombinationProcesses.CombinationProcessInterface;
import evolution.CombinationProcesses.CombinationProcessKeepFirstPerc;
import evolution.SelectionProcesses.SelectCrossPair;
import evolution.SelectionProcesses.SelectionProcessBestN;
import evolution.SelectionProcesses.SelectionProcessFirstN;
import evolution.SelectionProcesses.SelectionProcessInterface;
import evolution.enums.SelectionProcess;
import models.Chromosome;
import models.Data;
import models.Generation;
import models.Point;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private int populationSize = 0;
    private int numbersToMutate = 0;
    private float mutationProb = 0.0f; // Mutationswahrscheinlichkeit
    private float combinationProb = 0.3f; //Kombinationswahrscheinlichkeit
    private float selectNPercent = 0.05f;
    private Generation<Point> generation;

    private SelectionProcess selectionProcess = SelectionProcess.TOPN;

    public Evolution(int populationSize, int numbersToMutate, float mutationProb, float combinationProb) {
        this.populationSize = populationSize;
        this.numbersToMutate = numbersToMutate;
        this.mutationProb = mutationProb;
        this.combinationProb = combinationProb;
        this.generation = new Generation<Point>(populationSize, Data.cities);
    }

    public void fitness() {
        for (Chromosome<Point> p : generation.getChromosomList()) {
            int sum = 0;
            for (int i = 0; i < p.getAttributes().size(); i++) {
                Point pThis = p.getAttribute(i);
                Point pNext = p.getAttribute((i + 1) % p.getAttributes().size());
                sum += pThis.getCostsList().get(pNext.getName());
            }
            p.setFitness(sum);
        }
    }

    public void select() {
        SelectionProcessInterface<Point> select;
        switch (this.selectionProcess) {
            case TOPN:
                select = new SelectionProcessBestN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                break;
            case FIRSTN:
                select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                break;
            default:
                select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                break;
        }
        this.generation.getPairingCanidates().addAll(select.select());
        System.out.println("Select(): " + this.generation.getPairingCanidates());
        System.out.println("Select(): " + this.generation.getPairingCanidates().size());
    }

    public void combine() {
        List<Chromosome<Point>> newGen = new ArrayList<>();

        //TODO: CrossOver
        SelectionProcessInterface<Point> select = new SelectCrossPair<>(this.generation.getPairingCanidates());
        System.out.println("combine() PairingCanidates:" + select.toString());
        for (int i = 0; i < populationSize; i++) {
            List<Chromosome<Point>> crossList = select.select();
            if (crossList.size() != 2) {
                System.err.println("CrossList size != 2" + crossList.size());
                continue;
            }
            double propability = Math.random();
            if (propability < this.combinationProb) { //TODO: switch case
                CombinationProcessInterface<Point> comb = new CombinationProcessKeepFirstPerc<>(crossList.get(0), crossList.get(1));
                newGen.add(comb.crossOver());
            } else {
                //TODO: kein crossover -> zufällige chromosome in neue generation
            }
        }

    }

    public Chromosome<Point> mutation() {
        return new Chromosome<Point>(null);
    }

}
