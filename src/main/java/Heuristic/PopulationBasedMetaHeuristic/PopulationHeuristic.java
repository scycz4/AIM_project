package Heuristic.PopulationBasedMetaHeuristic;

import Problem.Problem;

import java.util.Random;

public abstract class PopulationHeuristic {
    protected final Problem problem;
    protected final Random random;

    public PopulationHeuristic(Problem problem){
        this.problem=problem;
        this.random= problem.getRandom();
    }

    public abstract void applyHeuristic(int var1);
}
