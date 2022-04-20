package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

/**
 * this class will search the space randomly
 */
public abstract class RMHC extends HillClimb {
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public RMHC(Problem problem) {
        super(problem);
    }

    /**
     * apply the heuristic for DoS times. search for the entire solution items randomly
     * @param index the index of solution
     */
    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getIntensityOfMutation()*problem.getNumberOfVariables();i++){
            double current=problem.getObjectiveFunctionValue(index);
            double origin=current;

            int point=random.nextInt(problem.getNumberOfVariables());
            this.problem.bitFlip(index,point);
            double candidate = deltaEvaluation(index);

            if(acceptMove(current,candidate)){
                current = candidate;
            }else{
                problem.bitFlip(index,point);
            }

            if(origin>=deltaEvaluation(index)){
                break;
            }
        }
    }
}
