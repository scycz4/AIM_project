package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;
import de.erichseifert.vectorgraphics2d.eps.EPSProcessor;

import java.util.Random;
import java.util.stream.IntStream;

public abstract class DBHC extends HillClimb {
    public DBHC(Problem problem){
        super(problem,new Random());
    }
    @Override
    public void applyHeuristic(int index) {
        for(int i=0;i<problem.getDepthOfSearch();i++){
            int[] indices= IntStream.range(0,problem.getNumberOfVariables()).toArray();
            int[] perm=shuffle(indices,random);
            double currentCost=this.problem.getObjectiveFunctionValue(index);
            double originCost=currentCost;
            for (int j = 0; j < perm.length; j++) {
                this.problem.bitFlip(index,perm[j]);
                double candidateCost = deltaEvaluation(index);
                if (acceptMove(currentCost, candidateCost)) {
                    currentCost = candidateCost;

                } else {

                    this.problem.bitFlip(index,perm[j]);
                }
            }
            if(originCost>=problem.deltaEvaluation(index)){
                break;
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


