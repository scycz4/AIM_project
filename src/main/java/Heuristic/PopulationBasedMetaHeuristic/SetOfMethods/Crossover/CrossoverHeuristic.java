package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

public abstract class CrossoverHeuristic {
    protected Problem problem;

    public CrossoverHeuristic(Problem problem){
        this.problem=problem;
    }

    public abstract void applyHeuristic(int var1,int var2,int var3,int var4);
}
