package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Problem.Problem;

import java.util.Random;

/**
 * mutate a solution according to last bit of solution
 */
public class BoundaryMutation extends Mutation{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public BoundaryMutation(Problem problem) {
        super(problem);
    }

    /**
     * apply the heuristic for IoM times. BoundaryMutation flips the bit according to last bit.
     * If a random generated mutation rate is greater than 0.5,
     * then flip the random generated bit while the last bit is 1. Else, if less than 0.5,
     * then flip the random generated bit while the last bit is 0
     * @param index the index of solution
     */
    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            int j=random.nextInt(problem.getNumberOfVariables());
            double mutation=random.nextDouble();
            if(mutation>0.5){
                if(problem.getLastBitOfSolution(index)){
                    problem.bitFlip(index,j);

                }
            }else {
                if(!problem.getLastBitOfSolution(index)){
                    problem.bitFlip(index,j);
                }
            }
        }
    }
}
