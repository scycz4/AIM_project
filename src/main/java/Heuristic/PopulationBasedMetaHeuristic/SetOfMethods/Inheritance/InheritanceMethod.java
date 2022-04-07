package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

import java.util.Random;

public abstract class InheritanceMethod {
    protected Problem problem;
    protected Random random;
    public InheritanceMethod(Problem problem){
        this.problem=problem;
        random=new Random();
    }

    public abstract void performMemeticInheritance(int p1,int p2,int c1,int c2, int populationSize);
}
