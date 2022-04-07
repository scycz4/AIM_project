package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class NDHC extends PopulationHeuristic {
    public NDHC(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index){
        int bestEval=problem.getObjectiveFunctionValue(index);
        int origin=bestEval;
        for(int j=0;j<problem.getIntensityOfMutation();j++){
            for(int i=0;i<problem.getNumberOfVariables();i++){
                problem.bitFlip(index,i);
                int tmpEval=problem.getObjectiveFunctionValue(index);
                if(acceptMove(bestEval,tmpEval)){
                    bestEval=tmpEval;
                }else{
                    problem.bitFlip(index,i);
                }
            }
            if(origin>=problem.getObjectiveFunctionValue(index)){
                break;
            }
        }

    }

    public abstract boolean acceptMove(int current,int candidate);
}
