package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance;

import Problem.Problem;

public class BestInheritanceMethod extends InheritanceMethod{
    public BestInheritanceMethod(Problem problem) {
        super(problem);
    }

    @Override
    public void performMemeticInheritance(int p1, int p2, int c1, int c2,int populationSize) {
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
