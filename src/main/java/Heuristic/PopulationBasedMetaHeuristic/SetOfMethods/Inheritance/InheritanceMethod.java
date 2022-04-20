package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

import java.util.Random;

public abstract class InheritanceMethod {
    protected Problem problem;
    protected Random random;
    protected int populationSize;
    public InheritanceMethod(Problem problem,int populationSize){
        this.problem=problem;
        random=problem.getRandom();
        this.populationSize=populationSize;
    }

    public abstract void performMemeticInheritance(int p1,int p2,int c1,int c2);
}
