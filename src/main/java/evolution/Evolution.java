package evolution;


import evolution.combinationProcesses.CombinationProcessInterface;
import evolution.combinationProcesses.CombinationProcessKeepFirstPerc;
import evolution.selectionProcesses.SelectCrossPair;
import evolution.selectionProcesses.SelectionProcessBestN;
import evolution.selectionProcesses.SelectionProcessFirstN;
import evolution.selectionProcesses.SelectionProcessInterface;
import evolution.enums.SelectionProcess;
import models.Chromosome;
import models.Data;
import models.Generation;
import models.Point;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private int genNumber = 1;
    private Chromosome<Point> bestCandidate;

    private int generationsToSimulate = 0;
    private int populationSize = 0;
    private int numbersToMutate = 0;
    private float mutationProb = 0.0f; // Mutationswahrscheinlichkeit
    private float combinationProb = 0.3f; //Kombinationswahrscheinlichkeit
    private float selectNPercent = 0.30f;
    private Generation<Point> generation;

    private SelectionProcess selectionProcess = SelectionProcess.TOPN;

    public Evolution(int generations, int populationSize, int numbersToMutate, float mutationProb, float combinationProb) {
        this.generationsToSimulate = generations;
        this.populationSize = populationSize;
        this.numbersToMutate = numbersToMutate;
        this.mutationProb = mutationProb;
        this.combinationProb = combinationProb;
        this.generation = new Generation<Point>(populationSize, Data.cities);
    }

    public void evaluate() {
        for (Chromosome<Point> p : generation.getChromosomList()) {
            int sum = 0;
            for (int i = 0; i < p.getAttributes().size(); i++) {
                Point pThis = p.getAttribute(i);
                Point pNext = p.getAttribute((i + 1) % p.getAttributes().size());
                sum += pThis.getCostsList().get(pNext.getName());
            }
            p.setFitness(sum);
            if(bestCandidate == null || sum < bestCandidate.getFitness()) {
                bestCandidate = p;
            }
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

    public List<Chromosome<Point>> combine() {
        List<Chromosome<Point>> newGen = new ArrayList<>();

        //TODO: CrossOver
        SelectionProcessInterface<Point> select = new SelectCrossPair<>(this.generation.getPairingCanidates());
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
                newGen.add(crossList.get(0));
            }
        }
        return newGen;
    }

    public Chromosome<Point> mutation() {
        return new Chromosome<Point>(null);
    }

    public void newGeneration(List<Chromosome<Point>> generation) {
        this.generation = new Generation(generation);
        genNumber++;
    }

    public int getGenNumber() {
        return genNumber;
    }

    public int getGenerationsToSimulate() {
        return generationsToSimulate;
    }

    public Chromosome<Point> getBestCandidate() {
        return bestCandidate;
    }
}

