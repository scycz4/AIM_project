//import Heuristic.SinglePointMetaHeuristic.HillClimb.DavisBitHC;
//import Heuristic.SinglePointMetaHeuristic.HillClimb.FirstImprovementHC;
//import Heuristic.SinglePointMetaHeuristic.HillClimb.SteepestHC;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.MemeticAlgorithm;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.SearchMethod;
import Heuristic.PopulationBasedMetaHeuristic.MultiMeme.MultiMemeAlgorithm;
import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.CrossoverHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.UniformCrossover;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.DavisBitHCIE;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.BitMutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.Replacement;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.ReplacementWithElitists;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.TournamentSelection;
import Heuristic.SinglePointMetaHeuristic.HillClimb.DavisBitHC;
import Heuristic.SinglePointMetaHeuristic.HillClimb.FirstImprovementHC;
import Heuristic.SinglePointMetaHeuristic.HillClimb.SteepestHC;
import Heuristic.SinglePointMetaHeuristic.LundyAndMees;
import Heuristic.SinglePointMetaHeuristic.SimulatedAnneal;
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

        PopulationHeuristicRun populationHeuristicRun=new PopulationHeuristicRun(filename);
        populationHeuristicRun.run();
    }
    static class PopulationHeuristicRun{
        private final int TRIAL=2;
        private final int POP_SIZE=16;
        private final int NUM_OF_MEMES=7;

        private final int MUTATION=2;
        private final int RUIN_RECREATE=2;
        private final int CROSSOVER=3;
        private final int LOCAL_SEARCH=8;
        private final int INHERITANCE=2;
        private final int REPLACEMENT=2;
        private final int SELECTION=2;

        private final double IOM=0.4;
        private final double DOS=0.5;

        private final double INNOVATION_RATE=0.2;

        private final int GENERATION=1000;

        private String filename;

        public PopulationHeuristicRun(String filename){
            this.filename=filename;
        }

        public void run(){
            long time=System.nanoTime();
            Problem problem;
            for(int trial=0;trial<TRIAL;trial++){
                System.out.println("Trial#"+(trial+1));
                //muation, ruin-recreate, crossover, localsearch, inheritance, replacement, selection
                problem=new Problem(POP_SIZE,NUM_OF_MEMES,new int[]{MUTATION,RUIN_RECREATE,CROSSOVER,LOCAL_SEARCH,INHERITANCE,REPLACEMENT,SELECTION});

                problem.loadInstance(filename);

                problem.setIntensityOfMutation(IOM);
                problem.setDepthOfSearch(DOS);

                MultiMemeAlgorithm mma=new MultiMemeAlgorithm(problem,POP_SIZE,INNOVATION_RATE);

                for(int i=0;i<GENERATION;i++){
                    mma.run();
                }
                System.out.println(problem.getBestSolutionValue());
                System.out.println(problem.getBestSolutionAsString());

                ArrayList<Integer> best=mma.getBest();
                ArrayList<Integer> worst=mma.getWorst();

                String inputFilename= problem.getFilename();
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
            System.out.println((double)(System.nanoTime()-time)/1000000000);
        }
    }

    class SinglePointHeuristicRun{
        private String filename;
        private int CURRENT_SOLUTION_INDEX=0;
        private int BACK_UP_SOLUTION_INDEX=1;
        private int LOOP_TIME=10000;
        private int RUN_TIME=15000;
        private int POP_SIZE=16;
        private int T_SIZE=3;
        public SinglePointHeuristicRun(String filename){
            this.filename=filename;
        }

        public void run(){

            Problem problem=new Problem();
            problem.loadInstance(filename);
            SteepestHC hc1=new SteepestHC();
            FirstImprovementHC hc2=new FirstImprovementHC();
            DavisBitHC hc3=new DavisBitHC();
            for(int i=0;i<LOOP_TIME;i++){
                hc1.applyHeuristic(problem);
                System.out.print("best: "+problem.getBestSolutionValue() + " " + problem.getBestSolutionAsString() + " current: ");
                System.out.println(problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX) + " " + problem.getSolutionAsString(CURRENT_SOLUTION_INDEX));
            }
            System.out.println(problem.getBestSolutionValue());


            LundyAndMees lundyAndMees=new LundyAndMees(problem.getBestSolutionValue());
            SimulatedAnneal simulatedAnneal=new SimulatedAnneal(lundyAndMees,problem);
            long t=System.currentTimeMillis();
            long end=t+RUN_TIME;
            while(System.currentTimeMillis()<end){
                simulatedAnneal.applyHeuristic();
                System.out.print("best: "+problem.getBestSolutionValue() + " " + problem.getBestSolutionAsString() + " current: ");
                System.out.println(problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX) + " " + problem.getSolutionAsString(CURRENT_SOLUTION_INDEX));
            }
            System.out.println(problem.getBestSolutionValue());


            Problem problem1=new Problem(POP_SIZE,0,null);
            TournamentSelection tournamentSelection=new TournamentSelection(problem1,POP_SIZE,T_SIZE);
            CrossoverHeuristic crossoverHeuristic=new UniformCrossover(problem1);
            PopulationHeuristic mutation=new BitMutation(problem1);
            PopulationHeuristic localSearch=new DavisBitHCIE(problem1);
            Replacement replacement=new ReplacementWithElitists();
            SearchMethod heuristic=new MemeticAlgorithm(problem1,POP_SIZE,crossoverHeuristic,mutation,localSearch,replacement);



            while(!problem.hasTimeExpired()) {
                heuristic.run();
                System.out.print("best: "+problem1.getBestSolutionValue() + " " + problem1.getBestSolutionAsString() + " current: ");
                System.out.println(problem1.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX) + " " + problem1.getSolutionAsString(CURRENT_SOLUTION_INDEX));
//        System.out.println(problem1.getBestSolutionValue());
//        System.out.println(problem1.getBestSolutionAsString());
            }
        }
    }
}
