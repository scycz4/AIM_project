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
            problem.bitFlip(index,random.nextInt(problem.getNumberOfVariables()));
        }
    }
}
