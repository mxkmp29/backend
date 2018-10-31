package models;

import java.util.*;

public class Chromosome<T> {

    private List<T> attributes = new ArrayList<T>();
    private float fitness = 0.0f;
    private double survivalProb = 0.0f;

    public Chromosome(List<T> attributes) {
        for (T attrib : attributes) {
            this.attributes.add(attrib);
        }
    }

    public List<T> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<T> attributes) {
        this.attributes = attributes;
    }

    public void setAttribute(int index, T element) {
        this.attributes.set(index, element);
    }

    public T getAttribute(int index) {
        return this.attributes.get(index);
    }

    public void generateRandomChromosom() throws Exception {
        Collections.shuffle(attributes);
        this.repairFunction();
    }

    public void repairFunction() throws Exception {
        //TODO: reparieren nicht ersetzten
        if (this.duplicates(this.attributes)) {
            try {
                this.attributes = (List<T>) Data.cities; //TODO:
                this.generateRandomChromosom();
            }catch(ClassCastException e){
                System.err.println(e);
            }
        }
        //TODO: Rückgabe mit neuer kreuzug
    }

    private boolean duplicates(List<T> attributes) {
        Set<T> lump = new HashSet<T>();
        for (T i : attributes) {
            if (lump.contains(i)) return true;
            lump.add(i);
        }
        return false;
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public double getSurvivalProb() {
        return survivalProb;
    }

    public void setSurvivalProb(double survivalProb) {
        this.survivalProb = survivalProb;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "attributes=" + attributes +
                ", fitness=" + fitness +
                ", survivalProb=" + survivalProb +
                '}';
    }
}
