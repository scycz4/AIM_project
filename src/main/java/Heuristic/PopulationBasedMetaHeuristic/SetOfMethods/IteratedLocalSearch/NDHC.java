package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class NDHC extends HillClimb{
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
                int tmpEval=deltaEvaluation(index);
                if(acceptMove(bestEval,tmpEval)){
                    bestEval=tmpEval;
                }else{
                    problem.bitFlip(index,i);
                }
            }
            if(origin>=deltaEvaluation(index)){
                break;
            }
        }

    }
}
