package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class NDHC_IE extends NDHC{
    public NDHC_IE(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate>=current;
    }

}
