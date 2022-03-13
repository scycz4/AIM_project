package Heuristic.SinglePointMetaHeuristic;

public class LundyAndMees {
    private double currentTemperature;
    private final double beta;

    public LundyAndMees(double initialSolutionFitness){
        double c=1.0;
        currentTemperature=c*initialSolutionFitness;
        this.beta=0.0001d;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void advanceTemperature() {

        // TODO update the value of the current temperature, 'dCurrentTemperature'

        currentTemperature = currentTemperature / (1 + beta * currentTemperature);
    }
}
