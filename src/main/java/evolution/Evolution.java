package evolution;


import evolution.combinationProcesses.CombinationProcessInterface;
import evolution.combinationProcesses.CombinationProcessKeepFirstPerc;
import evolution.combinationProcesses.CombinationProcessThreeThree;
import evolution.enums.CancelCriteria;
import evolution.enums.CombinationProcess;
import evolution.enums.SelectionProcess;
import evolution.selectionProcesses.*;
import io.socket.emitter.Emitter;
import models.*;
import server.ServerEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evolution {

    private int generationsToSimulate = 0;
    private int populationSize = 0;
    private double mutationProb = 0.0; // Mutationswahrscheinlichkeit
    private float combinationProb = 0.3f; //Kombinationswahrscheinlichkeit
    private float selectNPercent = 0.20f;
    private Generation<Point2D> generation;
    private boolean selectFromMatingPool = false;
    private CancelCriteria criteria = CancelCriteria.NOT_CHANGED;

    private SelectionProcess selectionProcess = SelectionProcess.SURIVAL;
    private CombinationProcess combinationProcess = CombinationProcess.KEEP_FIRST_PERC;

    private int stepInterval = 0;
    private boolean stop = false;

    public Evolution(int generations, int populationSize, double mutationProb, float combinationProb, boolean selectFromMatingPool) {
        this.generationsToSimulate = generations;
        this.populationSize = populationSize;
        this.mutationProb = mutationProb;
        this.combinationProb = combinationProb;
        this.generation = new Generation<Point2D>(populationSize, Data.cities2d);
        this.selectFromMatingPool = selectFromMatingPool;
        this.criteria = criteria;
    }

    public Evolution(Configuration config) {
        //TODO: generationToSimualate pro CancelCriteria im Frontend anders flaggen
        this(config.getPopulationToSimulate(), config.getPopulationSize(), config.getMutationProbability(), config.getCrossProbability(), config.isSelectFromMatingPool());
        this.criteria = CancelCriteria.values()[config.getCancelCriteria()];
        this.selectionProcess = SelectionProcess.values()[config.getSelectionProcess()];
        this.combinationProcess = CombinationProcess.values()[config.getCombinationProcess()];
        this.stepInterval = config.getStepInterval();
        Data.socketConnector.getSocket().on(ServerEvents.STOP, new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("SocketIO1:" + ServerEvents.STOP + " " + Arrays.toString(objects));
                Evolution.this.stop = true;
            }
        });
    }

    /**
     *
     */
    private void evaluate() {
        for (Chromosome<Point2D> p : generation.getChromosomList()) {
            float sum = this.fitness(p);
            p.setFitness(sum);
            generation.incrementOverallFitness(sum);
            if (generation.getBestCandidate() == null || sum < generation.getBestCandidate().getFitness()) {
                Chromosome<Point2D> pn = new Chromosome<>(p.getAttributes(), p.getFitness());
                generation.setBestCandidate(pn);
            }
        }
    }

    private float fitness(Chromosome<Point2D> chromosome) {
        float sum = 0;
        for (int i = 0; i < chromosome.getAttributes().size(); i++) {
            Point2D pThis = chromosome.getAttribute(i);
            Point2D pNext = chromosome.getAttribute((i + 1) % chromosome.getAttributes().size());
            sum += pThis.calcDistance(pNext);
        }
        return sum;
    }


    /**
     *
     */
    private void select() {
        SelectionProcessInterface<Point2D> select;
        if (this.selectFromMatingPool) {
            switch (this.selectionProcess) {
                case TOPN:
                    select = new SelectionProcessBestN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                    break;
                case FIRSTN:
                    select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                    break;
                case SURIVAL:
                    select = new SelectSurvivalOfTheFittest(this.generation.getChromosomeList(), this.generation.getOverallFitness(), Math.round(this.populationSize * selectNPercent));
                default:
                    select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), this.populationSize, this.selectNPercent);
                    break;
            }
            this.generation.getMatingPool().addAll(select.select());
        }
    }

    /**
     *
     */
    private void combine() {
        List<Chromosome<Point2D>> newGen = new ArrayList<>();
        SelectionProcessInterface<Point2D> select;

        if (selectFromMatingPool) {
            select = new SelectCrossPair<>(this.generation.getMatingPool());
        } else {
            switch (this.selectionProcess) {
                case TOPN:
                    select = new SelectionProcessBestN<>(this.generation.getChromosomList(), 1, 2); //TODO:
                    break;
                case FIRSTN:
                    select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), 1, 2);
                    break;
                case SURIVAL:
                    select = new SelectSurvivalOfTheFittest(this.generation.getChromosomeList(), this.generation.getOverallFitness(), 2);
                    break;
                default:
                    select = new SelectionProcessFirstN<>(this.generation.getChromosomList(), 1, 2);
                    break;
            }
        }

        for (int i = 0; i < populationSize; i++) {
            List<Chromosome<Point2D>> crossList = select.select();
            if (crossList.size() != 2) {
                System.err.println("CrossList size != 2" + crossList.size());
                continue;
            }
            double propability = Math.random();
            if (propability < this.combinationProb) {
                CombinationProcessInterface<Point2D> comb;
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
        generation = new Generation<>(newGen, this.generation);
    }

    private void mutate() {
        for (Chromosome<Point2D> chr : generation.getChromosomeList()) {
            double rand = Math.random();
            if (rand < mutationProb) {
                int gen1 = (int) (Math.random() * chr.getAttributes().size());
                int gen2 = (int) (Math.random() * chr.getAttributes().size());
                Point2D temp = chr.getAttribute(gen1);
                chr.setAttribute(gen1, chr.getAttribute(gen2));
                chr.setAttribute(gen2, temp);
            }
        }
    }

    public void runEvolution() {
        int bestChanged = 0;
        Chromosome<Point2D> oldBest;

        Data.socketConnector.sendMessage(ServerEvents.STOP, false);
        switch(criteria) {
            case NOT_CHANGED:
                while (bestChanged < generationsToSimulate && !stop) {
                    oldBest = generation.getBestCandidate();
                    evaluate();
                    select();
                    combine();
                    mutate();
                    if (generation.getBestCandidate() != oldBest) {
                        bestChanged = 0;
                        Data.socketConnector.sendMessage(ServerEvents.DATA, generation.getBestCandidate().toJSON());
                        System.out.println("Generation: " + generation.getGenerationNumber() + " Best candidate: " + generation.getBestCandidate().toString());
                    } else {
                        System.out.println("Generation: " + generation.getGenerationNumber());
                        bestChanged++;
                    }
                    try {
                        Thread.sleep(this.stepInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case SIMULATE_N_GENERATION:
                while (generation.getGenerationNumber() < generationsToSimulate && !stop) {
                    oldBest = generation.getBestCandidate();
                    evaluate();
                    select();
                    combine();
                    mutate();
                    if (generation.getBestCandidate() != oldBest) {
                        Data.socketConnector.sendMessage(ServerEvents.DATA, generation.getBestCandidate().toJSON());
                    }
                    System.out.println("Generation: " + generation.getGenerationNumber() + " Best candidate: " + generation.getBestCandidate().toString());
                    try {
                        Thread.sleep(this.stepInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        Data.socketConnector.sendMessage(ServerEvents.STOP, true);
        Data.socketConnector.sendMessage(ServerEvents.DATA, generation.getBestCandidate().toJSON());
        System.out.println("Simulation finished.");
        System.out.println("Simulated " + generation.getGenerationNumber() + " generations. With " + populationSize + " chromosomes in each generation.");
        System.out.println("Best candidate: " + generation.getBestCandidate().toString());

    }
}

