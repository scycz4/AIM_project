package Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods;

import Problem.Problem;

public class DBHC_IE extends DBHC{
    public DBHC_IE(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(int current, int candidate) {
        return candidate<=current;
    }
}
