package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Problem.Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class DestroyHighestSolution extends RuinRecreate{
    public DestroyHighestSolution(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int populationSize) {
        IndexValue[] obj=new IndexValue[populationSize];
        for(int i=0;i<populationSize;i++){
            obj[i]=new IndexValue(i,problem.getWeight(i));
        }
        Arrays.sort(obj, new Comparator<IndexValue>() {
            @Override
            public int compare(IndexValue o1, IndexValue o2) {
                return o2.v-o1.v;
            }
        });
        for(int i=0;i<this.IntensityOfMutation;i++){
            problem.reset(obj[i].i);
        }

    }
}
