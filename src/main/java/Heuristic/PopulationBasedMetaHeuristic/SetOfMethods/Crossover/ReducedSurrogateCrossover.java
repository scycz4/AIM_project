package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.ArrayList;
import java.util.Random;

public class ReducedSurrogateCrossover extends CrossoverHeuristic{
    public ReducedSurrogateCrossover(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            int[] diff=problem.diffGenePoint(parent1,parent2);
            if(diff.length==0){
                return;
            }else{
                int point=diff[new Random().nextInt(diff.length)];
                problem.copySolution(parent1,child1);
                problem.copySolution(parent2,child2);

                if(random.nextDouble()<CROSSOVER_PROBABILITY){
                    for(int j=0;j<point;j++){
                        problem.exchangeBits(child1,child2,j);
                        if(problem.isOverWeight(child1)||problem.isOverWeight(child2)){
                            problem.exchangeBits(child1,child2,j);
                        }
                    }
                }
            }
        }

    }
}
