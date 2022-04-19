package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public class LargestProfitDivWeight extends PopulationHeuristic {
    public LargestProfitDivWeight(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        int[] indexes=problem.getSortedLargestProfitDivWeightIndexArray(index);
        for(int i=0;i< problem.getNumberOfVariables();i++){
            if(!problem.getOneBitOfSolution(index,i)){
                problem.bitFlip(index,indexes[i]);
                if(problem.isOverWeight(index)){
                    problem.bitFlip(index,indexes[i]);
                }
            }
        }
    }
}
