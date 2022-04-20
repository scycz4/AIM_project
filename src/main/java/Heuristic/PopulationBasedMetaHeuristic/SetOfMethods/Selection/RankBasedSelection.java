package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection;

import Problem.Problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * this class will select parent according to their rank
 */
public class RankBasedSelection extends Selection{
    private int populationSize;

    /**
     * create object
     * @param problem the problem need to be solved
     * @param populationSize the size of population
     */
    public RankBasedSelection(Problem problem, int populationSize) {
        super(problem);
        this.populationSize=populationSize;
    }

    /**
     * rank the parent ascending by their objective value, and calculate the probability by dividing
     * current rank by the total sum of rank, then generate a random double to choose the parent
     * @return the index of parent
     */
    @Override
    public int applySelection() {
        IndexValue[] indexValues=new IndexValue[populationSize];
        for(int i=0;i<populationSize;i++){
            double currentFitness=problem.getObjectiveFunctionValue(i);
            indexValues[i]=new IndexValue(currentFitness,i);
        }
        Arrays.sort(indexValues, new Comparator<IndexValue>() {
            @Override
            public int compare(IndexValue o1, IndexValue o2) {
                return Double.compare(o2.current,o1.current);
            }
        });
        double prob=0.0;
        double randProb=random.nextDouble();
        double[] rank=new double[populationSize];
        double totalRank=(1+populationSize)*populationSize/2.0;
        for(int i=0;i<populationSize;i++){
            rank[i]=(i+1)/totalRank;
        }

        for(int i=0;i<populationSize;i++){
            prob+=rank[i];
            if(randProb<prob){
                return indexValues[i].index;
            }
        }
        return random.nextInt(populationSize);
    }

    /**
     * an inner class to store the index of parent and its objective value
     */
    static class IndexValue{
        private double current;
        private int index;
        public IndexValue(double current,int index){
            this.current=current;
            this.index=index;
        }


    }
}
