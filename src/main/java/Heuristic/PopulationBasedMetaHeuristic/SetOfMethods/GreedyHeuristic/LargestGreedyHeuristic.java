package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

/**
 * this class will greedy search for the largest items and flip that bit until it is overweight
 */
public class LargestGreedyHeuristic extends PopulationHeuristic {
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public LargestGreedyHeuristic(Problem problem) {
        super(problem);
    }

    /**
     * apply heuristic, select the unchosen item and flip that bit, until traversal all the bit.
     * If it is overweight, then put back the selected items and go to the next one
     * @param index the solution to be applied this heuristic
     */
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
