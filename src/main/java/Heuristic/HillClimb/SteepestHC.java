package Heuristic.HillClimb;

import Heuristic.HeuristicMethods;
import Problem.Problem;
import Problem.Solution;

public class SteepestHC extends HeuristicMethods {
    @Override
    public void applyHeuristic(Problem problem) {
        double bestEval= problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        boolean improved=false;
        int bestIndex=-1;
        int len=problem.getNumberOfVariables(CURRENT_SOLUTION_INDEX);
        for(int j=0;j<len;j++){
            problem.bitFlip(j);
            double tmpEval=problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
            if(tmpEval>bestEval){
                bestIndex=j;
                bestEval=tmpEval;
                improved=true;
            }
            problem.bitFlip(j);
        }
        if(improved){
            problem.bitFlip(bestIndex);
        }
    }
}
