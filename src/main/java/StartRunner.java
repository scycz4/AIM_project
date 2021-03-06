import Heuristic.PopulationBasedMetaHeuristic.MultiMeme.MultiMemeAlgorithm;
import Problem.Problem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class StartRunner {
    public static void main(String[] args){

        System.out.print("please enter the file name you want to load:");
        Scanner in=new Scanner(System.in);
        String filename=in.nextLine();

        PopulationHeuristicRun populationHeuristicRun=new PopulationHeuristicRun(filename);
        populationHeuristicRun.run();
    }

    /**
     * this class will run a multi-meme algorithm to solve problem
     */
    static class PopulationHeuristicRun{
        private final int TRIAL=5;
        private final int POP_SIZE=16;
        private final int NUM_OF_MEMES=6;

        private final int MUTATION=9;
        private final int CROSSOVER=5;
        private final int LOCAL_SEARCH=8;
        private final int INHERITANCE=2;
        private final int REPLACEMENT=3;
        private final int SELECTION=3;

        private final double IOM=0.8;
        private final double DOS=0.5;

        private final double INNOVATION_RATE=0.2;

        private final int GENERATION=150;

        private String filename;

        private Problem problem;
        private ArrayList<Double> best;
        private ArrayList<Double> worst;

        private Random random;

        /**
         * create runner
         * @param filename the file need to be loaded
         */
        public PopulationHeuristicRun(String filename){
            this.filename=filename;
            this.random=new Random();
        }

        /**
         * run the program
         */
        public void run(){
            for(int trial=0;trial<TRIAL;trial++){
                System.out.println("Trial#"+(trial+1));
                //muation, ruin-recreate, crossover, localsearch, inheritance, replacement, selection
                problem=new Problem(POP_SIZE,NUM_OF_MEMES,new int[]{MUTATION,CROSSOVER,LOCAL_SEARCH,INHERITANCE,REPLACEMENT,SELECTION},random);

                problem.loadInstance(filename);

                problem.setIntensityOfMutation(IOM);
                problem.setDepthOfSearch(DOS);

                MultiMemeAlgorithm mma=new MultiMemeAlgorithm(problem,POP_SIZE,INNOVATION_RATE);

                for(int i=0;i<GENERATION;i++){
                    mma.run();
                }
                System.out.println(problem.getBestSolutionValue());
                System.out.println(problem.getBestSolutionAsString());

                best=mma.getBest();
                worst=mma.getWorst();

                this.writeFile(trial);
            }


        }

        /**
         * write the best and worst result of all generations into file
         * @param trial the current trial
         */
        private void writeFile(int trial) {

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
    }
}
