package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Problem.Problem;

/**
 * this class is a RMHC only accept solution whose objective value is greater than previous objective value
 */
public class RMHC_OI extends RMHC{
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public RMHC_OI(Problem problem) {
        super(problem);
    }

    /**
     * comparing two objective value, and accept solution whose objective value is greater than previous objective value
     * @param current current objective value
     * @param candidate candidate objective value
     * @return the result of whether to accept new solution
     */
    @Override
    public boolean acceptMove(double current, double candidate) {
        return candidate>current;
    }

}
