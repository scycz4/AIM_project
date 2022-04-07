package Heuristic.PopulationBasedMetaHeuristic.MultiMeme;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.*;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.PopulationBasedSearchMethod;
import Heuristic.PopulationBasedMetaHeuristic.PopulationHeuristic;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.BestInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.InheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.SimpleInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.*;
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

    private final InheritanceMethod[] inheritance;

    private final PopulationHeuristic[] lss;

    private ArrayList<Integer> best=new ArrayList<Integer>();
    private ArrayList<Integer> worst=new ArrayList<Integer>();

    public MultiMemeAlgorithm(Problem problem,int populationSize, double innovationRate, RuinRecreate ruinRecreate[], CrossoverHeuristic[] crossoverHeuristic
                , PopulationHeuristic[] mutation, Replacement replacement, TournamentSelection selection, InheritanceMethod[] inheritanceMethod,
                              PopulationHeuristic[] lss) {
        super(problem, populationSize);

        this.innovationRate = innovationRate;
        this.ruinRecreates=ruinRecreate;
        this.crossover = crossoverHeuristic;
        this.mutation = mutation;
        this.replacement = replacement;
        this.selection = selection;
        this.inheritance = inheritanceMethod;
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
                        new UniformCrossover(problem),
                        new TwoPointCrossover(problem)
                },
                new PopulationHeuristic[]{
                        new BitMutation(problem),
                        new RandomBitFlip(problem)
                },
                new Replacement(),
                new TournamentSelection(problem,populationSize),
                new InheritanceMethod[]{
                        new SimpleInheritanceMethod(problem),
                        new BestInheritanceMethod(problem),
                },
                new PopulationHeuristic[]{
                        new DBHC_OI(problem),
                        new DBHC_IE(problem),
                        new SDHC_OI(problem),
                        new SDHC_IE(problem),
                        new RMHC_OI(problem),
                        new RMHC_IE(problem),
                        new NDHC_OI(problem),
                        new NDHC_IE(problem)
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

            applyCrossoverForChildDependentOnMeme(p1,p2,c1,c2,2);

            applyInheritanceForChildDependentOnMeme(p1,p2,c1,c2,POP_SIZE,4);

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

    private void applyInheritanceForChildDependentOnMeme(int p1, int p2, int c1, int c2, int pop_size,int memeIndex) {
        int index;
        if(problem.getObjectiveFunctionValue(c1)>problem.getObjectiveFunctionValue(c2)){
            index=c1;
        } else {
            index=c2;
        }
        inheritance[problem.getMeme(index,memeIndex).getOption()].performMemeticInheritance(p1,p2,c1,c2,pop_size);
    }

    public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {

        // TODO implementation of mutation embedding intensity of mutation from memes
        mutation[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    public void applyCrossoverForChildDependentOnMeme(int p1,int p2,int c1,int c2,int memeIndex){
        int index;
        if(problem.getObjectiveFunctionValue(c1)>problem.getObjectiveFunctionValue(c2)){
            index=c1;
        }else {
            index=c2;
        }
        crossover[problem.getMeme(index,memeIndex).getOption()].applyHeuristic(p1,p2,c1,c2);
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
        int choice=rng.nextInt(ruinRecreates.length);
        ruinRecreates[choice].setIntensityOfMutation(0.3);
        ruinRecreates[choice].applyHeuristic(populationSize);
    }
}
