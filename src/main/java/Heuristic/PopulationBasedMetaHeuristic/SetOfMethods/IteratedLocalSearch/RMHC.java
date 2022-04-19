package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class RMHC extends HillClimb {
    public RMHC(Problem problem) {
        super(problem,new Random());
    }

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
