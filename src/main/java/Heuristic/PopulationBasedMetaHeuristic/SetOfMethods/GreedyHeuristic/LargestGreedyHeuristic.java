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
            if(!problem.getOneBitOfSolution(index,i)){
                double current=problem.getObjectiveFunctionValue(index);
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
