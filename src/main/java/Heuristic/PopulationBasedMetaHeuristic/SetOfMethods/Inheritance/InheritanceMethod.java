package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

import java.util.Random;

/**
 * this class will select solutions and copy the meme option into child
 */
public abstract class InheritanceMethod {
    protected Problem problem;
    protected Random random;
    protected int populationSize;

    /**
     * create object
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     */
    public InheritanceMethod(Problem problem,int populationSize){
        this.problem=problem;
        random=problem.getRandom();
        this.populationSize=populationSize;
    }

    /**
     * apply inheritance and select memes
     * @param p1 parent1
     * @param p2 parent2
     * @param c1 child1
     * @param c2 child2
     */
    public abstract void performMemeticInheritance(int p1,int p2,int c1,int c2);
}
