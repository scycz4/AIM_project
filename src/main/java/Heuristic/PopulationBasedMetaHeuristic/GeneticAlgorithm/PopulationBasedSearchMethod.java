package Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class PopulationBasedSearchMethod implements SearchMethod{

    protected final Problem problem;
    protected final Random rng;
    protected final int POP_SIZE;
    protected final int BACK_UP_SOLUTION_INDEX;

    public PopulationBasedSearchMethod(Problem problem,int populationSize){
        this.POP_SIZE=populationSize;
        this.BACK_UP_SOLUTION_INDEX=(populationSize<<1)-1;
        problem.setPopulationSize(populationSize<<1);

        this.problem=problem;
        this.rng=new Random();
    }

    @Override
    public int run() {
        runMainLoop();

        int best=Integer.MIN_VALUE;
        for(int i=0;i<POP_SIZE;i++){
            int value=problem.getObjectiveFunctionValue(i);
            best=value<best?value:best;
        }

        return best;
    }

    protected abstract void runMainLoop();
}
