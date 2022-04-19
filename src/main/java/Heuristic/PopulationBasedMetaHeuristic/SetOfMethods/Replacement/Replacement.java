package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public abstract class Replacement {
    protected Problem problem;
    protected int populationSize;
    public Replacement(Problem problem,int populationSize) {
        this.problem=problem;
        this.populationSize=populationSize;
    }
    public void doReplacement(){
        int[] newPopulation=getNextGeneration();
//        newPopulation=Arrays.stream(newPopulation).boxed().sorted(Comparator.reverseOrder())
//                .mapToInt(Integer::intValue).toArray();
        Arrays.sort(newPopulation);
        for(int i=0;i<populationSize;i++){
            if(i!=newPopulation[i]){
                problem.copySolution(newPopulation[i],i);
            }
        }
    }

    protected abstract int[] getNextGeneration();
}
