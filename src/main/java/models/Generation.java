package models;

import java.util.ArrayList;
import java.util.List;

public class Generation<T> {

    private List<Chromosome<T>> chromosomeList = new ArrayList<>();
    private List<Chromosome<T>> pairingCanidates = new ArrayList<>();

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
        System.out.println(this);
    }

    public Generation(List<Chromosome<T>> chromosomeList) {
        this.chromosomeList = chromosomeList;
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

    public List<Chromosome<T>> getPairingCanidates() {
        return pairingCanidates;
    }

    public void setPairingCanidates(List<Chromosome<T>> pairingCanidates) {
        this.pairingCanidates = pairingCanidates;
    }

    @Override
    public String toString() {
        return "Generation{" +
                "chromosomeList=" + chromosomeList +
                '}';
    }
}
