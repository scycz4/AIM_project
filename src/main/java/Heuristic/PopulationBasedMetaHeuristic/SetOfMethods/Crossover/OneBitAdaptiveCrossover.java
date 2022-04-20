package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

/**
 * this class will apply different crossover heuristic according to
 * the last bit of the current solution
 */
public class OneBitAdaptiveCrossover extends CrossoverHeuristic{
    /**
     * create this object
     * @param problem the problem need to be solved
     */
    public OneBitAdaptiveCrossover(Problem problem) {
        super(problem);
    }

    /**
     * apply the heuristic and choose one of two heuristics for IoM times, and the strategy to choose heuristic is to compare
     * onr last bit of two parents
     * @param parent1 the parent
     * @param parent2 the parent
     * @param child1 the child solution need to be generated
     * @param child2 the child solution need to be generated
     */
    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        CrossoverHeuristic twoPointCrossover=new TwoPointCrossover(problem);
        CrossoverHeuristic uniformCrossover=new UniformCrossover(problem);

        for(int i=0;i<problem.getIntensityOfMutation();i++){
            if(problem.getLastBitOfSolution(parent1)==problem.getLastBitOfSolution(parent2)&&problem.getLastBitOfSolution(parent1)){
                twoPointCrossover.applyHeuristic(parent1, parent2, child1, child2);

            }else if(problem.getLastBitOfSolution(parent1)==problem.getLastBitOfSolution(parent2)&&!problem.getLastBitOfSolution(parent1)){
                uniformCrossover.applyHeuristic(parent1, parent2, child1, child2);
            }else{
                if(random.nextBoolean()){
                    uniformCrossover.applyHeuristic(parent1, parent2, child1, child2);
                }
                else {
                    twoPointCrossover.applyHeuristic(parent1, parent2, child1, child2);
                }
            }
        }

    }
}
