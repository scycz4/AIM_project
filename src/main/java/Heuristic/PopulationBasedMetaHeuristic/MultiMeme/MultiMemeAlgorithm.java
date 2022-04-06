package Heuristic.PopulationBasedMetaHeuristic.MultiMeme;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.CrossoverHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.OnePointCrossover;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.PTX1;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.UniformCrossover;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.PopulationBasedSearchMethod;
import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.SimpleInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.DBHC_IE;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.DBHC_OI;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.SDHC_IE;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.SDHC_OI;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.BitMutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.RandomBitFlip;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.Replacement;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate.DestroyHighestSolution;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate.DestroyLowestSolution;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate.RuinRecreate;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.TournamentSelection;
import Problem.Problem;

import java.util.ArrayList;

public class MultiMemeAlgorithm extends PopulationBasedSearchMethod {
    private final double innovationRate;
    private PopulationHeuristic[] mutation;
    private final RuinRecreate[] ruinRecreates;
    private final CrossoverHeuristic[] crossover;
    private final Replacement replacement;
    private final TournamentSelection selection;

    private final SimpleInheritanceMethod inheritance;

    private final PopulationHeuristic[] lss;

    private ArrayList<Integer> best=new ArrayList<Integer>();
    private ArrayList<Integer> worst=new ArrayList<Integer>();

    public MultiMemeAlgorithm(Problem problem,int populationSize, double innovationRate, RuinRecreate ruinRecreate[], CrossoverHeuristic[] crossoverHeuristic
                , PopulationHeuristic[] mutation, Replacement replacement, TournamentSelection selection, SimpleInheritanceMethod simpleInheritanceMethod,
                              PopulationHeuristic[] lss) {
        super(problem, populationSize);

        this.innovationRate = innovationRate;
        this.ruinRecreates=ruinRecreate;
        this.crossover = crossoverHeuristic;
        this.mutation = mutation;
        this.replacement = replacement;
        this.selection = selection;
        this.inheritance = simpleInheritanceMethod;
        this.lss = lss;

    }

    public MultiMemeAlgorithm(Problem problem,int populationSize,double innovationRate){
        this(
                problem,
                populationSize,
                innovationRate,
                new RuinRecreate[]{
                        new DestroyHighestSolution(problem),
                        new DestroyLowestSolution(problem),
                },
                new CrossoverHeuristic[]{
                        new OnePointCrossover(problem),
                        new PTX1(problem),
                        new UniformCrossover(problem)
                },
                new PopulationHeuristic[]{
                        new BitMutation(problem),
                        new RandomBitFlip(problem)
                },
                new Replacement(),
                new TournamentSelection(problem,populationSize),
                new SimpleInheritanceMethod(problem),
                new PopulationHeuristic[]{
                        new DBHC_OI(problem),
                        new DBHC_IE(problem),
                        new SDHC_OI(problem),
                        new SDHC_IE(problem)
                }
        );
    }

    public ArrayList<Integer> getBest() {
        return best;
    }

    public ArrayList<Integer> getWorst() {
        return worst;
    }

    @Override
    protected void runMainLoop() {
        final int tSize=3;

        for(int i=0;i<POP_SIZE;i+=2){
            int p1 = selection.tournamentSelection(tSize);
            int p2 = selection.tournamentSelection(tSize);

            int c1=i+POP_SIZE;
            int c2=c1+1;

            applyRuinRecreateForChildDependentOnMeme(POP_SIZE);

            CrossoverHeuristic heuristic=applyCrossoverForChildDependentOnMeme(c1,2);
            heuristic.applyHeuristic(p1,p2,c1,c2);

            inheritance.performMemeticInheritance(p1,p2,c1,c2);

            performMutationOfMemeplex(c1);
            performMutationOfMemeplex(c2);

            applyMutationForChildDependentOnMeme(c1,0);
            applyMutationForChildDependentOnMeme(c2,0);

            applyLocalSearchForChildDependentOnMeme(c1,3);
            applyLocalSearchForChildDependentOnMeme(c2,3);

        }


        int bestObjValue=problem.getObjectiveFunctionValue(0);
        int worstObjValue=problem.getObjectiveFunctionValue(0);
        for(int i=0;i<POP_SIZE;i++){
            int currentObjValue= problem.getObjectiveFunctionValue(i);
            if(bestObjValue<=currentObjValue){
                bestObjValue=currentObjValue;
            }
            if(worstObjValue>=currentObjValue){
                worstObjValue=currentObjValue;
            }
        }

        best.add(bestObjValue);
        worst.add(worstObjValue);

        replacement.doReplacement(problem,POP_SIZE);
    }

    public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {

        // TODO implementation of mutation embedding intensity of mutation from memes
        mutation[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    public CrossoverHeuristic applyCrossoverForChildDependentOnMeme(int childIndex,int memeIndex){
        return crossover[problem.getMeme(childIndex,memeIndex).getOption()];
    }

    public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {
        // TODO implementation of local search dependent on memes
        lss[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    public void performMutationOfMemeplex(int solutionIndex) {

        // TODO implementation of mutation of memeplex
        for(int i=0;i< problem.getNumberOfMemes();i++){
            if(rng.nextDouble()<innovationRate){
                int option;
                do{
                    option=rng.nextInt(problem.getMeme(solutionIndex,i).getTotalOptions());
                }
                while(option==problem.getMeme(solutionIndex,i).getOption());
                problem.getMeme(solutionIndex,i).setOption(option);
            }
        }
    }

    public void applyRuinRecreateForChildDependentOnMeme(int populationSize){
        ruinRecreates[rng.nextInt(ruinRecreates.length)].setIntensityOfMutation(0.3);
        ruinRecreates[rng.nextInt(ruinRecreates.length)].applyHeuristic(populationSize);
    }
}
