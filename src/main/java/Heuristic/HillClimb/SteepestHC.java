package Heuristic.HillClimb;

import Heuristic.HeuristicMethods;
import Problem.Problem;

public class SteepestHC extends HeuristicMethods {
    public SteepestHC(){
        super();
    }


    @Override
    public void applyHeuristic(Problem problem) {
        double bestEval= problem.getObjectiveFunctionValue();
        boolean improved=false;
        int bestIndex=-1;
        int len=problem.getSolutionAsString().length();
        for(int j=0;j<len;j++){
            problem.bitFlip(j);
            double tmpEval=problem.getObjectiveFunctionValue();
            if(tmpEval>bestEval&&problem.getWeight()<=problem.getBoundary()){
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
