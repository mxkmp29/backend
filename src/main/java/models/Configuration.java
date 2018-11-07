package models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import evolution.enums.CancelCriteria;

import java.lang.reflect.Type;
import java.util.Collection;

public class Configuration {
    @SerializedName("stepInterval")
    private int stepInterval;
    @SerializedName("populationSize")
    private int populationSize;
    @SerializedName("mutationProbability")
    private float mutationProbability;
    @SerializedName("crossProbability")
    private float crossProbability;
    @SerializedName("populationToSimulate")
    private int populationToSimulate;
    @SerializedName("CancelCriteria")
    private CancelCriteria cancelCriteria;
    @SerializedName("selectFromMatingPool")
    private boolean selectFromMatingPool;


    public void jsonParse(String json) {
        Gson gson = new Gson();

        Type collectionType = new TypeToken<Collection<Configuration>>() {
        }.getType();
        Collection<Configuration> enums = gson.fromJson(json, collectionType);

        if (enums.size() > 1) {
            System.err.println("Invalid Configsize"); //TODO: Senden
            return;
        }
        for (Configuration config : enums) {
            this.stepInterval = config.getStepInterval();
            this.populationSize = config.getPopulationSize();
            this.mutationProbability = config.getMutationProbability();
            this.crossProbability = config.getCrossProbability();
            this.populationToSimulate = config.getPopulationToSimulate();
            this.cancelCriteria = config.getCancelCriteria();
            this.selectFromMatingPool = config.isSelectFromMatingPool();
        }
        System.out.println(this);
    }

    public int getStepInterval() {
        return stepInterval;
    }

    public void setStepInterval(int stepInterval) {
        this.stepInterval = stepInterval;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public float getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(float mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public float getCrossProbability() {
        return crossProbability;
    }

    public void setCrossProbability(float crossProbability) {
        this.crossProbability = crossProbability;
    }

    public int getPopulationToSimulate() {
        return populationToSimulate;
    }

    public void setPopulationToSimulate(int populationToSimulate) {
        this.populationToSimulate = populationToSimulate;
    }

    public CancelCriteria getCancelCriteria() {
        return cancelCriteria;
    }

    public void setCancelCriteria(CancelCriteria cancelCriteria) {
        this.cancelCriteria = cancelCriteria;
    }

    public boolean isSelectFromMatingPool() {
        return selectFromMatingPool;
    }

    public void setSelectFromMatingPool(boolean selectFromMatingPool) {
        this.selectFromMatingPool = selectFromMatingPool;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "stepInterval=" + stepInterval +
                ", populationSize=" + populationSize +
                ", mutationProbability=" + mutationProbability +
                ", crossProbability=" + crossProbability +
                ", populationToSimulate=" + populationToSimulate +
                ", cancelCriteria=" + cancelCriteria +
                ", selectFromMatingPool=" + selectFromMatingPool +
                '}';
    }
}