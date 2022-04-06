package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;
import java.util.stream.IntStream;

public abstract class DBHC extends PopulationHeuristic {
    public DBHC(Problem problem){
        super(problem,new Random());
    }
    @Override
    public void applyHeuristic(int index) {
        int[] indices= IntStream.range(0,problem.getNumberOfVariables()).toArray();
        int[] perm=shuffle(indices,random);

        double currentCost=this.problem.getObjectiveFunctionValue(index);

        for (int j = 0; j < perm.length; j++) {

            this.problem.bitFlip(index,perm[j]);
            double candidateCost = this.problem.getObjectiveFunctionValue(index);

            if (acceptMove(currentCost, candidateCost)) {

                currentCost = candidateCost;

            } else {

                this.problem.bitFlip(index,perm[j]);
            }
        }
    }

    public abstract boolean acceptMove(double current, double candidate);

    private int[] shuffle(int[] array, Random random) {
        int[] shuffledArray = new int[array.length];
        System.arraycopy(array, 0, shuffledArray, 0, array.length);

        for(int i = 0; i < shuffledArray.length; ++i) {
            int index = random.nextInt(shuffledArray.length);
            int temp = shuffledArray[i];
            shuffledArray[i] = shuffledArray[index];
            shuffledArray[index] = temp;
        }

        return shuffledArray;
    }
}
