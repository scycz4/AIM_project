package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class RMHC extends PopulationHeuristic {
    public RMHC(Problem problem) {
        super(problem,new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getIntensityOfMutation()*problem.getNumberOfItems();i++){
            int current=problem.getObjectiveFunctionValue(index);
            int origin=current;

            int point=random.nextInt(problem.getNumberOfVariables());
            this.problem.bitFlip(index,point);
            int candidate = this.problem.getObjectiveFunctionValue(index);

            if(acceptMove(current,candidate)){
                current = candidate;
            }else{
                problem.bitFlip(index,point);
            }

            if(origin>=problem.getObjectiveFunctionValue(index)){
                break;
            }
        }
    }

    public abstract boolean acceptMove(int current,int candidate);
}
