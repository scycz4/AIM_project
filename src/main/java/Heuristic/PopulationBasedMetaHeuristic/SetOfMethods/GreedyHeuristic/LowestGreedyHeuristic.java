package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Arrays;
import java.util.Random;

public class LowestGreedyHeuristic extends PopulationHeuristic {
    public LowestGreedyHeuristic(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        int[] indexes=problem.getSortedLargestProfitIndexArray(index);

        for(int i=problem.getNumberOfVariables()-1;i>=0;i--){
            if(!problem.getOneBitOfSolution(index,i)){
                problem.bitFlip(index,indexes[i]);
                if(problem.isOverWeight(index)){
                    problem.bitFlip(index,indexes[i]);
                }
            }
        }
    }
}
