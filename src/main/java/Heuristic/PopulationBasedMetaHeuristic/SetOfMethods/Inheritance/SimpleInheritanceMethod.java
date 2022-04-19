package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

public class SimpleInheritanceMethod extends InheritanceMethod{
    public SimpleInheritanceMethod(Problem problem,int populationSize){
        super(problem,populationSize);
    }



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
