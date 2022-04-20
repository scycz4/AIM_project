package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.Mutation;
import Problem.Problem;

/**
 * this class will ruin a subset of solution's bits and recreate the subset
 */
public abstract class RuinRecreate extends Mutation {
    protected int IntensityOfMutation;
    protected int[] indices;
    //the rate of whether to recreate on that bit
    protected final double recreateRate=0.4;

    /**
     * create object
     * @param problem the problem need to be solved
     */
    public RuinRecreate(Problem problem){
        super(problem);
        IntensityOfMutation=problem.getIntensityOfMutation();
    }

    /**
     * apply heuristic on solution
     * @param index index of solution
     */
    public abstract void applyHeuristic(int index);

    /**
     * ruin the solution
     * @param index index of solution
     */
    protected abstract void ruin(int index);

    /**
     * recreate the solution
     * @param index index of solution
     */
    protected void recreate(int index){
        for(int i=0;i<indices.length;i++){
            if(random.nextDouble()<recreateRate){
                problem.bitFlip(index,indices[i]);
            }
        }
    }

}
