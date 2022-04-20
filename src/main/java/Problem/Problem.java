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

/**
 * this class will create a problem to be solved
 */
public class Problem {
    private Solution[] solutions;
    private InputStream file;

    private String filename;

    private int numberOfMemes;
    private int[] memeStates;

    private Solution bestEverSolution;
    private double bestEverSolutionObjectiveValue;

    private int intensityOfMutation;
    private int depthOfSearch;

    private double[] currentObjectiveValue;
    boolean[][] flippedIndex;

    private PopulationHeuristic[] greedyHeuristics;

    private Random random;

    /**
     * create a problem
     * @param populationSize the size of population
     * @param numberOfMemes the number of memes
     * @param memeStates the options that memes has
     * @param random a random generator
     */
    public Problem(int populationSize,int numberOfMemes,int[] memeStates,Random random){
        filename="test2_10_269.txt";
        file=getClass().getClassLoader().getResourceAsStream(filename);
        solutions=new Solution[populationSize];
        this.random=random;

        this.memeStates=memeStates;
        this.numberOfMemes=numberOfMemes;
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

    /**
     * load the instances(items) from file
     * @param filename the name of file
     */
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
            currentObjectiveValue[i]=solutions[i].getObjectiveValue();
        }

        for(int i=0;i<solutions.length;i++){
            applyGreedyHeuristic(i);
        }
    }

    /**
     * apply one of three greedy search on solution on that index
     * @param index the index of solution
     */
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

    /**
     * get the file name
     * @return the file name
     */
    public String getFilename() {
        return this.filename;
    }

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
        if(bestEverSolution==null||reload){
            bestEverSolution=this.solutions[i].deepCopy();
            bestEverSolutionObjectiveValue=getObjectiveFunctionValue(i);
        }
        if(getObjectiveFunctionValue(i)>=bestEverSolutionObjectiveValue){
            this.bestEverSolution=this.solutions[i].deepCopy();
            bestEverSolutionObjectiveValue=getObjectiveFunctionValue(i);
        }
    }


    /**
     * rank the indices by their profit descending
     * @param memoryIndex the index of solution
     * @return ranked indices
     */
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

    /**
     * rank the indices by their profit per weight descending
     * @param memoryIndex the index of solution
     * @return ranked indices
     */
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

    /**
     * get the string of best solution
     * @return the string of best solution
     */
    public String getBestSolutionAsString(){
        return this.getSolutionAsString(this.bestEverSolution);
    }

    /**
     * get the string of that solution
     * @param solution solution
     * @return the string of that solution
     */
    public String getSolutionAsString(Solution solution){
        return solution.getSolutionAsString();
    }

    /**
     * get the string of that solution
     * @param index the index of solution
     * @return the string of that solution
     */
    public String getSolutionAsString(int index){
        return solutions[index].getSolutionAsString();
    }

    /**
     * get the objective value of that index solution, if the weight is
     * greater than boundary, then multiple a punishing factor with objective value
     * @param index the index of solution
     * @return the objective value
     */
    public double getObjectiveFunctionValue(int index){
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

    /**
     * get the value of best solution
     * @return the value of best solution
     */
    public double getBestSolutionValue(){
        return bestEverSolutionObjectiveValue;
    }

    /**
     * bit flip the current solution on index item
     * @param index the index of item
     */
    public void bitFlip(int index){
        this.bitFlip(0,index);
    }

    /**
     * bit flip the bit on index of solution on memoryIndex
     * @param memoryIndex index of solution
     * @param index index of item
     */
    public void bitFlip(int memoryIndex,int index){
        solutions[memoryIndex].bitFlip(index);
        flippedIndex[memoryIndex][index]=!flippedIndex[memoryIndex][index];
    }

    /**
     * set the bit on index of solution on memoryIndex to false
     * @param memoryIndex the index of solution
     * @param index the index of item
     */
    public void destroyBit(int memoryIndex, int index){
        if(getOneBitOfSolution(memoryIndex,index)){
            bitFlip(memoryIndex,index);
            flippedIndex[memoryIndex][index]=!flippedIndex[memoryIndex][index];
        }
    }

    /**
     * get current weight of solution
     * @param index the index of solution
     * @return the weight
     */
    public double getWeight(int index) {
        return solutions[index].getWeight();
    }

    /**
     * get current boundary of solution
     * @param index the index of solution
     * @return the boundary
     */
    public double getBoundary(int index) {
        return solutions[index].getBoundary();
    }

    /**
     * the number of items
     * @return number of items
     */
    public int getNumberOfVariables(){
        return bestEverSolution.getNumberOfInstance();
    }

    /**
     * copy the solution from originIndex to destinationIndex
     * @param originIndex index of solution
     * @param destinationIndex index of solution
     */
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

    /**
     * exchange the bit on j index between child1 and child2 solution
     * @param child1 the child1 index
     * @param child2 the child2 index
     * @param j the index of items
     */
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

    /**
     * set the size of population of problem
     * @param populationSize the size of population
     */
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

    /**
     * get the number of memes
     * @return the number of memes
     */
    public int getNumberOfMemes(){
        return this.numberOfMemes;
    }

    /**
     * get the meme
     * @param solutionIndex the index of solution
     * @param memeNumber the index of meme
     * @return the meme
     */
    public Meme getMeme(int solutionIndex,int memeNumber){
        if(solutionIndex<this.solutions.length&&memeNumber<this.getNumberOfMemes()){
            return this.solutions[solutionIndex].getMeme(memeNumber);
        }
        else{
            throw new IndexBoundaryException("solution index or meme number exceeded the total number of solutions or memes!");
        }
    }

    /**
     * set IoM
     * @param intensityOfMutation IoM
     */
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

    /**
     * set DoS
     * @param depthOfSearch DoS
     */
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

    /**
     * get IoM
     * @return IoM
     */
    public int getIntensityOfMutation() {
        return intensityOfMutation;
    }

    /**
     * get DoS
     * @return DoS
     */
    public int getDepthOfSearch() {
        return depthOfSearch;
    }

    /**
     * get the objective value using delta evaluation
     * @param index index of solution
     * @return the objective value
     */
    public double deltaEvaluation(int index) {
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

    /**
     * get the array of different genes indices
     * @param p1 the parent1 index
     * @param p2 the parent2 index
     * @return the array of different genes indices
     */
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

    /**
     * get the last bit of solution
     * @param index the index of solution
     * @return the state of last bit
     */
    public boolean getLastBitOfSolution(int index){
        return getOneBitOfSolution(index,getNumberOfVariables()-1);
    }

    /**
     * get one bit of solution
     * @param solutionIndex the index of solution
     * @return the state of one bit
     */
    public boolean getOneBitOfSolution(int solutionIndex, int i){
        Instance[] instances=solutions[solutionIndex].getInstance();
        return instances[i].isState();
    }

    /**
     * get the index of bit with the highest profit
     * @param index index of solution
     * @return the index of bit with the highest profit
     */
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

    /**
     * get the index of bit with the highest weight
     * @param index index of solution
     * @return the index of bit with the highest weight
     */
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

    /**
     * get the index of bit with the lowest profit
     * @param index index of solution
     * @return the index of bit with the lowest profit
     */
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

    /**
     * get the index of bit with the lowest weight
     * @param index index of solution
     * @return the index of bit with the lowest weight
     */
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

    /**
     * get the index of bit with the highest profit per weight
     * @param index index of solution
     * @return the index of bit with the highest profit per weight
     */
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

    /**
     * get the index of bit with the lowest profit per weight
     * @param index index of solution
     * @return the index of bit with the lowest profit per weight
     */
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

    /**
     * get the random generator
     * @return the random generator
     */
    public Random getRandom() {
        return random;
    }

    /**
     * get whether weight is greater than boundary
     * @param index the index of solution
     * @return whether weight is greater than boundary
     */
    public boolean isOverWeight(int index){
        return getWeight(index)>getBoundary(index);
    }

    /**
     * the inner class that stores the index and objective value of solution
     */
    class IndexValue{
        int index;
        double value;
        public IndexValue(int index,double value){
            this.index=index;
            this.value=value;
        }
    }
}
