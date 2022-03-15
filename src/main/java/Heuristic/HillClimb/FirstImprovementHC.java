package Heuristic.HillClimb;

import Heuristic.HeuristicMethods;
import Problem.Problem;
import Problem.Solution;

public class FirstImprovementHC extends HeuristicMethods {

    @Override
    public void applyHeuristic(Problem problem) {
        int bestEval=problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        for(int j=0;j<problem.getNumberOfVariables(CURRENT_SOLUTION_INDEX);j++){
            problem.bitFlip(CURRENT_SOLUTION_INDEX,j);
            int tmpEval= problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
            if(tmpEval>bestEval)
                bestEval=tmpEval;
            else
                problem.bitFlip(CURRENT_SOLUTION_INDEX,j);
        }
    }
}
