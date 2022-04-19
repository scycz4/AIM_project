package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class SDHC_OI extends SDHC{
    public SDHC_OI(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate>current;
    }
}