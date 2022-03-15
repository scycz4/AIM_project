package Heuristic.HillClimb;

import Heuristic.HeuristicMethods;
import Problem.Problem;

import java.util.Random;

public class DavisBitHC extends HeuristicMethods {
    @Override
    public void applyHeuristic(Problem problem) {
        int bestEval=problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
        int[] perm=createRandomPermutation(problem);
        for(int j=0;j<problem.getNumberOfVariables(CURRENT_SOLUTION_INDEX);j++){
            problem.bitFlip(CURRENT_SOLUTION_INDEX,perm[j]);
            int tmpEval=problem.getObjectiveFunctionValue(CURRENT_SOLUTION_INDEX);
            if(tmpEval>=bestEval){
                bestEval=tmpEval;
            }
            else{
                problem.bitFlip(CURRENT_SOLUTION_INDEX,perm[j]);
            }
        }
    }

    private int[] createRandomPermutation(Problem problem) {
        int[] arr=new int[problem.getNumberOfVariables(CURRENT_SOLUTION_INDEX)];
        for(int i=0;i<arr.length;i++){
            arr[i]=i;
        }
        arr=shuffle(arr,new Random());
        return arr;
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
