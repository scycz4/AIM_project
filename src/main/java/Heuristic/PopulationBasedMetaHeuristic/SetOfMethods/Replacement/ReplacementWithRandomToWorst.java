package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement;

import Problem.Problem;

import java.util.Random;
import java.util.stream.IntStream;

public class ReplacementWithRandomToWorst extends Replacement{
    public ReplacementWithRandomToWorst(Problem problem, int populationSize) {
        super(problem, populationSize);
    }

    @Override
    protected int[] getNextGeneration() {
        int totalPopulationSize = populationSize << 1;

        // offspring indices are from 'populationSize' inclusive to 'populationSize * 2' exclusive.
        int[] offspringIndices = IntStream.range(populationSize, totalPopulationSize).toArray();

        // elitism replacing worst offspring with best solution if not in offspring
        double[] populationCosts = new double[totalPopulationSize];

        double worstOffspringCost =Double.POSITIVE_INFINITY;
        int worstOffspringIndex = -1;

        // evaluate the objective function value (cost) of each solution from both parent and offspring populations
        for(int index = 0; index < totalPopulationSize; index++) {

            double currentCost = problem.getObjectiveFunctionValue(index);
            populationCosts[index] = currentCost;

            // keep track of the worst solution in the offspring population
            if( index >= populationSize && currentCost < worstOffspringCost) {

                worstOffspringIndex = index;
                worstOffspringCost = currentCost;
            }
        }
        int index=new Random().nextInt(totalPopulationSize);
        // if best solution is in parent population, replace worst in offspring with best from parents
        if(index < populationSize) {
            offspringIndices[worstOffspringIndex - populationSize] = index;
        }

        // return array of memory locations for replacement
        return offspringIndices;
    }
}
