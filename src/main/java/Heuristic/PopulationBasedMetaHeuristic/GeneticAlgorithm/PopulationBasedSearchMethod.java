package Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm;

import Problem.Problem;

import java.util.Random;

public abstract class PopulationBasedSearchMethod implements SearchMethod{

    protected final Problem problem;
    protected final Random random;
    protected final int POP_SIZE;
    protected final int BACK_UP_SOLUTION_INDEX;

    public PopulationBasedSearchMethod(Problem problem,int populationSize){
        this.POP_SIZE=populationSize;
        this.BACK_UP_SOLUTION_INDEX=(populationSize<<1)-1;
        problem.setPopulationSize(populationSize<<1);

        this.problem=problem;
        this.random =problem.getRandom();
    }

    @Override
    public double run() {
        runMainLoop();

        double best=Double.NEGATIVE_INFINITY;
        for(int i=0;i<POP_SIZE;i++){
            double value=problem.getObjectiveFunctionValue(i);
            best=value<best?value:best;
        }

        return best;
    }

    protected abstract void runMainLoop();
}
