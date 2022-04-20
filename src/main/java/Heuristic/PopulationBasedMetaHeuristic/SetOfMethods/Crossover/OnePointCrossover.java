package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

/**
 * the class will find a random point in solution and exchange all the bit between 0 and point
 */
public class OnePointCrossover extends CrossoverHeuristic{
    /**
     * create an object
     * @param problem the problem need to be solved
     */
    public OnePointCrossover(Problem problem) {
        super(problem);
    }

    /**
     * apply the heuristic for IoM times, randomly generate a point between 0 and number of
     * (variables-1), if the random genrated double number is less that crossoverProbability,
     * then exchange all the bits whose index is between 0 and point in children
     * @param parent1 the parent
     * @param parent2 the parent
     * @param child1 the child solution need to be generated
     * @param child2 the child solution need to be generated
     */
    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        problem.copySolution(parent1,child1);
        problem.copySolution(parent2,child2);
        for(int j=0;j<problem.getIntensityOfMutation();j++){
            int point= random.nextInt(problem.getNumberOfVariables());
            if(random.nextDouble()< crossoverProbability){
                for(int i=0;i<point;i++){
                    problem.exchangeBits(child1,child2,i);
//                    if(problem.isOverWeight(child1)||problem.isOverWeight(child2)){
//                        problem.exchangeBits(child1,child2,i);
//                    }
                }
            }
        }
    }
}
