package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Problem.Problem;

public class DestroyLowestProfitDivWeightSolution extends RuinRecreate{
    public DestroyLowestProfitDivWeightSolution(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int index) {
        ruin(index);
        recreate(index);
    }

    @Override
    protected void ruin(int index) {
        indices=new int[this.IntensityOfMutation];
        for(int i=0;i<this.IntensityOfMutation;i++){
            int j=problem.getBitWithLowestProfitDivWeight(index);
            problem.destroyBit(index,j);
            indices[i]=j;
        }
    }
}
