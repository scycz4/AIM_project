package Heuristic.Mutation;

import Heuristic.HeuristicMethods;
import Problem.Problem;
import Problem.Solution;

import java.util.Random;

public class RandomBitFlip extends HeuristicMethods {
    @Override
    public void applyHeuristic(Problem problem) {
        int bitIndex=new Random().nextInt(problem.getSolutionAsString(CURRENT_SOLUTION_INDEX).length());
        problem.bitFlip(bitIndex);
    }
}
