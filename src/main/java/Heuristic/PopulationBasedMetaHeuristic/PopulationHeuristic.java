package Heuristic.PopulationBasedMetaHeuristic;

import Problem.Problem;

import java.util.Random;

/**
 * this class is the heuristic that can be applied on population
 */
public abstract class PopulationHeuristic {
    protected final Problem problem;
    protected final Random random;

    /**
     * create object
     * @param problem the problem need to be solved
     */
    public PopulationHeuristic(Problem problem){
        this.problem=problem;
        this.random= problem.getRandom();
    }

    /**
     * apply the heuristic
     * @param index the index of solution
     */
    public abstract void applyHeuristic(int index);
}
