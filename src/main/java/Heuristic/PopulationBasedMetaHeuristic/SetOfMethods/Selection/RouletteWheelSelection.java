package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection;

import Problem.Problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class RouletteWheelSelection extends Selection{
    private int populationSize;
    private Random random;
    public RouletteWheelSelection(Problem problem,int populationSize) {
        super(problem);
        this.populationSize=populationSize;
        random=new Random();
    }

    @Override
    public int applySelection() {
        double totalFitness=0;
        IndexValue[] indexValues=new IndexValue[populationSize];
        for(int i=0;i<populationSize;i++){
            double currentFitness=problem.getObjectiveFunctionValue(i);
            totalFitness+=currentFitness;
            indexValues[i]=new IndexValue(currentFitness,i);
        }
        IndexValue.totalFitness=totalFitness;
        Arrays.sort(indexValues, new Comparator<IndexValue>() {
            @Override
            public int compare(IndexValue o1, IndexValue o2) {
                return Double.compare(o2.current,o1.current);
            }
        });

        double prob=0.0;
        double randProb=random.nextDouble();

        for(int i=0;i<populationSize;i++){
            prob+=indexValues[i].getFitnessProb();
            if(randProb<prob){
                IndexValue.totalFitness=0;
                return indexValues[i].index;
            }
        }
        IndexValue.totalFitness=0;
        return random.nextInt(indexValues.length);
    }

    static class IndexValue{
        private double current;
        private int index;
        private static double totalFitness;
        public IndexValue(double current,int index){
            this.current=current;
            this.index=index;
        }

        public double getFitnessProb(){
            return current/totalFitness;
        }


    }
}
