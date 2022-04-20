package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * this class will replace the worst index solution with
 * the best index in both parent and child
 */
public class ReplacementWithStrongElitists extends Replacement{
    /**
     * create object
     * @param problem problem need to be solved
     * @param populationSize size of population
     */
    public ReplacementWithStrongElitists(Problem problem, int populationSize) {
        super(problem,populationSize);
    }

    /**
     * search the entire generation (both the parent and child) for the worst
     * and best solution then replace it then return offspring
     * @return the offspring
     */
    @Override
    protected int[] getNextGeneration() {
        int totalPopulationSize = populationSize << 1;

        // offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
        int[] totalIndices = IntStream.range(0, totalPopulationSize).toArray();

        // elitism replacing worst offspring with best solution if not in offspring
        double[] totalPopulationCosts = new double[totalPopulationSize];

        double bestSolutionCost = Integer.MIN_VALUE;
        double worstOffspringCost =Integer.MAX_VALUE;
        int bestIndex = -1;
        int worstOffspringIndex = -1;

        // evaluate the objective function value (cost) of each solution from both parent and offspring populations
        for(int index = 0; index < totalPopulationSize; index++) {

            double currentSolution = problem.getObjectiveFunctionValue(index);
            totalPopulationCosts[index] = currentSolution;

            // update index of best solution, favouring offspring solutions
            if( currentSolution >= bestSolutionCost ) {
                bestSolutionCost = currentSolution;
                bestIndex = index;
            }

            // keep track of the worst solution in the offspring population
            if( index >= populationSize && currentSolution < worstOffspringCost) {

                worstOffspringIndex = index;
                worstOffspringCost = currentSolution;
            }
        }

        totalIndices[worstOffspringIndex] = bestIndex;

        int[] springIndices = Arrays.copyOfRange(totalIndices, populationSize, totalPopulationSize);


        // return array of memory locations for replacement
        return springIndices;
    }
}
