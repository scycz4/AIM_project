package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Arrays;

public abstract class Replacement {
    public void doReplacement(Problem problem, int populationSize){
        int[] newPopulation=getNextGeneration(problem,populationSize);
        Arrays.sort(newPopulation);

        for(int i=0;i<populationSize;i++){
            if(i!=newPopulation[i]){
                problem.copySolution(newPopulation[i],i );
            }
        }
    }

    protected abstract int[] getNextGeneration(Problem problem,int populationSize);
}
