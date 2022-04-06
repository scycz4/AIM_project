package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class SDHC_OI extends SDHC{
    public SDHC_OI(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(int current, int candidate) {
        return candidate>current;
    }
}