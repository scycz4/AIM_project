package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection;

import Problem.Problem;

public abstract class Selection {
    protected Problem problem;
    public Selection(Problem problem){
        this.problem=problem;
    }

    public abstract int applySelection();
}
