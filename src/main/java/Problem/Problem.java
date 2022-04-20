package Problem;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic.LargestGreedyHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic.LargestProfitDivWeight;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.GreedyHeuristic.LowestGreedyHeuristic;
import MyExceptions.IndexBoundaryException;
import MyExceptions.NonExistBestSolutionException;

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
    private double bestEverSolutionObjectiveValue;


    private long totalEvaluations;
    private final long MAX_EVALUATIONS;
    private final long[] SIXTY_SECONDS_EVALUATIONS;

    private int intensityOfMutation;
    private int depthOfSearch;

    private double[] currentObjectiveValue;
    boolean[][] flippedIndex;

    private PopulationHeuristic[] greedyHeuristics;

    private Random random;


    public Problem(int populationSize,int numberOfMemes,int[] memeStates,Random random){
        filename="test2_10_269.txt";
        file=getClass().getClassLoader().getResourceAsStream(filename);
        solutions=new Solution[populationSize];
        this.random=random;

        this.memeStates=memeStates;
        this.numberOfMemes=numberOfMemes;
        this.totalEvaluations=0L;
        this.SIXTY_SECONDS_EVALUATIONS=new long[]{1873579L, 1201680L, 1068678L, 2171053L, 1862364L, 1660721L, 5666442L, 5155633L, 3994215L, 2889132L, 2534306L, 4284292L};
        this.MAX_EVALUATIONS=(long)((double)this.SIXTY_SECONDS_EVALUATIONS[random.nextInt(SIXTY_SECONDS_EVALUATIONS.length)]*((double)Integer.MAX_VALUE/60.0D));

        currentObjectiveValue=new double[populationSize];
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
                double profit=Double.parseDouble(instance[0]);
                double weight=Double.parseDouble(instance[1]);
                instances[i]=new Instance(profit,weight);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        flippedIndex=new boolean[solutions.length][instances.length];
        for(int i=0;i<solutions.length;i++){
            initializeSolution(i,instances,boundary,false);
//            while(isOverWeight(i)){
//                initializeSolution(i,instances,boundary,false);
//            }
            currentObjectiveValue[i]=solutions[i].getObjectiveValue();
        }

        greedyHeuristics=new PopulationHeuristic[]{
                new LargestGreedyHeuristic(this),
                new LowestGreedyHeuristic(this),
                new LargestProfitDivWeight(this)
        };
        for(int i=0;i<solutions.length;i++){
            applyGreedyHeuristic(i);
        }
    }

    public void loadInstance(String filename){
        this.filename=filename;

        file=getClass().getClassLoader().getResourceAsStream(filename);

        double boundary=0;
        Instance[] instances = null;
        try {
            BufferedReader br=new BufferedReader(new InputStreamReader(file));
            String s=null;
            s=br.readLine();
            String[] instance=s.split(" ");
            int item=Integer.parseInt(instance[0]);
            instances=new Instance[item];
            boundary=Double.parseDouble(instance[1]);
            int i=0;
            while((s=br.readLine())!=null){
                instance=s.split(" ");
                double profit=Double.parseDouble(instance[0]);
                double weight=Double.parseDouble(instance[1]);
                instances[i]=new Instance(profit,weight);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        flippedIndex=new boolean[solutions.length][instances.length];
        for(int i=0;i<solutions.length;i++){
            initializeSolution(i,instances,boundary,true);
//            while(isOverWeight(i)){
//                initializeSolution(i,instances,boundary,true);
//            }
            currentObjectiveValue[i]=solutions[i].getObjectiveValue();
        }

        for(int i=0;i<solutions.length;i++){
            applyGreedyHeuristic(i);
        }
    }

    public void applyGreedyHeuristic(int index){
        PopulationHeuristic greedyHeuristic=greedyHeuristics[random.nextInt(greedyHeuristics.length)];
        greedyHeuristic.applyHeuristic(index);
        if(bestEverSolution==null){
            throw new NonExistBestSolutionException();
        }
        if(bestEverSolutionObjectiveValue<=getObjectiveFunctionValue(index)){
            bestEverSolution=solutions[index].deepCopy();
            bestEverSolutionObjectiveValue=getObjectiveFunctionValue(index);
        }
    }

    public String getFilename() {
        return this.filename;
    }

//    public int getNumberOfItems(){
//        return bestEverSolution.getNumberOfInstance();
//    }

    private void initializeSolution(int i, Instance[] instances, double boundary, boolean reload) {
        this.solutions[i]=new Solution(instances,boundary,numberOfMemes,memeStates,random);
        Instance[] is=this.solutions[i].deepCopy().getInstance();
        for(int j=0;j<instances.length;j++){
            if(random.nextDouble()<0.8){
                is[j].setState(true);
            }
            else{
                is[j].setState(false);
            }
        }
        this.solutions[i].setInstance(is);
//        applyGreedyHeuristic(i);
        if(bestEverSolution==null||reload){
            bestEverSolution=this.solutions[i].deepCopy();
            bestEverSolutionObjectiveValue=getObjectiveFunctionValue(i);
        }
        if(getObjectiveFunctionValue(i)>=bestEverSolutionObjectiveValue){
            this.bestEverSolution=this.solutions[i].deepCopy();
            bestEverSolutionObjectiveValue=getObjectiveFunctionValue(i);
        }
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
                return Double.compare(o2.value,o1.value);
            }
        });
        int index[]=new int[indexValues.length];
        for(int i=0;i<index.length;i++){
            index[i]=indexValues[i].index;
        }
        return index;
    }

    public int[] getSortedLargestProfitDivWeightIndexArray(int memoryIndex) {
        Solution solution=solutions[memoryIndex];
        Instance[] instances=solution.getInstance();
        IndexValue[] indexValues=new IndexValue[instances.length];
        for(int i=0;i<instances.length;i++){
            indexValues[i]=new IndexValue(i,instances[i].getProfit()/(double)instances[i].getWeight());
        }

        Arrays.sort(indexValues, new Comparator<IndexValue>() {
            @Override
            public int compare(IndexValue o1, IndexValue o2) {
                return Double.compare(o2.value,o1.value);
            }
        });
        int index[]=new int[indexValues.length];
        for(int i=0;i<index.length;i++){
            index[i]=indexValues[i].index;
        }
        return index;
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

    public double getObjectiveFunctionValue(int index){
        this.totalEvaluations++;
        double value;
        if(isOverWeight(index)){
            value=solutions[index].getObjectiveValue()*(1.0/(4.0*getWeight(index)/getBoundary(index)));
        }
        else{
            value=solutions[index].getObjectiveValue();
        }
        if(value>bestEverSolutionObjectiveValue){
            bestEverSolution=solutions[index].deepCopy();
            bestEverSolutionObjectiveValue=value;
        }
        currentObjectiveValue[index]=solutions[index].getObjectiveValue();
        Arrays.fill(flippedIndex[index],false);
        return value;
    }

    public double getBestSolutionValue(){
        return bestEverSolutionObjectiveValue;
    }

    public void bitFlip(int index){
        this.bitFlip(0,index);
    }

    public void bitFlip(int memoryIndex,int index){
        solutions[memoryIndex].bitFlip(index);
        flippedIndex[memoryIndex][index]=!flippedIndex[memoryIndex][index];
    }

    public void destroyBit(int memoryIndex, int index){
        if(getOneBitOfSolution(memoryIndex,index)){
            bitFlip(memoryIndex,index);
            flippedIndex[memoryIndex][index]=!flippedIndex[memoryIndex][index];
        }
    }

    public double getWeight(int index) {
        return solutions[index].getWeight();
    }

    public double getBoundary(int index) {
        return solutions[index].getBoundary();
    }

    public int getNumberOfVariables(){
        return bestEverSolution.getNumberOfInstance();
    }

    public void copySolution(int originIndex,int destinationIndex){
        if(originIndex < 0 || originIndex >= this.solutions.length) {

            throw new IndexBoundaryException("Origin Index [" + originIndex + "] does not exist.");

        } else if(destinationIndex < 0 || destinationIndex >= this.solutions.length) {

            throw new IndexBoundaryException("Destination Index [" + destinationIndex + "]  does not exist.");

        } else {

            Solution solution = this.solutions[originIndex].deepCopy();
            this.solutions[destinationIndex] = solution;
            currentObjectiveValue[destinationIndex]=currentObjectiveValue[originIndex];
            flippedIndex[destinationIndex]=flippedIndex[originIndex].clone();
        }
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
                        throw new IndexBoundaryException(j < 0 ? "Variable cannot be negative." : "Variable " + j + " exceeds the number of variables.");
                    }
                } else {
                    boolean origin = this.solutions[child1] == null;
                    throw new IndexBoundaryException("No solution initialised at " + (origin ? "origin index." : "destination index."));
                }
            } else {
                throw new IndexBoundaryException("DestinationIndex [" + child2 + "] does not exist.");
            }
        } else {
            throw new IndexBoundaryException("Origin Index [" + child1 + "] does not exist.");
        }
    }

//    public double translateMaxToMinValue(){
//        int value=0;
//        for(int i=0;i<getNumberOfVariables();i++){
//            value+=solutions[0].getInstance()[i].getProfit();
//        }
//        return value-solutions[0].getObjectiveValue();
//    }

    public void setPopulationSize(int populationSize){
        Solution[] newPopulation;
        int i;
        currentObjectiveValue=new double[populationSize];
        flippedIndex=new boolean[populationSize][getNumberOfVariables()];
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


    public int getNumberOfMemes(){
        return this.numberOfMemes;
    }

    public Meme getMeme(int solutionIndex,int memeNumber){
        if(solutionIndex<this.solutions.length&&memeNumber<this.getNumberOfMemes()){
            return this.solutions[solutionIndex].getMeme(memeNumber);
        }
        else{
            throw new IndexBoundaryException("solution index or meme number exceeded the total number of solutions or memes!");
        }
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

    public double deltaEvaluation(int index) {
        this.totalEvaluations++;
        double delta=0;
        Instance[] instances=solutions[index].getInstance();
        for(int i=0;i<getNumberOfVariables();i++){
            if(flippedIndex[index][i]){
                if(instances[i].isState()){
                    delta+=instances[i].getProfit();
                }
                else{
                    delta-=instances[i].getProfit();
                }
            }
        }

        currentObjectiveValue[index]=currentObjectiveValue[index]+delta;
        Arrays.fill(flippedIndex[index],false);
        if(isOverWeight(index)){
            return currentObjectiveValue[index]*(1.0/(4.0*getWeight(index)/getBoundary(index)));
        }
        else{
            if(currentObjectiveValue[index]>bestEverSolutionObjectiveValue){
                bestEverSolution=solutions[index].deepCopy();
                bestEverSolutionObjectiveValue=getObjectiveFunctionValue(index);
            }
            return currentObjectiveValue[index];
        }
    }

    public int[] diffGenePoint(int p1,int p2){
        ArrayList<Integer> diff=new ArrayList<>();
        Instance[] ins1=solutions[p1].getInstance();
        Instance[] ins2=solutions[p2].getInstance();

        for(int i=0;i<getNumberOfVariables();i++){
            if(ins1[i].isState()==ins2[i].isState()){
                diff.add(i);
            }
        }

        int[] diffArray=diff.stream().mapToInt(i->i).toArray();
        return diffArray;
    }

    public boolean getLastBitOfSolution(int index){
        return getOneBitOfSolution(index,getNumberOfVariables()-1);
    }

    public boolean getOneBitOfSolution(int solutionIndex, int i){
        Instance[] instances=solutions[solutionIndex].getInstance();
        return instances[i].isState();
    }

    public int getBitWithHighestProfit(int index){
        Instance[] instances=solutions[index].getInstance();
        int bit=random.nextInt(instances.length);
        double profit=Integer.MIN_VALUE;
        for(int j=0;j<instances.length;j++){
            if(instances[j].isState()){
                if(instances[j].getProfit()>=profit){
                    bit=j;
                    profit=instances[j].getProfit();
                }
            }
        }
        return bit;
    }

    public int getBitWithHighestWeight(int index){
        Instance[] instances=solutions[index].getInstance();
        int bit=random.nextInt(instances.length);
        double weight=Integer.MIN_VALUE;
        for(int j=0;j<instances.length;j++){
            if(instances[j].isState()){
                if(instances[j].getWeight()>=weight){
                    bit=j;
                    weight=instances[j].getWeight();
                }
            }
        }
        return bit;
    }

    public int getBitWithLowestProfit(int index){
        Instance[] instances=solutions[index].getInstance();
        int bit=random.nextInt(instances.length);
        double profit=Integer.MAX_VALUE;
        for(int j=0;j<instances.length;j++){
            if(instances[j].isState()){
                if(instances[j].getProfit()<=profit){
                    bit=j;
                    profit=instances[j].getProfit();
                }
            }
        }
        return bit;
    }

    public int getBitWithLowestWeight(int index){
        Instance[] instances=solutions[index].getInstance();
        int bit=random.nextInt(instances.length);
        double weight=Integer.MAX_VALUE;
        for(int j=0;j<instances.length;j++){
            if(instances[j].isState()){
                if(instances[j].getWeight()<=weight){
                    bit=j;
                    weight=instances[j].getWeight();
                }
            }
        }
        return bit;
    }

    public int getBitWithHighestProfitDivWeight(int index){
        Instance[] instances=solutions[index].getInstance();
        int bit=random.nextInt(instances.length);
        double div=Double.NEGATIVE_INFINITY;
        for(int j=0;j<instances.length;j++){
            if(instances[j].isState()){
                double profit=instances[j].getProfit();
                double weight=instances[j].getWeight();
                double current=profit/(double)weight;
                if(current>=div){
                    bit=j;
                    div=current;
                }
            }
        }
        return bit;
    }

    public int getBitWithLowestProfitDivWeight(int index){
        Instance[] instances=solutions[index].getInstance();
        int bit=random.nextInt(instances.length);
        double div=Double.POSITIVE_INFINITY;
        for(int j=0;j<instances.length;j++){
            if(instances[j].isState()){
                double profit=instances[j].getProfit();
                double weight=instances[j].getWeight();
                double current=profit/(double)weight;
                if(current<=div){
                    bit=j;
                    div=current;
                }
            }
        }
        return bit;
    }
    public Random getRandom() {
        return random;
    }

    public boolean isOverWeight(int index){
        return getWeight(index)>getBoundary(index);
    }

    class IndexValue{
        int index;
        double value;
        public IndexValue(int index,double value){
            this.index=index;
            this.value=value;
        }
    }
}
