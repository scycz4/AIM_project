package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Problem.Problem;

public abstract class RuinRecreate {
    protected int IntensityOfMutation;
    protected Problem problem;

    public RuinRecreate(Problem problem){
        this.problem=problem;
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

    class IndexValue{
        final int i;
        final int v;

        public IndexValue(int i,int v){
            this.i=i;
            this.v=v;
        }
    }

    public abstract void applyHeuristic(int populationSize);

}
