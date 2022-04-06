package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

import java.util.Random;

public class SimpleInheritanceMethod {
    private final Problem problem;
    private final Random random;

    public SimpleInheritanceMethod(Problem problem){
        this.problem=problem;
        this.random=new Random();
    }

    public void performMemeticInheritance(int parent1, int parent2, int child1, int child2) {

        // TODO - implementation of simple inheritance method
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
        else if(problem.getObjectiveFunctionValue(parent1)<problem.getObjectiveFunctionValue(parent2)){
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
