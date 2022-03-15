package Heuristic.SinglePointMetaHeuristic;

import Heuristic.HeuristicMethods;
import Problem.Problem;

import java.util.Random;

public class SimulatedAnneal{
    private int CURRENT_SOLUTION_INDEX=0;
    private int BACKUP_SOLUTION_INDEX=1;

    private LundyAndMees coolingSchedule;
    private Problem problem;
    private Random random;

    public SimulatedAnneal(LundyAndMees lundyAndMees,Problem problem){
        this.coolingSchedule=lundyAndMees;
        this.problem=problem;
        random=new Random();
    }

    public void applyHeuristic() {
        int len = problem.getNumberOfVariables();
        int rndIndex = random.nextInt(len);
        problem.bitFlip(CURRENT_SOLUTION_INDEX, rndIndex);

        double s2 = problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        double s = problem.getObjectiveFunctionValue(BACKUP_SOLUTION_INDEX);
        double delta = s2 - s;

        double r = random.nextDouble();

        double pValue = Math.exp(delta / coolingSchedule.getCurrentTemperature());
        if ((delta > 0 || r < pValue)) {
            problem.copySolution(CURRENT_SOLUTION_INDEX, BACKUP_SOLUTION_INDEX);
        } else {
            problem.copySolution(BACKUP_SOLUTION_INDEX, CURRENT_SOLUTION_INDEX);
        }

        coolingSchedule.advanceTemperature();
        if(coolingSchedule.getCurrentTemperature()<1){
            coolingSchedule.reHeat();
        }
    }
}
