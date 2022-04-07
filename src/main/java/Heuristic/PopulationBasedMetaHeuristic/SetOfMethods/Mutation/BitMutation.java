package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public class BitMutation extends PopulationHeuristic {

    private double mutationRate;
    public BitMutation(Problem problem){
        super(problem,new Random());
        this.mutationRate =1.0/problem.getNumberOfVariables();
    }

    @Override
    public void applyHeuristic(int solutionIndex) {
        for(int i=0;i< problem.getIntensityOfMutation();i++){
            for(int j=0;j<problem.getNumberOfVariables();j++){
                if(random.nextDouble()< mutationRate){
                    problem.bitFlip(solutionIndex,j);
                }
            }
        }
    }
}
