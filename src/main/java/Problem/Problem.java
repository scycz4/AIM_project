package Problem;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Problem {
    private Solution[] solutions;
    private InputStream file;

    private int numberOfMemes;
    private int[] memeStates;

    public Problem(){
        file=getClass().getClassLoader().getResourceAsStream("test2_10_269.txt");
        solutions=new Solution[2];
        initialize();
    }

    public Problem(int populationSize,int numberOfMemes,int[] memeStates){
        file=getClass().getClassLoader().getResourceAsStream("test1_4_20.txt");
        solutions=new Solution[populationSize];
        this.memeStates=memeStates;
        this.numberOfMemes=numberOfMemes;
        int boundary=0;
        Instance[] instances = null;
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(file));
            String s=null;
            s=br.readLine();
            String[] instance=s.split(" ");
            int item=Integer.parseInt(instance[0]);
            instances=new Instance[item];
            boundary=Integer.parseInt(instance[1]);
            int i=0;
            while((s=br.readLine())!=null){
                instance=s.split(" ");
                int profit=Integer.parseInt(instance[0]);
                int weight=Integer.parseInt(instance[1]);
                instances[i]=new Instance(profit,weight);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Solution(instances,boundary,this.numberOfMemes,this.memeStates);

        for(int i=0;i<solutions.length;i++){
            initializeSolution(i,instances,boundary);
        }
    }

    private void initializeSolution(int i, Instance[] instances, int boundary) {
        this.solutions[i]=new Solution(instances,boundary,numberOfMemes,memeStates);
        Instance[] is=this.solutions[i].getInstance();
        for(int j=0;j<instances.length;j++){
            is[j].setState(new Random().nextBoolean());
        }
        this.solutions[i].setInstance(is);
    }


    public void initialize(){
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(file));
            String s=null;
            s=br.readLine();
            String[] instance=s.split(" ");
            int item=Integer.parseInt(instance[0]);
            Instance[] instances=new Instance[item];
            int boundary=Integer.parseInt(instance[1]);
            int i=0;
            while((s=br.readLine())!=null){
                instance=s.split(" ");
                int profit=Integer.parseInt(instance[0]);
                int weight=Integer.parseInt(instance[1]);
                instances[i]=new Instance(profit,weight);
                i++;
            }
            Solution solution=new Solution(instances,boundary,0,null);
            solutions[0]=solutions[1]=solution;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFile(String file){
        this.file=getClass().getResourceAsStream(file);
        this.initialize();
    }
    public String getSolutionAsString(int index){
        return solutions[index].getSolutionAsString();
    }

    public int getObjectiveFunctionValue(int index){
        return solutions[index].getObjectiveValue();
    }

    public void bitFlip(int index){
        this.bitFlip(0,index);
    }

    public void bitFlip(int memoryIndex,int index){
        solutions[memoryIndex].bitFlip(index);
    }

    public int getWeight(int index) {
        return solutions[index].getWeight();
    }

    public int getBoundary(int index) {
        return solutions[index].getBoundary();
    }

    public int getNumberOfVariables(int index){
        return solutions[index].getNumberOfInstance();
    }

    public int getNumberOfVariables(){
        return solutions[0].getNumberOfInstance();
    }

    public void copySolution(int originIndex,int destinationIndex){
        if (originIndex >= 0 && originIndex < this.solutions.length) {
            if (destinationIndex >= 0 && destinationIndex < this.solutions.length) {
                Solution solution = this.solutions[originIndex].deepCopy();
                this.solutions[destinationIndex] = solution;
            } else {
                this.fatal("Destination Index [" + destinationIndex + "]  does not exist.");
            }
        } else {
            this.fatal("Origin Index [" + originIndex + "] does not exist.");
        }
    }

    private void fatal(String errorMessage) {
        System.err.println(errorMessage);
        System.exit(0);
    }

    public void exchangeBits(int child1,int child2,int j){
        if (child1 >= 0 && child1 < this.solutions.length) {
            if (child2 >= 0 && child2 < this.solutions.length) {
                if (this.solutions[child1] != null && this.solutions[child2] != null) {
                    if (j >= 0 && j < this.solutions[child1].getInstance().length) {
                        Instance originVariableA = this.solutions[child1].getInstance()[j];
                        Instance originVariableB = this.solutions[child2].getInstance()[j];
                        boolean bVarA = originVariableA.isState();
                        boolean bVarB = originVariableB.isState();
                        this.solutions[child1].getInstance()[j].setState(bVarB);
                        this.solutions[child2].getInstance()[j].setState(bVarA);
                    } else {
                        this.fatal(j < 0 ? "Variable cannot be negative." : "Variable " + j + " exceeds the number of variables.");
                    }
                } else {
                    boolean origin = this.solutions[child1] == null;
                    this.fatal("No solution initialised at " + (origin ? "origin index." : "destination index."));
                }
            } else {
                this.fatal("DestinationIndex [" + child2 + "] does not exist.");
            }
        } else {
            this.fatal("Origin Index [" + child1 + "] does not exist.");
        }
    }

    public int translateMaxToMinValue(){
        int value=0;
        for(int i=0;i<getNumberOfVariables();i++){
            value+=solutions[0].getInstance()[i].getProfit();
        }
        return value-solutions[0].getObjectiveValue();
    }
}
