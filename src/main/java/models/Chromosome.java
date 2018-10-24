package models;

import java.util.*;

public class Chromosome<T> {

    private List<T> attributes = new ArrayList<T>();
    private T startingPoint;

    public Chromosome(List<T> attributes) {
        this.attributes = attributes;
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
        // startbedingungen -> Vorgegebener Punkt
        if (this.startingPoint != null) {
            try{
                this.startingPoint = (T) Data.startingPoint; //TODO:
            }catch(ClassCastException e){
                System.err.println(e);
            }

        }
        int idx = this.attributes.indexOf(this.startingPoint);
        if (idx != 0) {
            this.attributes.set(idx, this.attributes.get(0));
            this.attributes.set(0, startingPoint);
        }

        //TODO: reparieren nicht ersetzten
        if (!this.duplicates(this.attributes)) {
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
}
