package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.stream.IntStream;

public class ReplacementWithElitists extends Replacement{
    @Override
    protected int[] getNextGeneration(Problem problem, int populationSize) {
        int iTotalPopulationSize = populationSize << 1;

        // offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
        int[] aiOffpsringMemoryIndices = IntStream.range(populationSize, iTotalPopulationSize).toArray();

        // elitism replacing worst offspring with best solution if not in offspring
        int[] adTotalPopulationCosts = new int[iTotalPopulationSize];

        int dBestSolutionCost = Integer.MIN_VALUE;
        int dWorstOffspringCost =Integer.MAX_VALUE;
        int bestIndex = -1;
        int worstOffspringIndex = -1;

        // evaluate the objective function value (cost) of each solution from both parent and offspring populations
        for(int iMemoryIndex = 0; iMemoryIndex < iTotalPopulationSize; iMemoryIndex++) {

            int dSolutionCost = problem.getObjectiveFunctionValue(iMemoryIndex);
            adTotalPopulationCosts[iMemoryIndex] = dSolutionCost;

            // update index of best solution, favouring offspring solutions
            if( dSolutionCost >= dBestSolutionCost ) {
                dBestSolutionCost = dSolutionCost;
                bestIndex = iMemoryIndex;
            }

            // keep track of the worst solution in the offspring population
            if( iMemoryIndex >= populationSize && dSolutionCost < dWorstOffspringCost) {

                worstOffspringIndex = iMemoryIndex;
                dWorstOffspringCost = dSolutionCost;
            }
        }

        aiOffpsringMemoryIndices[worstOffspringIndex - populationSize] = bestIndex;


        // return array of memory locations for replacement
        return aiOffpsringMemoryIndices;
    }
}
