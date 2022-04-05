package Heuristic.PopulationBasedMetaHeuristic.MultiMeme.SetOfMethods;

import Problem.Problem;

public class SDHC_IE extends SDHC{
    public SDHC_IE(Problem problem) {
        super(problem);
    }

    @Override
    public boolean acceptMove(int current, int candidate) {
        return candidate<=current;
    }
}
