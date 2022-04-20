package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * replace the offspring with chosen solution
 */
public abstract class Replacement {
    protected Problem problem;
    protected int populationSize;

    /**
     * create object
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     */
    public Replacement(Problem problem,int populationSize) {
        this.problem=problem;
        this.populationSize=populationSize;
    }

    /**
     * replace the solutions from 0 to population size with solution from next generation
     */
    public void doReplacement(){
        int[] newPopulation=getNextGeneration();
        Arrays.sort(newPopulation);
        for(int i=0;i<populationSize;i++){
            if(i!=newPopulation[i]){
                problem.copySolution(newPopulation[i],i);
            }
        }
    }

    /**
     * get the indices of next generation
     * @return the indices of next generation
     */
    protected abstract int[] getNextGeneration();
}
