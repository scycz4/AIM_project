package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

import java.util.Random;

public class TwoPointCrossover extends CrossoverHeuristic{
    private Random random;
    private double CROSSOVER_PROBABILITY=0.95;
    public TwoPointCrossover(Problem problem) {
        super(problem);
        random=new Random();
    }

    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        int point1= random.nextInt(problem.getNumberOfVariables());
        int point2;
        do{
            point2= random.nextInt(problem.getNumberOfVariables());
        }while(point1==point2);
        if(point1>point2){
            int temp=point2;
            point2=point1;
            point1=temp;
        }

        problem.copySolution(parent1,child1);
        problem.copySolution(parent2,child2);

        if(random.nextDouble()<CROSSOVER_PROBABILITY){
            for(int i=point1;i<point2;i++){
                problem.exchangeBits(child1,child2,i);
            }
        }
    }
}
