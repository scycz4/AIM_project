package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * this class will replace the worst index solution with
 * a random index in both parent and child
 */
public class ReplacementWithRandomToWorst extends Replacement{
    /**
     * create object
     * @param problem problem need to be solved
     * @param populationSize size of population
     */
    public ReplacementWithRandomToWorst(Problem problem, int populationSize) {
        super(problem, populationSize);
    }

    /**
     * search the entire generation (both the parent and child) for the worst
     * solution then replace it if the worst solution is in offspring,
     * then return offspring
     * @return
     */
    @Override
    protected int[] getNextGeneration() {
        int totalPopulationSize = populationSize << 1;

        // offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
        int[] offspringIndices = IntStream.range(populationSize, totalPopulationSize).toArray();

        double[] populationCosts = new double[totalPopulationSize];

        double worstOffspringCost =Double.POSITIVE_INFINITY;
        int worstOffspringIndex = -1;

        for(int index = 0; index < totalPopulationSize; index++) {

            double currentCost = problem.getObjectiveFunctionValue(index);
            populationCosts[index] = currentCost;

            if( index >= populationSize && currentCost < worstOffspringCost) {

                worstOffspringIndex = index;
                worstOffspringCost = currentCost;
            }
        }
        int index=problem.getRandom().nextInt(populationSize);
        // if best solution is in parent population, replace worst in offspring with random from parents and children
        if(index < populationSize) {
            offspringIndices[worstOffspringIndex - populationSize] = index;
        }

        return offspringIndices;
    }
}
