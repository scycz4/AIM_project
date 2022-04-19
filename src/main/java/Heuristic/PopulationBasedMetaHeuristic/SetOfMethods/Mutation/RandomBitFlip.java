package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public class RandomBitFlip extends Mutation {

    public RandomBitFlip(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            int j=random.nextInt(problem.getNumberOfVariables());
            problem.bitFlip(index,j);
            if(problem.isOverWeight(index)) {
                problem.bitFlip(index,j);
            }
        }
    }
}
