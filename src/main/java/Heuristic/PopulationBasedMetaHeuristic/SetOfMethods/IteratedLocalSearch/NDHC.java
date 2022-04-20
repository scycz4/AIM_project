package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

/**
 * this class will search the space in order
 */
public abstract class NDHC extends HillClimb{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public NDHC(Problem problem) {
        super(problem);
    }

    /**
     * apply the heuristic for DoS times. search for the entire solution items in order
     * @param index the index of solution
     */
    @Override
    public void applyHeuristic(int index){
        double bestEval=problem.getObjectiveFunctionValue(index);
        double origin=bestEval;
        for(int j=0;j<problem.getIntensityOfMutation();j++){
            for(int i=0;i<problem.getNumberOfVariables();i++){
                problem.bitFlip(index,i);
                double tmpEval=deltaEvaluation(index);
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
