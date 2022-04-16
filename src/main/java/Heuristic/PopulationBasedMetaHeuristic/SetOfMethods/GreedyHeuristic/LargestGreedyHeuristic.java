package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public class LargestGreedyHeuristic extends PopulationHeuristic {
    public LargestGreedyHeuristic(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        int[] indexes=problem.getSortedLargestProfitIndexArray(index);
        for(int i=0;i< problem.getNumberOfVariables();i++){
            problem.bitFlip(index,indexes[i]);
            if(problem.getWeight(index)>problem.getBoundary(index)){
                problem.bitFlip(index,indexes[i]);
            }
        }

    }
}
