package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover;

import Problem.Problem;

public class OneBitAdaptiveCrossover extends CrossoverHeuristic{
    public OneBitAdaptiveCrossover(Problem problem) {
        super(problem);
    }

    @Override
    public void applyHeuristic(int p1, int p2, int c1, int c2) {
        CrossoverHeuristic twoPointCrossover=new TwoPointCrossover(problem);
        CrossoverHeuristic uniformCrossover=new UniformCrossover(problem);

        for(int i=0;i<problem.getIntensityOfMutation();i++){
            if(problem.getLastBitOfSolution(p1)==problem.getLastBitOfSolution(p2)&&problem.getLastBitOfSolution(p1)){
                twoPointCrossover.applyHeuristic(p1,p2,c1,c2);
            }else if(problem.getLastBitOfSolution(p1)==problem.getLastBitOfSolution(p2)&&!problem.getLastBitOfSolution(p1)){
                uniformCrossover.applyHeuristic(p1,p2,c1,c2);
            }else{
                if(random.nextDouble()<0.5){
                    uniformCrossover.applyHeuristic(p1,p2,c1,c2);
                }
                else {
                    twoPointCrossover.applyHeuristic(p1,p2,c1,c2);
                }
            }
        }

    }
}
