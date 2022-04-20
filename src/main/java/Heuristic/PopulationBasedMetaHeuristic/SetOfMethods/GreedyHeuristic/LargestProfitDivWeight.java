package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

/**
 * this class will greedy search for the items which has the largest profit per weight and flip that bit until it is overweight
 */
public class LargestProfitDivWeight extends PopulationHeuristic {
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public LargestProfitDivWeight(Problem problem) {
        super(problem);
    }

    /**
     * apply heuristic, select the unchosen item and flip that bit, until traversal all the bit.
     * If it is overweight, then put back the selected items and go to the next one
     * @param index the solution to be applied this heuristic
     */
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
