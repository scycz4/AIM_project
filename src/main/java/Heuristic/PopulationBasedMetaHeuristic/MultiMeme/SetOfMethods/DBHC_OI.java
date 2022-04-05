package Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods;

import Problem.Problem;

public class DBHC_OI extends DBHC{
    public DBHC_OI(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(int current, int candidate) {
        return candidate<current;
    }
}
