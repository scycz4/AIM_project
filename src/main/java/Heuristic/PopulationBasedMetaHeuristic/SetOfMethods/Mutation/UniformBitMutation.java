package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Problem.Problem;

/**
 * the class will mutate the solution by traversal entire solution
 */
public class UniformBitMutation extends Mutation {

    private double mutationRate;

    /**
     * create object
     * @param problem problem need to be solved
     */
    public UniformBitMutation(Problem problem){
        super(problem);
        this.mutationRate =1.0/problem.getNumberOfVariables();
    }

    /**
     * apply heuristic for IoM times. traversals all bit and decides whether to
     * flip bit according to mutation rate. The mutation rate is 1/numberOfVariables.
     * If random generated double number is less than mutation rate, then do bit flip.
     * @param index the index of solution
     */
    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i< problem.getIntensityOfMutation();i++){
            for(int j=0;j<problem.getNumberOfVariables();j++){
                if(random.nextDouble()< mutationRate){
                    problem.bitFlip(index,j);
                }
            }
        }
    }
}
