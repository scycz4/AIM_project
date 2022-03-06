package Heuristic.HillClimb;

import Heuristic.HeuristicMethods;
import Problem.Problem;

public class SteepestHC extends HeuristicMethods {
    public SteepestHC(){
        super();
    }


    @Override
    public void applyHeuristic(Problem problem) {
        int bestEval= problem.getObjectiveFunctionValue();
        boolean improved=false;
        for(int j=0;j<problem.getObjectiveFunctionValue();j++){

        }
    }
}
