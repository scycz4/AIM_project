package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection;

import Problem.Problem;

import java.util.Random;

public abstract class Selection {
    protected Problem problem;
    protected Random random;
    public Selection(Problem problem){
        this.problem=problem;
        this.random= problem.getRandom();;
    }

    public abstract int applySelection();
}
