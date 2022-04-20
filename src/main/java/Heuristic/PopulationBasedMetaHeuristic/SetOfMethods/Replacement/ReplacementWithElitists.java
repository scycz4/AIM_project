package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * this class will replace the worst index solution in offspring with
 * the best index in both parent and child
 */
public class ReplacementWithElitists extends Replacement{
    /**
     * create object
     * @param problem problem need to be solved
     * @param populationSize size of population
     */
    public ReplacementWithElitists(Problem problem, int populationSize) {
        super(problem,populationSize);
    }

    /**
     * search the entire generation (both the parent and child) for the worst
     * and best solution then replace it if the worst solution is in offspring,
     * then return offspring
     * @return the offspring
     */
    protected int[] getNextGeneration() {

        // total population size is size of parent population plus size of offspring population
        int totalPopulationSize = populationSize << 1;

        // offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
        int[] offspringIndices = IntStream.range(populationSize, totalPopulationSize).toArray();

        // elitism replacing worst offspring with best solution if not in offspring
        double[] populationCosts = new double[totalPopulationSize];

        double bestSolutionCost = Double.NEGATIVE_INFINITY;
        double worstOffspringCost =Double.POSITIVE_INFINITY;
        int bestIndex = -1;
        int worstOffspringIndex = -1;

        // evaluate the objective function value (cost) of each solution from both parent and offspring populations
        for(int index = 0; index < totalPopulationSize; index++) {

            double currentCost = problem.getObjectiveFunctionValue(index);
            populationCosts[index] = currentCost;

            // update index of best solution, favouring offspring solutions
            if( currentCost >= bestSolutionCost ) {
                bestSolutionCost = currentCost;
                bestIndex = index;
            }

            // keep track of the worst solution in the offspring population
            if( index >= populationSize && currentCost < worstOffspringCost) {

                worstOffspringIndex = index;
                worstOffspringCost = currentCost;
            }
        }

        // if best solution is in parent population, replace worst in offspring with best from parents
        if(bestIndex < populationSize) {
            offspringIndices[worstOffspringIndex - populationSize] = bestIndex;
        }

        // return array of memory locations for replacement
        return offspringIndices;
    }
}
