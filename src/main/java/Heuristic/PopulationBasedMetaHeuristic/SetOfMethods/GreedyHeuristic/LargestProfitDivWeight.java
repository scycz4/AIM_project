package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public class LargestProfitDivWeight extends PopulationHeuristic {
    public LargestProfitDivWeight(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int index) {
        int[] indexes=problem.getSortedLargestProfitDivWeightIndexArray(index);
        for(int i=0;i< problem.getNumberOfVariables();i++){
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
