package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

/**
 * parents generated new children by crossover class
 */
public abstract class CrossoverHeuristic {
    protected Problem problem;
    protected Random random;
    //the rate of whether to do crossover
    protected double crossoverProbability =0.95;

    /**
     * create a crossover object
     * @param problem the problem need to be solved
     */
    public CrossoverHeuristic(Problem problem){
        this.problem=problem;
        this.random=problem.getRandom();
    }

    /**
     * apply crossover
     * @param parent1 the parent
     * @param parent2 the parent
     * @param child1 the child solution need to be generated
     * @param child2 the child solution need to be generated
     */
    public abstract void applyHeuristic(int parent1,int parent2,int child1,int child2);
}
