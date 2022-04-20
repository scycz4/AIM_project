package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;
import java.util.stream.IntStream;

public class DavisBitHCIE extends PopulationHeuristic {
    public DavisBitHCIE(Problem problem){
        super(problem);
    }

    @Override
    public void applyHeuristic(int memoryIndex) {
        double bestEval=problem.getObjectiveFunctionValue(memoryIndex);
        int[] indices= IntStream.range(0,problem.getNumberOfVariables()).toArray();
        int[] perm=shuffle(indices,random);

        for(int index=0;index<problem.getNumberOfVariables();index++){
            problem.bitFlip(memoryIndex,perm[index]);
            double tempEval=problem.getObjectiveFunctionValue(memoryIndex);

            if(tempEval>=bestEval){
                bestEval=tempEval;
            }
            else{
                problem.bitFlip(memoryIndex,perm[index]);
            }
        }
    }

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
