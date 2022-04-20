package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

/**
 * this class will traversal all the bits and choose whether to exchange bits according to a constant probability
 */
public class UniformCrossover extends CrossoverHeuristic{
    /**
     * create object
     * @param problem
     */
    public UniformCrossover(Problem problem) {
        super(problem);
    }

    /**
     * apply this heuristic for IoM times. Traversal all the bits in children, and if randomly generated
     * double is less than 0.5, then exchange the bits between two children
     * @param parent1 the parent
     * @param parent2 the parent
     * @param child1 the child solution need to be generated
     * @param child2 the child solution need to be generated
     */
    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {

        problem.copySolution(parent1, child1);
        problem.copySolution(parent2, child2);
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            for (int j = 0; j < problem.getNumberOfVariables(); j++) {
                if (random.nextDouble() < 0.5) {
                    problem.exchangeBits(child1, child2, j);
//                    if(problem.isOverWeight(child1)||problem.isOverWeight(child2)){
//                        problem.exchangeBits(child1,child2,j);
//                    }
                }
            }
        }
    }
}
