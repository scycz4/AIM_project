package Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.CrossoverHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.ReplacementWithStrongElitists;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.TournamentSelection;
import Problem.Problem;

public class MemeticAlgorithm extends PopulationBasedSearchMethod{
    private final CrossoverHeuristic crossover;
    private final PopulationHeuristic mutation;
    private final PopulationHeuristic localSearch;
    private final ReplacementWithStrongElitists replacement;
    private final TournamentSelection selection;

    private final int tSize=3;

    public MemeticAlgorithm(Problem problem, int populationSize, CrossoverHeuristic crossover,
                            PopulationHeuristic mutation, PopulationHeuristic localSearch, ReplacementWithStrongElitists replacement) {

        super(problem, populationSize);

        this.crossover = crossover;
        this.mutation = mutation;
        this.localSearch = localSearch;
        this.replacement = replacement;
        this.selection = new TournamentSelection(problem, populationSize,tSize);
    }

    @Override
    public void runMainLoop() {
        for (int i = 0; i < POP_SIZE/2; i++) {
            int parent1 = selection.applySelection();
            int parent2 = selection.applySelection();
            // ensure parents are different
            while (parent2 == parent1) parent2 = selection.applySelection();

            crossover.applyHeuristic(parent1, parent2, POP_SIZE+i*2, POP_SIZE+i*2+1);
            mutation.applyHeuristic(POP_SIZE+i*2);
            mutation.applyHeuristic(POP_SIZE+i*2+1);
            localSearch.applyHeuristic(POP_SIZE+i*2);
            localSearch.applyHeuristic(POP_SIZE+i*2+1);
        }
        replacement.doReplacement(problem, POP_SIZE);
    }
}
