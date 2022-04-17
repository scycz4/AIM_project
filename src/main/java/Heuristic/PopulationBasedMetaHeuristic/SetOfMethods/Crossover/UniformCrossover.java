package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

public class UniformCrossover extends CrossoverHeuristic{
    public UniformCrossover(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            problem.copySolution(parent1, child1);
            problem.copySolution(parent2, child2);
            for (int j = 0; j < problem.getNumberOfVariables(); j++) {
                if (random.nextDouble() < 0.5) {
                    problem.exchangeBits(child1, child2, j);
                }
            }
        }
    }
}
