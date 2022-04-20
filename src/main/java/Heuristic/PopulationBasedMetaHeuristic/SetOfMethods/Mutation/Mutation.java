package Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation;

import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Problem.Problem;

/**
 * this class will mutate the solution
 */
public abstract class Mutation extends PopulationHeuristic {
    /**
     * create object
     * @param problem the problem need to be solved
     */
    public Mutation(Problem problem) {
        super(problem);
    }

    /**
     * get the objective value using delta evaluation
     * @param index the index of solution
     * @return the objective valur
     */
    public double deltaEvaluation(int index){
        return problem.deltaEvaluation(index);
    }
}
