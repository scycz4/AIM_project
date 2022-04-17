package Problem;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic.LargestGreedyHeuristic;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Problem {
    private Solution[] solutions;
    private InputStream file;

    private String filename;

    private int numberOfMemes;
    private int[] memeStates;

    private Solution bestEverSolution;

    private long totalEvaluations;
    private final long MAX_EVALUATIONS;
    private final long[] SIXTY_SECONDS_EVALUATIONS;

    private int intensityOfMutation;
    private int depthOfSearch;

    private int[] currentObjectiveValue;
    boolean[][] flippedIndex;

    public Problem(){
        filename="test1_4_20.txt";
        file=getClass().getClassLoader().getResourceAsStream(filename);
        solutions=new Solution[2];
        currentObjectiveValue=new int[1];

        this.totalEvaluations=0L;
        this.SIXTY_SECONDS_EVALUATIONS=new long[]{1873579L, 1201680L, 1068678L, 2171053L, 1862364L, 1660721L, 5666442L, 5155633L, 3994215L, 2889132L, 2534306L, 4284292L};
        this.MAX_EVALUATIONS=(long)((double)this.SIXTY_SECONDS_EVALUATIONS[new Random().nextInt(SIXTY_SECONDS_EVALUATIONS.length)]*((double)Integer.MAX_VALUE/60.0D));

        initialize();

        for(int i=0;i<solutions.length-1;i++){
            initializeSolution(i,solutions[i].deepCopy().getInstance(),solutions[i].deepCopy().getBoundary(),false);
            while(this.solutions[i].getWeight()>this.solutions[i].getBoundary()){
                initializeSolution(i,solutions[i].deepCopy().getInstance(),solutions[i].deepCopy().getBoundary(),false);
            }
            currentObjectiveValue[i]=solutions[i].getObjectiveValue();
        }
        currentObjectiveValue[1]=currentObjectiveValue[0];
        flippedIndex=new boolean[solutions.length][getNumberOfItems()];

    }

    public Problem(int populationSize,int numberOfMemes,int[] memeStates){
        filename="test2_10_269.txt";
        file=getClass().getClassLoader().getResourceAsStream(filename);
        solutions=new Solution[populationSize];


        this.memeStates=memeStates;
        this.numberOfMemes=numberOfMemes;
        this.totalEvaluations=0L;
        this.SIXTY_SECONDS_EVALUATIONS=new long[]{1873579L, 1201680L, 1068678L, 2171053L, 1862364L, 1660721L, 5666442L, 5155633L, 3994215L, 2889132L, 2534306L, 4284292L};
        this.MAX_EVALUATIONS=(long)((double)this.SIXTY_SECONDS_EVALUATIONS[new Random().nextInt(SIXTY_SECONDS_EVALUATIONS.length)]*((double)Integer.MAX_VALUE/60.0D));

        currentObjectiveValue=new int[populationSize];

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

        for(int i=0;i<solutions.length;i++){
            initializeSolution(i,instances,boundary,false);
            while(this.solutions[i].getWeight()>this.solutions[i].getBoundary()){
                initializeSolution(i,instances,boundary,false);
            }
            currentObjectiveValue[i]=solutions[i].getObjectiveValue();
        }
        flippedIndex=new boolean[solutions.length][getNumberOfItems()];
    }

    public void loadInstance(String filename){
        this.filename=filename;

        file=getClass().getClassLoader().getResourceAsStream(filename);

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

        for(int i=0;i<solutions.length;i++){
            initializeSolution(i,instances,boundary,true);
            while(this.solutions[i].getWeight()>this.solutions[i].getBoundary()){
                initializeSolution(i,instances,boundary,true);
            }
            currentObjectiveValue[i]=solutions[i].getObjectiveValue();
        }
        flippedIndex=new boolean[solutions.length][getNumberOfItems()];
        PopulationHeuristic greedyHeuristic=new LargestGreedyHeuristic(this);
        greedyHeuristic.applyHeuristic(new Random().nextInt(solutions.length));
    }

    public String getFilename() {
        return this.filename;
    }

    public int getNumberOfItems(){
        return bestEverSolution.getNumberOfInstance();
    }

    private void initializeSolution(int i, Instance[] instances, int boundary, boolean reload) {
        this.solutions[i]=new Solution(instances,boundary,numberOfMemes,memeStates);
        Instance[] is=this.solutions[i].deepCopy().getInstance();
        for(int j=0;j<instances.length;j++){
            is[j].setState(new Random().nextBoolean());
        }
        this.solutions[i].setInstance(is);
        if(bestEverSolution==null||reload){
            bestEverSolution=this.solutions[i].deepCopy();
        }
        if(this.solutions[i].getObjectiveValue()>=bestEverSolution.getObjectiveValue()){
            this.bestEverSolution=this.solutions[i].deepCopy();
        }
    }

    public void rebuildSolution(int index){
        initializeSolution(index,solutions[index].getInstance(),solutions[index].getBoundary(),false);
        while(this.solutions[index].getWeight()>this.solutions[index].getBoundary()){
            initializeSolution(index,solutions[index].getInstance(),solutions[index].getBoundary(),false);
        }
        currentObjectiveValue[index]=solutions[index].getObjectiveValue();
        Arrays.fill(flippedIndex[index],false);
    }

    public int[] getSortedLargestProfitIndexArray(int memoryIndex){
        Solution solution=solutions[memoryIndex];
        Instance[] instances=solution.getInstance();
        IndexValue[] indexValues=new IndexValue[instances.length];
        for(int i=0;i<instances.length;i++){
            indexValues[i]=new IndexValue(i,instances[i].getProfit());
        }

        Arrays.sort(indexValues, new Comparator<IndexValue>() {
            @Override
            public int compare(IndexValue o1, IndexValue o2) {
                return o2.value-o1.value;
            }
        });
        int index[]=new int[indexValues.length];
        for(int i=0;i<index.length;i++){
            index[i]=indexValues[i].index;
        }
        return index;
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
            solutions[0]=solution;
            solutions[1]=solution.deepCopy();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBestSolutionAsString(){
        return this.getSolutionAsString(this.bestEverSolution);
    }

    public String getSolutionAsString(Solution solution){
        return solution.getSolutionAsString();
    }

    public String getSolutionAsString(int index){
        return solutions[index].getSolutionAsString();
    }

    public int getObjectiveFunctionValue(int index){
        this.totalEvaluations++;
        if(solutions[index].getObjectiveValue()>bestEverSolution.getObjectiveValue()&&solutions[index].getWeight()<=solutions[index].getBoundary()){
            bestEverSolution=solutions[index].deepCopy();
        }
        currentObjectiveValue[index]=solutions[index].getObjectiveValue();
        Arrays.fill(flippedIndex[index],false);
        return currentObjectiveValue[index];
    }

    public int getBestSolutionValue(){
        return bestEverSolution.getObjectiveValue();
    }

    public void bitFlip(int index){
        this.bitFlip(0,index);
    }

    public void bitFlip(int memoryIndex,int index){
        solutions[memoryIndex].bitFlip(index);
        flippedIndex[memoryIndex][index]=!flippedIndex[memoryIndex][index];
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
        return bestEverSolution.getNumberOfInstance();
    }

    public void copySolution(int originIndex,int destinationIndex){
        if(originIndex < 0 || originIndex >= this.solutions.length) {

            fatal("Origin Index [" + originIndex + "] does not exist.");

        } else if(destinationIndex < 0 || destinationIndex >= this.solutions.length) {

            fatal("Destination Index [" + destinationIndex + "]  does not exist.");

        } else {

            Solution solution = this.solutions[originIndex].deepCopy();
            this.solutions[destinationIndex] = solution;
            currentObjectiveValue[destinationIndex]=currentObjectiveValue[originIndex];
            flippedIndex[destinationIndex]=flippedIndex[originIndex].clone();
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

    public double translateMaxToMinValue(){
        int value=0;
        for(int i=0;i<getNumberOfVariables();i++){
            value+=solutions[0].getInstance()[i].getProfit();
        }
        return value-solutions[0].getObjectiveValue();
    }

    public void setPopulationSize(int populationSize){
        Solution[] newPopulation;
        int i;
        currentObjectiveValue=new int[populationSize];
        flippedIndex=new boolean[populationSize][getNumberOfItems()];
        if(populationSize<this.solutions.length){
            newPopulation=new Solution[populationSize];

            for(i=0;i<populationSize;i++){
                newPopulation[i]=this.solutions[i];
                currentObjectiveValue[i]=newPopulation[i].getObjectiveValue();
            }
            this.solutions=newPopulation;

        }else if(populationSize>this.solutions.length){
            newPopulation=new Solution[populationSize];
            for(i=0;i<this.solutions.length;i++){
                newPopulation[i]=this.solutions[i];
                currentObjectiveValue[i]=newPopulation[i].getObjectiveValue();
            }
            for(i=solutions.length;i<populationSize;i++){
                newPopulation[i]=this.solutions[0].deepCopy();
                currentObjectiveValue[i]=newPopulation[i].getObjectiveValue();
            }
            this.solutions=newPopulation;
        }

    }

    public long getElapsedTime(){
        return this.totalEvaluations;
    }

    public long getMaxTime(){
        return this.MAX_EVALUATIONS;
    }

    public boolean hasTimeExpired(){
        return this.totalEvaluations>=this.MAX_EVALUATIONS;
    }

    public int getNumberOfMemes(){
        return this.numberOfMemes;
    }

    public Meme getMeme(int solutionIndex,int memeNumber){
        if(solutionIndex<this.solutions.length&&memeNumber<this.getNumberOfMemes()){
            return this.solutions[solutionIndex].getMeme(memeNumber);
        }
        else{
            this.fatal("solution index or meme number exceeded the total number of solutions or memes!");
            return null;
        }
    }

    public void reset(int index){
        Instance[] instances=solutions[index].getInstance();
        for(int i=0;i<instances.length;i++){
            instances[i].setState(false);
        }
        solutions[index].setInstance(instances);
        currentObjectiveValue[index]=getObjectiveFunctionValue(index);
        Arrays.fill(flippedIndex[index],false);
    }

    public void setIntensityOfMutation(double intensityOfMutation){
        if(intensityOfMutation>=0.0&&intensityOfMutation<0.2){
            this.intensityOfMutation=1;
        }else if(intensityOfMutation<0.4){
            this.intensityOfMutation=2;
        }else if(intensityOfMutation<0.6){
            this.intensityOfMutation=3;
        }else if(intensityOfMutation<0.8){
            this.intensityOfMutation=4;
        }else if(intensityOfMutation<1.0){
            this.intensityOfMutation=5;
        }else if(intensityOfMutation==1.0){
            this.intensityOfMutation=6;
        }
    }

    public void setDepthOfSearch(double depthOfSearch){
        if(depthOfSearch>=0.0&&depthOfSearch<0.2){
            this.depthOfSearch=1;
        }else if(depthOfSearch<0.4){
            this.depthOfSearch=2;
        }else if(depthOfSearch<0.6){
            this.depthOfSearch=3;
        }else if(depthOfSearch<0.8){
            this.depthOfSearch=4;
        }else if(depthOfSearch<1.0){
            this.depthOfSearch=5;
        }else if(depthOfSearch==1.0){
            this.depthOfSearch=6;
        }
    }

    public int getIntensityOfMutation() {
        return intensityOfMutation;
    }

    public int getDepthOfSearch() {
        return depthOfSearch;
    }

    public int deltaEvaluation(int index) {
        this.totalEvaluations++;
        int delta=0;
        Instance[] instances=solutions[index].getInstance();
        for(int i=0;i<getNumberOfItems();i++){
            if(flippedIndex[index][i]){
                if(instances[i].isState()){
                    delta+=instances[i].getProfit();
                }
                else{
                    delta-=instances[i].getProfit();
                }
            }
        }

        int weight=getWeight(index);
        int boundary=getBoundary(index);

        if(weight>boundary){
            currentObjectiveValue[index]=(weight-boundary)/boundary;
        }
        else{
            currentObjectiveValue[index]=currentObjectiveValue[index]+delta;
        }

        if(currentObjectiveValue[index]>bestEverSolution.getObjectiveValue()){
            bestEverSolution=solutions[index].deepCopy();
        }
        Arrays.fill(flippedIndex[index],false);
        return currentObjectiveValue[index];
    }

    public int[] diffGenePoint(int p1,int p2){
        ArrayList<Integer> diff=new ArrayList<>();
        Instance[] ins1=solutions[p1].getInstance();
        Instance[] ins2=solutions[p2].getInstance();

        for(int i=0;i<getNumberOfItems();i++){
            if(ins1[i].isState()==ins2[i].isState()){
                diff.add(i);
            }
        }

        int[] diffArray=diff.stream().mapToInt(i->i).toArray();
        return diffArray;
    }

    public boolean getLastBitOfSolution(int index){
        Instance[] instances=solutions[index].getInstance();
        return instances[instances.length-1].isState();
    }

    class IndexValue{
        int index;
        int value;
        public IndexValue(int index,int value){
            this.index=index;
            this.value=value;
        }
    }
}
