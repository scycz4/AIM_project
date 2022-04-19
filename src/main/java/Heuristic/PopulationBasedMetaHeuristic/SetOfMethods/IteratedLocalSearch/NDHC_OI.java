package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class NDHC_OI extends NDHC{
    public NDHC_OI(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate>current;
    }

}
