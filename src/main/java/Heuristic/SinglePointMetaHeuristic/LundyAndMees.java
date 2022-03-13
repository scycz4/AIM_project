package Heuristic.SinglePointMetaHeuristic;

public class LundyAndMees {
    private double currentTemperature;
    private double backupTemperature;
    private final double beta;

    public LundyAndMees(double initialSolutionFitness){
        double c=1.0;
        backupTemperature=currentTemperature=c*initialSolutionFitness;
        this.beta=0.0001d;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void advanceTemperature() {

        // TODO update the value of the current temperature, 'dCurrentTemperature'

        currentTemperature = currentTemperature / (1 + beta * currentTemperature);
    }

    public void reHeat(){
        currentTemperature=backupTemperature;
    }


}
