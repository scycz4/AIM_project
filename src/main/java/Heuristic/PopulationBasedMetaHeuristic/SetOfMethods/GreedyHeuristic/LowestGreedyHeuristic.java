package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Arrays;
import java.util.Random;

public class LowestGreedyHeuristic extends PopulationHeuristic {
    public LowestGreedyHeuristic(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int index) {
        int[] indexes=problem.getSortedLargestProfitIndexArray(index);

        for(int i=problem.getNumberOfVariables()-1;i>=0;i--){
            double current=problem.getObjectiveFunctionValue(index);
            if(!problem.getOneBitOfSolution(index,i)){
                problem.bitFlip(index,indexes[i]);
                if(problem.isOverWeight(index)){
                    if(problem.deltaEvaluation(index)>current){
                        continue;
                    }
                    else{
                        problem.bitFlip(index,indexes[i]);
                    }
                }
            }
        }
    }
}
