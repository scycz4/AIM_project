package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection;

import Problem.Problem;

import java.util.Random;

public class TournamentSelection extends Selection{
    private int POP_SIZE;
    private int tSize;

    public TournamentSelection(Problem problem,int POP_SIZE,int tSize){
        super(problem);
        this.POP_SIZE=POP_SIZE;
        this.tSize=tSize;
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


    @Override
    public int applySelection() {
        int bestIndex=-1;
        double bestFitness= Double.NEGATIVE_INFINITY;

        int[] indices=new int[POP_SIZE];
        for(int i=0;i<POP_SIZE;i++){
            indices[i]=i;
        }
        indices=shuffle(indices,new Random());

        for(int i=0;i<tSize;i++){
            int sol=indices[i];
            double fitness=problem.getObjectiveFunctionValue(sol);

            if(fitness>=bestFitness){
                bestFitness=fitness;
                bestIndex=sol;
            }
        }

        return bestIndex;
    }

    public void setTournamentSize(int tSize){
        this.tSize=tSize;
    }
}
