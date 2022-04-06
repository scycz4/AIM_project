package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate;

import Problem.Problem;

import java.util.Arrays;
import java.util.Comparator;

public class DestroyLowestSolution extends RuinRecreate{
    public DestroyLowestSolution(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int populationSize) {
        IndexValue[] obj=new IndexValue[populationSize];
        for(int i=0;i<populationSize;i++){
            obj[i]=new IndexValue(i,problem.getObjectiveFunctionValue(i));
        }
        Arrays.sort(obj, new Comparator<IndexValue>() {
            @Override
            public int compare(IndexValue o1, IndexValue o2) {
                return o1.v-o2.v;
            }
        });
        for(int i=0;i<this.IntensityOfMutation;i++){
            problem.reset(obj[i].i);
        }
    }
}
