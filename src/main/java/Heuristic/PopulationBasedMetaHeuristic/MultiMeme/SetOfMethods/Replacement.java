package Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods;

import Problem.Problem;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Replacement {

    public void doReplacement(Problem problem,int populationSize){
        int[] newPopulation=getNextGeneration(problem,populationSize);
        Arrays.sort(newPopulation);

        for(int i=0;i<populationSize;i++){
            if(i!=newPopulation[i]){
                problem.copySolution(newPopulation[i],i );
            }
        }
    }

    protected int[] getNextGeneration(Problem oProblem, int iPopulationSize) {

        // total population size is size of parent population plus size of offspring population
        int iTotalPopulationSize = iPopulationSize << 1;

        // offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
        int[] aiOffpsringMemoryIndices = IntStream.range(iPopulationSize, iTotalPopulationSize).toArray();

        // elitism replacing worst offspring with best solution if not in offspring
        int[] adTotalPopulationCosts = new int[iTotalPopulationSize];

        int dBestSolutionCost = Integer.MIN_VALUE;
        int dWorstOffspringCost =Integer.MAX_VALUE;
        int bestIndex = -1;
        int worstOffspringIndex = -1;

        // evaluate the objective function value (cost) of each solution from both parent and offspring populations
        for(int iMemoryIndex = 0; iMemoryIndex < iTotalPopulationSize; iMemoryIndex++) {

            int dSolutionCost = oProblem.getObjectiveFunctionValue(iMemoryIndex);
            adTotalPopulationCosts[iMemoryIndex] = dSolutionCost;

            // update index of best solution, favouring offspring solutions
            if( dSolutionCost >= dBestSolutionCost ) {
                dBestSolutionCost = dSolutionCost;
                bestIndex = iMemoryIndex;
            }

            // keep track of the worst solution in the offspring population
            if( iMemoryIndex >= iPopulationSize && dSolutionCost < dWorstOffspringCost) {

                worstOffspringIndex = iMemoryIndex;
                dWorstOffspringCost = dSolutionCost;
            }
        }

        // if best solution is in parent population, replace worst in offspring with best from parents
        if(bestIndex < iPopulationSize) {

            aiOffpsringMemoryIndices[worstOffspringIndex - iPopulationSize] = bestIndex;
        }

        // return array of memory locations for replacement
        return aiOffpsringMemoryIndices;
    }
}
