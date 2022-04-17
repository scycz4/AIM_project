package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

public class OnePointCrossover extends CrossoverHeuristic{
    public OnePointCrossover(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        for(int j=0;j<problem.getIntensityOfMutation();j++){
            int point= random.nextInt(problem.getNumberOfVariables());
            problem.copySolution(parent1,child1);
            problem.copySolution(parent2,child2);

            if(random.nextDouble()<CROSSOVER_PROBABILITY){
                for(int i=0;i<point;i++){
                    problem.exchangeBits(child1,child2,i);
                }
            }
        }
    }
}
