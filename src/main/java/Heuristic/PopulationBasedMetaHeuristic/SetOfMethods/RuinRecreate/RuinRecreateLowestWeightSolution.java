package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Problem.Problem;

/**
 * this class will ruin the solution's item from item with
 * the lowest weight
 */
public class RuinRecreateLowestWeightSolution extends RuinRecreate{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public RuinRecreateLowestWeightSolution(Problem problem) {
        super(problem);
    }

    /**
     * apply the ruin and recreate heuristic
     * @param index index of solution
     */
    @Override
    public void applyHeuristic(int index) {
        ruin(index);
        recreate(index);
    }

    /**
     * ruin the first IoM of items which has the lowest weight
     * @param index index of solution
     */
    @Override
    protected void ruin(int index) {
        indices=new int[this.IntensityOfMutation];
        for(int i=0;i<this.IntensityOfMutation;i++){
            int j=problem.getBitWithLowestWeight(index);
            problem.destroyBit(index,j);
            indices[i]=j;
        }
    }
}
