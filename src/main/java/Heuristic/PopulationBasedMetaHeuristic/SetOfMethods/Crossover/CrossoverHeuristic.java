package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

public abstract class CrossoverHeuristic {
    protected Problem problem;
    protected Random random;
    protected double CROSSOVER_PROBABILITY=0.95;

    public CrossoverHeuristic(Problem problem){
        this.problem=problem;
        this.random=new Random();
    }

    public abstract void applyHeuristic(int var1,int var2,int var3,int var4);
}
