package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class SDHC extends HillClimb {
    public SDHC(Problem problem) {
        super(problem, new Random());
    }

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
