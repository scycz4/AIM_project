package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.HillClimb;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.SDHC_IE;
import Problem.Problem;

public class DestroyHighestProfitDivWeightSolution extends RuinRecreate{
    public DestroyHighestProfitDivWeightSolution(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int index) {
        //ruin
        ruin(index);

        //recreate
        recreate(index);
    }

    @Override
    public void ruin(int index) {
        indices=new int[this.IntensityOfMutation];
        for(int i=0;i<this.IntensityOfMutation;i++){
            int j=problem.getBitWithHighestProfitDivWeight(index);
            problem.destroyBit(index,j);
            indices[i]=j;
        }
    }
}
