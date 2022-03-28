import Heuristic.Crossover.CrossoverHeuristic;
import Heuristic.Crossover.UniformCrossover;
import Heuristic.HillClimb.DavisBitHC;
import Heuristic.HillClimb.FirstImprovementHC;
import Heuristic.HillClimb.SteepestHC;
import Heuristic.PopulationBasedMetaHeuristic.*;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.MemeticAlgorithm;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.SearchMethod;
import Heuristic.SinglePointMetaHeuristic.LundyAndMees;
import Heuristic.SinglePointMetaHeuristic.SimulatedAnneal;
import Problem.Problem;
import Problem.Solution;

import static java.lang.Thread.sleep;

public class main {
    public static void main(String[] args) throws InterruptedException {

        int CURRENT_SOLUTION_INDEX=0;
        int BACK_UP_SOLUTION_INDEX=1;
        Problem problem=new Problem();

        SteepestHC hc1=new SteepestHC();
        FirstImprovementHC hc2=new FirstImprovementHC();
        DavisBitHC hc3=new DavisBitHC();
//        for(int i=0;i<10000;i++){
//            hc1.applyHeuristic(problem);
//            System.out.print("best: "+problem.getBestSolutionValue() + " " + problem.getBestSolutionAsString() + " current: ");
//            System.out.println(problem.getObjectiveFunctionValue(0) + " " + problem.getSolutionAsString(0));
//        }
//        System.out.println(problem.getBestSolutionValue());


//        LundyAndMees lundyAndMees=new LundyAndMees(48);
//        SimulatedAnneal simulatedAnneal=new SimulatedAnneal(lundyAndMees,problem);
//        long t=System.currentTimeMillis();
//        long end=t+15000;
//        while(System.currentTimeMillis()<end){
//            simulatedAnneal.applyHeuristic();
//            System.out.print("best: "+problem.getBestSolutionValue() + " " + problem.getBestSolutionAsString() + " current: ");
//            System.out.println(problem.getObjectiveFunctionValue(0) + " " + problem.getSolutionAsString(0));
//        }
//        System.out.println(problem.getBestSolutionValue());


//        Problem problem1=new Problem(16,0,null);
//        TournamentSelection tournamentSelection=new TournamentSelection(problem1,16);
//        CrossoverHeuristic crossoverHeuristic=new UniformCrossover(problem1);
//        PopulationHeuristic mutation=new BitMutation(problem1);
//        PopulationHeuristic localSearch=new DavisBitHCIE(problem1);
//        Replacement replacement=new Replacement();
//        SearchMethod heuristic=new MemeticAlgorithm(problem1,16,crossoverHeuristic,mutation,localSearch,replacement);
//
//        for(int i=0;i<10;i++) {
//            heuristic.run();
//            System.out.print("best: "+problem1.getBestSolutionValue() + " " + problem1.getBestSolutionAsString() + " current: ");
//            System.out.println(problem1.getObjectiveFunctionValue(0) + " " + problem1.getSolutionAsString(0));
////        System.out.println(problem1.getBestSolutionValue());
////        System.out.println(problem1.getBestSolutionAsString());
//        }
    }
}
