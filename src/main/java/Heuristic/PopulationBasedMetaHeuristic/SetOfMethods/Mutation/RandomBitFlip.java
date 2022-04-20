package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Problem.Problem;

/**
 * this class will mutate the random index of solution
 */
public class RandomBitFlip extends Mutation {

    /**
     * create object
     * @param problem the problem need to be solved
     */
    public RandomBitFlip(Problem problem) {
        super(problem);
    }

    /**
     * apply heuristic IoM times. choose an index randomly, then apply bit flip
     * @param index index of solution
     */
    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            int j=random.nextInt(problem.getNumberOfVariables());
            problem.bitFlip(index,j);
        }
    }
}
