package Heuristic.SinglePointMetaHeuristic.Mutation;

import Heuristic.HeuristicMethods;
import Problem.Problem;

import java.util.Random;

public class RandomBitFlip extends HeuristicMethods {
    @Override
    public void applyHeuristic(Problem problem) {
        int bitIndex=problem.getRandom().nextInt(problem.getSolutionAsString(CURRENT_SOLUTION_INDEX).length());
        problem.bitFlip(bitIndex);
    }
}
