package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

/**
 * this class is a SDHC only accept solution whose objective value is greater than or equal to previous objective value
 */
public class SDHC_IE extends SDHC{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public SDHC_IE(Problem problem) {
        super(problem);
    }

    /**
     * comparing two objective value, and accept solution whose objective value is greater than or equal to previous objective value
     * @param current current objective value
     * @param candidate candidate objective value
     * @return the result of whether to accept new solution
     */
    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate>=current;
    }
}