package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Problem.Problem;

/**
 * this class will ruin the solution's item from item with
 * the highest weight
 */
public class RuinRecreateHighestWeightSolution extends RuinRecreate{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public RuinRecreateHighestWeightSolution(Problem problem) {
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
     * ruin the first IoM of items which has the highest weight
     * @param index index of solution
     */
    @Override
    protected void ruin(int index) {
        indices=new int[this.IntensityOfMutation];
        for(int i=0;i<this.IntensityOfMutation;i++){
            int j=problem.getBitWithHighestWeight(index);
            problem.destroyBit(index,j);
            indices[i]=j;
        }
    }
}
