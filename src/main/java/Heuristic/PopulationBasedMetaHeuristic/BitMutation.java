package Heuristic.PopulationBasedMetaHeuristic;

import Problem.Problem;

import java.util.Random;

public class BitMutation extends PopulationHeuristic{

    private final double MUTATION_RATE;
    private Random random;
    public BitMutation(Problem problem){
        super(problem);
        this.MUTATION_RATE=1.0/problem.getNumberOfVariables();
        random=new Random();
    }
    @Override
    public void applyHeuristic(int solutionIndex) {
        for(int j=0;j<problem.getNumberOfVariables();j++){
            if(random.nextDouble()<MUTATION_RATE){
                problem.bitFlip(solutionIndex,j);
            }
        }
    }
}
