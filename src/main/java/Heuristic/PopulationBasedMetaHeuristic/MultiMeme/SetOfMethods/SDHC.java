package Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

public abstract class SDHC extends PopulationHeuristic {
    public SDHC(Problem problem) {
        super(problem, new Random());
    }

    @Override
    public void applyHeuristic(int index) {
        int candidate= this.problem.getObjectiveFunctionValue(index);
        int current = candidate;
        int i = -1;
        for (int j = 0; j < this.problem.getNumberOfVariables(); j++) {
            this.problem.bitFlip(j, index);
            candidate = this.problem.getObjectiveFunctionValue(index);
            if (acceptMove(current, candidate)) {
                i = j;
                current = candidate;
            }
            this.problem.bitFlip(j, index);
        }
        if (i != -1) {
            this.problem.bitFlip(i, index);
        }
    }

    public abstract boolean acceptMove(int current,int candidate);
}
