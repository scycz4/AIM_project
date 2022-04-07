import Heuristic.SinglePointMetaHeuristic.HillClimb.DavisBitHC;
import Heuristic.SinglePointMetaHeuristic.HillClimb.FirstImprovementHC;
import Heuristic.SinglePointMetaHeuristic.HillClimb.SteepestHC;
import Heuristic.PopulationBasedMetaHeuristic.MultiMeme.MultiMemeAlgorithm;
import Problem.Problem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class main {
    public static void main(String[] args) throws InterruptedException {

        System.out.print("please enter the file name you want to load:");
        Scanner in=new Scanner(System.in);
        String filename=in.nextLine();

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
//
//
//        while(!problem.hasTimeExpired()) {
//            heuristic.run();
//            System.out.print("best: "+problem1.getBestSolutionValue() + " " + problem1.getBestSolutionAsString() + " current: ");
//            System.out.println(problem1.getObjectiveFunctionValue(0) + " " + problem1.getSolutionAsString(0));
////        System.out.println(problem1.getBestSolutionValue());
////        System.out.println(problem1.getBestSolutionAsString());
//        }
        Problem problem2;
        for(int trial=0;trial<2;trial++){
            System.out.println("Trial#"+(trial+1));
            //muation, ruin-recreate, crossover, localsearch
            problem2=new Problem(16,4,new int[]{2,2,4,4});

            problem2.loadInstance(filename);

            problem2.setIntensityOfMutation(0.4);
            problem2.setDepthOfSearch(0.5);

            MultiMemeAlgorithm mma=new MultiMemeAlgorithm(problem2,16,0.2);

            for(int i=0;i<1000;i++){

                mma.run();
            }
            System.out.println(problem2.getBestSolutionValue());
            System.out.println(problem2.getBestSolutionAsString());

            ArrayList<Integer> best=mma.getBest();
            ArrayList<Integer> worst=mma.getWorst();

            String inputFilename= problem2.getFilename();
            String[] elementArray=inputFilename.split("_");
            String outputFilename=elementArray[0]+"_"+elementArray[1]+"_"+elementArray[2].split("\\.")[0]+"_trial"+(trial+1)+"_output.txt";

            FileWriter fileWriter=null;
            try {
                fileWriter=new FileWriter(outputFilename);
                StringBuffer buffer=new StringBuffer();
                for(int i=0;i<best.size();i++){
                    buffer.append(best.get(i));
                    buffer.append(" - ");
                    buffer.append(i+1);
                    buffer.append("       ");

                    buffer.append(worst.get(i));
                    buffer.append(" - ");
                    buffer.append(i+1);
                    buffer.append("\n");
                }
                fileWriter.write(buffer.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(fileWriter!=null){
                    try {
                        fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
