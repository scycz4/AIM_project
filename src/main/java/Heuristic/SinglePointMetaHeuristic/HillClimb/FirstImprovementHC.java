package Heuristic.SinglePointMetaHeuristic.HillClimb;

import Heuristic.HeuristicMethods;
import Problem.Problem;

public class FirstImprovementHC extends HeuristicMethods {

    @Override
    public void applyHeuristic(Problem problem) {
        double bestEval=problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        for(int j=0;j<problem.getNumberOfVariables();j++){
            problem.bitFlip(CURRENT_SOLUTION_INDEX,j);
            double tmpEval= problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
            if(tmpEval>bestEval)
                bestEval=tmpEval;
            else
                problem.bitFlip(CURRENT_SOLUTION_INDEX,j);
        }
    }
}
