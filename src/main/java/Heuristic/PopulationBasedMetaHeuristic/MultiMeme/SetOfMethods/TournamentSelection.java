package Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods;

import Problem.Problem;

import java.util.Arrays;
import java.util.Random;

public class TournamentSelection {
    private int POP_SIZE;
    private Problem problem;

    public TournamentSelection(Problem problem,int POP_SIZE){
        this.problem=problem;
        this.POP_SIZE=POP_SIZE;
    }

    public int tournamentSelection(int tournamentSize){
        int bestIndex=-1;
        double bestFitness= Integer.MIN_VALUE;

        int[] indices=new int[POP_SIZE];
        for(int i=0;i<POP_SIZE;i++){
            indices[i]=i;
        }
        indices=shuffle(indices,new Random());

        for(int i=0;i<tournamentSize;i++){
            int sol=indices[i];
            int fitness=problem.getObjectiveFunctionValue(sol);
            if(fitness>=bestFitness){
                bestFitness=fitness;
                bestIndex=sol;
            }
        }

        return bestIndex;
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
