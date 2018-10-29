package models;

import java.util.ArrayList;
import java.util.List;

public class Generation<T> {

    private List<Chromosome<T>> chromosomeList = new ArrayList<>();
    private List<Chromosome<T>> matingPool = new ArrayList<>();
    private Chromosome<T> bestCandidate;
    private int generationNumber = 0;
    private int overallFitness = 0;

    public Generation(int size, List<T> cities) {
        for(int i = 0; i < size; i++){
            Chromosome<T> chromosome = new Chromosome<>(cities);
            try{
                chromosome.generateRandomChromosom();
                this.chromosomeList.add(chromosome);
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }

    public Generation(List<Chromosome<T>> chromosomeList) {
        this.chromosomeList = chromosomeList;
    }

    public Generation(List<Chromosome<T>> chromosomeList, Generation<T> generation) {
        this(chromosomeList);
        this.bestCandidate = generation.getBestCandidate();
        this.generationNumber = generation.getGenerationNumber() + 1;
    }
    public List<Chromosome<T>> getChromosomList() {
        return chromosomeList;
    }

    public Chromosome<T> getChromosom(int index) {
        return this.chromosomeList.get(index);
    }

    public List<Chromosome<T>> getChromosomeList() {
        return chromosomeList;
    }

    public void setChromosomeList(List<Chromosome<T>> chromosomeList) {
        this.chromosomeList = chromosomeList;
    }

    public List<Chromosome<T>> getMatingPool() {
        return matingPool;
    }

    public void setMatingPool(List<Chromosome<T>> matingPool) {
        this.matingPool = matingPool;
    }

    public Chromosome<T> getBestCandidate() {
        return bestCandidate;
    }

    public void setBestCandidate(Chromosome<T> bestCandidate) {
        this.bestCandidate = bestCandidate;
    }

    public void incrementGenerationNumber() {
        this.generationNumber++;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    public int getOverallFitness() {
        return overallFitness;
    }

    public void setOverallFitness(int overallFitness) {
        this.overallFitness = overallFitness;
    }

    public void addToOveralFitness(int fitness) {
        this.overallFitness += fitness;
    }

    @Override
    public String toString() {
        return "Generation{" +
                "chromosomeList=" + chromosomeList +
                '}';
    }
}
