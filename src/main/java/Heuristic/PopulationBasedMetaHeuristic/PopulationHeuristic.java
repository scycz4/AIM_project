package Heuristic.PopulationBasedMetaHeuristic;

import Problem.Problem;

public abstract class PopulationHeuristic {
    protected final Problem problem;

    public PopulationHeuristic(Problem problem){
        this.problem=problem;
    }

    public abstract void applyHeuristic(int var1);
}
