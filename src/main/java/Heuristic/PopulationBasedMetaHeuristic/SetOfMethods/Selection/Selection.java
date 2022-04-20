package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection;

import Problem.Problem;

import java.util.Random;

/**
 * select the parent
 */
public abstract class Selection {
    protected Problem problem;
    protected Random random;

    /**
     * create object
     * @param problem the problem need to be solved
     */
    public Selection(Problem problem){
        this.problem=problem;
        this.random= problem.getRandom();;
    }

    /**
     * apply heuristic
     * @return the index of parent
     */
    public abstract int applySelection();
}
