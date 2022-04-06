package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class DBHC_OI extends DBHC{
    public DBHC_OI(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate<current;
    }
}
