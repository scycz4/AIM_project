package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class DBHC_IE extends DBHC{
    public DBHC_IE(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate>=current;
    }
}