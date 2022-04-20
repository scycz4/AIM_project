package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

/**
 * this class will exchange the different genes between 0 and random generated point from indices of different genes
 */
public class ReducedSurrogateCrossover extends CrossoverHeuristic{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public ReducedSurrogateCrossover(Problem problem) {
        super(problem);
    }

    /**
     * it will apply this heuristic for IoM times, then get the indices of all different genes between
     * parent1 and parent2, then randomly choose a point from indices, and then exchange all the different bits
     * between 0 and this point
     * @param parent1 the parent
     * @param parent2 the parent
     * @param child1 the child solution need to be generated
     * @param child2 the child solution need to be generated
     */
    @Override
    public void applyHeuristic(int parent1, int parent2, int child1, int child2) {
        problem.copySolution(parent1,child1);
        problem.copySolution(parent2,child2);
        for(int i=0;i<problem.getIntensityOfMutation();i++){
            //indices of different genes(bits) between parent1 and parent2
            int[] diff=problem.diffGenePoint(parent1,parent2);
            if(diff.length==0){
                return;
            }else{
                int point=diff[random.nextInt(diff.length)];
                if(random.nextDouble()< crossoverProbability){
                    for(int j=0;j<point;j++){
                        problem.exchangeBits(child1,child2,j);
                    }
                }
            }
        }

    }
}
