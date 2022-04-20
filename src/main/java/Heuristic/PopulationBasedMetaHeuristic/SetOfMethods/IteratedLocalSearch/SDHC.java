package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;


/**
 * this class will search the space and move to the best performance solution
 */
public abstract class SDHC extends HillClimb {
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public SDHC(Problem problem) {
        super(problem);
    }

    /**
     * apply the heuristic for DoS times. search for the entire solution items and flip the bit to get the best objective value
     * @param index the index of solution
     */
    @Override
    public void applyHeuristic(int index) {
        for(int k=0;k<problem.getDepthOfSearch();k++){
            double candidate= this.problem.getObjectiveFunctionValue(index);
            double current = candidate;
            double origin=current;
            int i = -1;
            for (int j = 0; j < this.problem.getNumberOfVariables(); j++) {
                this.problem.bitFlip(index,j);
                candidate = deltaEvaluation(index);
                if (acceptMove(current, candidate)) {
                    i = j;
                    current = candidate;
                }
                this.problem.bitFlip(index,j);
            }
            if (i != -1) {
                this.problem.bitFlip(index,i);
            }
            if(origin>=deltaEvaluation(index)){
                break;
            }
        }
    }
}
