package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

public class RMHC_OI extends RMHC{
    public RMHC_OI(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(int current, int candidate) {
        return candidate>current;
    }
}
