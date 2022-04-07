package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class NDHC_IE extends NDHC{
    public NDHC_IE(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(int current, int candidate) {
        return candidate>=current;
    }
}
