package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Problem.Problem;

import java.util.Random;

public class BoundaryMutation extends Mutation{
    public BoundaryMutation(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            int j=random.nextInt(problem.getNumberOfVariables());
            double mutation=random.nextDouble();
            if(mutation>0.5){
                if(problem.getLastBitOfSolution(index)){
                    problem.bitFlip(index,j);
//                    if(problem.isOverWeight(index)) {
//                        problem.bitFlip(index,j);
//                    }
                }
            }else {
                if(!problem.getLastBitOfSolution(index)){
                    problem.bitFlip(index,j);
//                    if(problem.isOverWeight(index)) {
//                        problem.bitFlip(index,j);
//                    }
                }
            }
        }
    }
}
