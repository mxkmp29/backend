package evolution;


import evolution.combinationProcesses.CombinationProcessInterface;
import evolution.combinationProcesses.CombinationProcessKeepFirstPerc;
import evolution.combinationProcesses.CombinationProcessThreeThree;
import evolution.enums.CombinationProcess;
import evolution.enums.SelectionProcess;
import evolution.selectionProcesses.*;
import models.Chromosome;
import models.Data;
import models.Generation;
import models.Point;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private int generationsToSimulate = 0;
    private int populationSize = 0;
    private double mutationProb = 0.0; // Mutationswahrscheinlichkeit
    private float combinationProb = 0.3f; //Kombinationswahrscheinlichkeit
    private float selectNPercent = 0.40f;
    private Generation<Point> generation;

    private SelectionProcess selectionProcess = SelectionProcess.TOPN;
    private CombinationProcess combinationProcess = CombinationProcess.KEEP_FIRST_PERC;

    public Evolution(int generations, int populationSize, double mutationProb, float combinationProb) {
        this.generationsToSimulate = generations;
        this.populationSize = populationSize;
        this.mutationProb = mutationProb;
        this.combinationProb = combinationProb;
        this.generation = new Generation<Point>(populationSize, Data.cities);
    }

    /**
     *
     */
    private void evaluate() {
        for (Chromosome<Point> p : generation.getChromosomList()) {
            float sum = this.fitness(p);
            p.setFitness(sum);
            generation.incrementOverallFitness(sum);
            if (generation.getBestCandidate() == null || sum < generation.getBestCandidate().getFitness()) {
                generation.setBestCandidate(p);
            }
        }
    }

    private float fitness(Chromosome<Point> chromosome) {
        float sum = 0;
        for (int i = 0; i < chromosome.getAttributes().size(); i++) {
            Point pThis = chromosome.getAttribute(i);
            Point pNext = chromosome.getAttribute((i + 1) % chromosome.getAttributes().size());
            sum += pThis.getCostsList().get(pNext.getName());
        }
        return sum;
    }


    /**
     *
     */
    private void select() {
        SelectionProcessInterface<Point> select;
        switch (this.selectionProcess) {
            case TOPN:
                select = new SelectionProcessBestN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                break;
            case FIRSTN:
                select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                break;
            case SURIVAL:
                select = new SelectSurvivalOfTheFittest(this.generation.getChromosomeList(), this.selectNPercent, this.generation.getOverallFitness());
            default:
                select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                break;
        }
        this.generation.getMatingPool().addAll(select.select());
        //System.out.println("Select(): " + this.generation.getMatingPool());
        //System.out.println("Select(): " + this.generation.getMatingPool().size());
    }

    /**
     *
     */
    private void combine() {
        List<Chromosome<Point>> newGen = new ArrayList<>();

        SelectionProcessInterface<Point> select = new SelectCrossPair<>(this.generation.getMatingPool());
        for (int i = 0; i < populationSize; i++) {
            List<Chromosome<Point>> crossList = select.select();
            if (crossList.size() != 2) {
                System.err.println("CrossList size != 2" + crossList.size());
                continue;
            }
            double propability = Math.random();
            if (propability < this.combinationProb) {
                CombinationProcessInterface<Point> comb;
                switch(this.combinationProcess) {
                    case THREE_THREE:
                        comb = new CombinationProcessThreeThree<>(crossList.get(0), crossList.get(1));
                        break;
                    case KEEP_FIRST_PERC:
                        comb = new CombinationProcessKeepFirstPerc<>(crossList.get(0), crossList.get(1));
                        break;
                    default:
                        comb = new CombinationProcessKeepFirstPerc<>(crossList.get(0), crossList.get(1));
                }
                newGen.add(comb.crossOver());
            } else {
                //TODO: kein crossover -> zufällige chromosome in neue generation
                newGen.add(crossList.get(0));
            }
        }
        generation = new Generation<>(newGen, generation);
    }

    private void mutate() {
        for(Chromosome<Point> chr : generation.getChromosomeList()) {
            double rand = Math.random();
            if (rand <= mutationProb) {
                int gen1 = (int) (Math.random() * chr.getAttributes().size());
                int gen2 = (int) (Math.random() * chr.getAttributes().size());
                Point temp = chr.getAttribute(gen1);
                chr.setAttribute(gen1, chr.getAttribute(gen2));
                chr.setAttribute(gen2, temp);
            }
        }
    }

    public void runEvolution() {
        int bestChanged = 0;
        Chromosome<Point> oldBest;
        while (bestChanged < 100) {
            oldBest = generation.getBestCandidate();
       // while(genNumber < generationsToSimulate) {
            evaluate();
            select();
            combine();
            mutate();
            if (generation.getBestCandidate() != oldBest) {
                bestChanged = 0;
            } else {
                bestChanged++;
            }
            System.out.println("Generation: " + generation.getGenerationNumber() + " Best candidate: " + generation.getBestCandidate().toString());
        }

        System.out.println("Simulation finished.");
        System.out.println("Simulated " + generation.getGenerationNumber() + " generations. With " + populationSize + " chromosomes in each generation.");
        System.out.println("Best candidate: " + generation.getBestCandidate().toString());
    }

    public void setSelectionProcess(SelectionProcess selectionProcess) {
        this.selectionProcess = selectionProcess;
    }

    public void setCombinationProcess(CombinationProcess combinationProcess) {
        this.combinationProcess = combinationProcess;
    }
}

