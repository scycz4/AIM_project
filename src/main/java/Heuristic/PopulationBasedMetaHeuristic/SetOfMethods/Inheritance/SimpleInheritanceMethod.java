package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

/**
 * this class will compare two parents and select one with better performance,
 * and then copy all the meme options into two children
 */
public class SimpleInheritanceMethod extends InheritanceMethod{
    /**
     * create object
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     */
    public SimpleInheritanceMethod(Problem problem,int populationSize){
        super(problem,populationSize);
    }


    /**
     * compare two parents objective value and select the one with higher objective value,
     * copy all the meme options into two children
     * @param parent1
     * @param parent2
     * @param child1
     * @param child2
     */
    @Override
    public void performMemeticInheritance(int parent1, int parent2, int child1, int child2) {
        if(problem.getObjectiveFunctionValue(parent1)==problem.getObjectiveFunctionValue(parent2)){
            if(random.nextDouble()>0.5){
                for(int i=0;i<problem.getNumberOfMemes();i++){
                    this.problem.getMeme(child1,i).setOption(this.problem.getMeme(parent2,i).getOption());
                    this.problem.getMeme(child2,i).setOption(this.problem.getMeme(parent2,i).getOption());
                }
            }
            else{
                for(int i=0;i<problem.getNumberOfMemes();i++){
                    this.problem.getMeme(child1,i).setOption(this.problem.getMeme(parent1,i).getOption());
                    this.problem.getMeme(child2,i).setOption(this.problem.getMeme(parent1,i).getOption());
                }
            }
        }
        else if(problem.getObjectiveFunctionValue(parent1)>problem.getObjectiveFunctionValue(parent2)){
            for(int i=0;i<problem.getNumberOfMemes();i++){
                this.problem.getMeme(child1,i).setOption(this.problem.getMeme(parent1,i).getOption());
                this.problem.getMeme(child2,i).setOption(this.problem.getMeme(parent1,i).getOption());
            }
        }
        else{
            for(int i=0;i<problem.getNumberOfMemes();i++){
                this.problem.getMeme(child1,i).setOption(this.problem.getMeme(parent2,i).getOption());
                this.problem.getMeme(child2,i).setOption(this.problem.getMeme(parent2,i).getOption());
            }
        }
    }
}
