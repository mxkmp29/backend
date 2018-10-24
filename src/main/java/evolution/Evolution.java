package evolution;


import models.Chromosome;
import models.Data;
import models.Generation;
import models.Point;

public class Evolution {

    private int populationSize = 0;
    private int numbersToMutate = 0;
    private double mutationProb = 0.0; // Mutationswahrscheinlichkeit
    private double combinationProb = 0.0; //Kombinationswahrscheinlichkeit
    private Generation<Point> generation;

    public Evolution(int populationSize, int numbersToMutate, double mutationProb, double combinationProb) {
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
        SelectionProcessInterface select = new SelectionProcessBestN(this.generation.getChromosomList(), this.populationSize, 0.05);
        System.out.println(select.select());
        System.out.println(select.select().size());
    }

    public Chromosome<Point> mutation() {
        return new Chromosome<Point>(null);
    }

}
