package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

/**
 * this class will exchange all the bits between selected two points
 */
public class TwoPointCrossover extends CrossoverHeuristic{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public TwoPointCrossover(Problem problem) {
        super(problem);
    }

    /**
     * it will apply heuristic for IoM times, and randomly choose two points, then if randomly generated
     * double is less than crossover probability, exchange all the bits between two points
     * @param parent1 the parent
     * @param parent2 the parent
     * @param child1 the child solution need to be generated
     * @param child2 the child solution need to be generated
     */
    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        problem.copySolution(parent1,child1);
        problem.copySolution(parent2,child2);
        for(int j=0;j<problem.getIntensityOfMutation();j++){
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



            if(random.nextDouble()< crossoverProbability){
                for(int i=point1;i<point2;i++){
                    problem.exchangeBits(child1,child2,i);
//                    if(problem.isOverWeight(child1)||problem.isOverWeight(child2)){
//                        problem.exchangeBits(child1,child2,i);
//                    }
                }
            }
        }
    }
}
