package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.ArrayList;
import java.util.Random;

public class ReducedSurrogateCrossover extends CrossoverHeuristic{
    public ReducedSurrogateCrossover(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int p1, int p2, int c1, int c2) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            int[] diff=problem.diffGenePoint(p1,p2);
            if(diff.length==0){
                return;
            }else{
                int point=diff[new Random().nextInt(diff.length)];
                problem.copySolution(p1,c1);
                problem.copySolution(p2,c2);

                if(random.nextDouble()<CROSSOVER_PROBABILITY){
                    for(int j=0;j<point;j++){
                        problem.exchangeBits(c1,c2,j);
                    }
                }
            }
        }

    }
}
