package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

/**
 * this class will select the solution with the best performance, and copy the meme option into two children
 */
public class BestInheritanceMethod extends InheritanceMethod{
    /**
     * create object
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     */
    public BestInheritanceMethod(Problem problem,int populationSize) {
        super(problem,populationSize);
    }

    /**
     * traversal the generation, select the solution with the best performance,
     * and copy meme options into two children
     * @param p1 parent1
     * @param p2 parent2
     * @param c1 child1
     * @param c2 child2
     */
    @Override
    public void performMemeticInheritance(int p1, int p2, int c1, int c2) {
        int bestIndex=0;
        for(int i=0;i<populationSize;i++){
            if(problem.getObjectiveFunctionValue(bestIndex)<problem.getObjectiveFunctionValue(i)){
                bestIndex=i;
            }
        }
        for(int i=0;i<problem.getNumberOfMemes();i++){
            this.problem.getMeme(c1,i).setOption(this.problem.getMeme(bestIndex,i).getOption());
            this.problem.getMeme(c2,i).setOption(this.problem.getMeme(bestIndex,i).getOption());
        }
    }


}
