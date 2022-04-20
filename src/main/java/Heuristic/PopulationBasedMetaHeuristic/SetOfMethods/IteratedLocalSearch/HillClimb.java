package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

import java.util.Random;

/**
 * this class will search for local optima
 */
public abstract class HillClimb extends PopulationHeuristic {
    public HillClimb(Problem problem) {
        super(problem);
    }

    /**
     * apply this heuristic
     * @param index the index of solution
     */
    @Override
    public abstract void applyHeuristic(int index);

    /**
     * compare current objective value and candidate objective value and return the result
     * @param current current objective value
     * @param candidate candidate objective value
     * @return the result of comparison
     */
    public abstract boolean acceptMove(double current, double candidate);

    /**
     * return the objective value using delta evaluation
     * @param index the index of solution
     * @return the objective value
     */
    public double deltaEvaluation(int index){
        return problem.deltaEvaluation(index);
    }
}
