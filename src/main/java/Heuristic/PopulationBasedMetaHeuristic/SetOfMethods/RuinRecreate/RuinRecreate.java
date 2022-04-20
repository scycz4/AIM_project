package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.Mutation;
import Problem.Problem;

import java.util.Random;

public abstract class RuinRecreate extends Mutation {
    protected int IntensityOfMutation;
    protected int[] indices;

    public RuinRecreate(Problem problem){
        super(problem,new Random());
    }

    public void setIntensityOfMutation(double intensityOfMutation) {
        if(intensityOfMutation>=0.0&&intensityOfMutation<0.2){
            this.IntensityOfMutation=1;
        }else if(intensityOfMutation<0.4){
            this.IntensityOfMutation=2;
        }else if(intensityOfMutation<0.6){
            this.IntensityOfMutation=3;
        }else if(intensityOfMutation<0.8){
            this.IntensityOfMutation=4;
        }else if(intensityOfMutation<1.0){
            this.IntensityOfMutation=5;
        }else if(intensityOfMutation==1.0){
            this.IntensityOfMutation=6;
        }
    }

    public abstract void applyHeuristic(int index);

    protected abstract void ruin(int index);
    protected void recreate(int index){
        for(int i=0;i<indices.length;i++){
            if(random.nextDouble()<0.4){
                problem.bitFlip(index,indices[i]);
            }
        }
    }

}
