package Heuristic.PopulationBasedMetaHeuristic;

import Problem.Problem;

import java.util.Random;

public abstract class PopulationHeuristic {
    protected final Problem problem;
    protected final Random random;

    public PopulationHeuristic(Problem problem,Random random){
        this.problem=problem;
        this.random=random;
    }

    public abstract void applyHeuristic(int var1);
}
