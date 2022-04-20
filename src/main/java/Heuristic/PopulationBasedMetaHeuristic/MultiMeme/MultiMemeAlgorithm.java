package Heuristic.PopulationBasedMetaHeuristic.MultiMeme;

import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Crossover.*;
import Heuristic.PopulationBasedMetaHeuristic.GeneticAlgorithm.PopulationBasedSearchMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.BestInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.InheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Inheritance.SimpleInheritanceMethod;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.IteratedLocalSearch.*;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.BitMutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.BoundaryMutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.Mutation;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Mutation.RandomBitFlip;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.Replacement;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.ReplacementWithRandomToWorst;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.ReplacementWithStrongElitists;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Replacement.ReplacementWithElitists;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.RuinRecreate.*;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.RankBasedSelection;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.RouletteWheelSelection;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.Selection;
import Heuristic.PopulationBasedMetaHeuristic.SetOfMethods.Selection.TournamentSelection;
import Problem.Problem;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MultiMemeAlgorithm extends PopulationBasedSearchMethod {
    private final double innovationRate;
    private Mutation[] mutation;
    private final CrossoverHeuristic[] crossover;
    private final Replacement[] replacement;
    private final Selection[] selection;

    private final InheritanceMethod[] inheritance;

    private final HillClimb[] lss;

    private ArrayList<Double> best=new ArrayList<Double>();
    private ArrayList<Double> worst=new ArrayList<Double>();

    private static final int tSize=4;

    private final int MUTATION=0;
    private final int CROSSOVER=1;
    private final int LOCAL_SEARCH =2;
    private final int INHERITANCE=3;
    private final int REPLACEMENT=4;
    private final int SELECTION=5;




    public MultiMemeAlgorithm(Problem problem, int populationSize, double innovationRate, CrossoverHeuristic[] crossoverHeuristic
                , Mutation[] mutation, Replacement[] replacement, Selection[] selection, InheritanceMethod[] inheritanceMethod,
                              HillClimb[] lss) {
        super(problem, populationSize);

        this.innovationRate = innovationRate;
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
                new CrossoverHeuristic[]{
                        new OnePointCrossover(problem),
                        new UniformCrossover(problem),
                        new TwoPointCrossover(problem),
                        new ReducedSurrogateCrossover(problem),
                        new OneBitAdaptiveCrossover(problem)
                },
                new Mutation[]{
                        new BitMutation(problem),
                        new RandomBitFlip(problem),
                        new BoundaryMutation(problem),
                        new DestroyHighestValueSolution(problem),
                        new DestroyLowestValueSolution(problem),
                        new DestroyHighestWeightSolution(problem),
                        new DestroyLowestWeightSolution(problem),
                        new DestroyHighestProfitDivWeightSolution(problem),
                        new DestroyLowestProfitDivWeightSolution(problem)
                },
                new Replacement[]{
                        new ReplacementWithElitists(problem,populationSize),
                        new ReplacementWithStrongElitists(problem,populationSize),
                        new ReplacementWithRandomToWorst(problem,populationSize)
                },
                new Selection[]{
                        new TournamentSelection(problem,populationSize,tSize),
                        new RouletteWheelSelection(problem,populationSize),
                        new RankBasedSelection(problem,populationSize)
                },

                new InheritanceMethod[]{
                        new SimpleInheritanceMethod(problem,populationSize),
                        new BestInheritanceMethod(problem,populationSize)
                },
                new HillClimb[]{
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

    public ArrayList<Double> getBest() {
        return best;
    }

    public ArrayList<Double> getWorst() {
        return worst;
    }

    @Override
    protected void runMainLoop() {

        int p1,p2,c1,c2;
        p1=p2=c1=c2=0;
//        for(int i=0;i<POP_SIZE;i++){
//            System.out.print(problem.getObjectiveFunctionValue(i)+" ");
//        }
//        System.out.println();
        for(int i=0;i<POP_SIZE;i+=2){
//            System.out.print("origin:");
//            System.out.print(problem.getObjectiveFunctionValue(p1)+" ");
//            System.out.print(problem.getObjectiveFunctionValue(p2)+" ");
            do{
                p1 = applySelectionForChildDependentOnMeme(p1,SELECTION);
                p2 = applySelectionForChildDependentOnMeme(p2,SELECTION);
            }while(p1==p2);
            c1=i+POP_SIZE;
            c2=c1+1;

            applyCrossoverForChildDependentOnMeme(p1,p2,c1,c2,CROSSOVER);
//            System.out.print("crossover:");
//            System.out.print(problem.getObjectiveFunctionValue(c1)+" ");
//            System.out.print(problem.getObjectiveFunctionValue(c2)+" ");

            applyInheritanceForChildDependentOnMeme(p1,p2,c1,c2,INHERITANCE);

            performMutationOfMemeplex(c1);
            performMutationOfMemeplex(c2);

            applyMutationForChildDependentOnMeme(c1,MUTATION);
            applyMutationForChildDependentOnMeme(c2,MUTATION);

//            System.out.print("mutation:");
//            System.out.print(problem.getObjectiveFunctionValue(c1)+" ");
//            System.out.print(problem.getObjectiveFunctionValue(c2)+" ");


            applyLocalSearchForChildDependentOnMeme(c1, LOCAL_SEARCH);
            applyLocalSearchForChildDependentOnMeme(c2, LOCAL_SEARCH);

//            System.out.print("local search:");
//            System.out.print(problem.getObjectiveFunctionValue(c1)+" ");
//            System.out.print(problem.getObjectiveFunctionValue(c2)+" ");
//            System.out.println();

        }
//        System.out.println();
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        double bestObjValue=problem.getObjectiveFunctionValue(0);
        double worstObjValue=problem.getObjectiveFunctionValue(0);

        for(int i=0;i<POP_SIZE;i++){
            double currentObjValue= problem.getObjectiveFunctionValue(i);
            if(bestObjValue<=currentObjValue){
                bestObjValue=currentObjValue;
            }
            if(worstObjValue>=currentObjValue){
                worstObjValue=currentObjValue;
            }
        }
        best.add(bestObjValue);
        worst.add(worstObjValue);

//        System.out.print("before: ");
//        for(int i=POP_SIZE;i<POP_SIZE*2;i++){
//            System.out.print(problem.getObjectiveFunctionValue(i)+" ");
//        }
//        System.out.println();
        applyReplacementForChildDependentOnMeme(c1,c2,REPLACEMENT);
//        System.out.print("after: ");
//        for(int i=0;i<POP_SIZE;i++){
//            System.out.print(problem.getObjectiveFunctionValue(i)+" ");
//        }
//        System.out.println();
    }

    private int applySelectionForChildDependentOnMeme(int parentIndex, int memeIndex) {
        return selection[problem.getMeme(parentIndex,memeIndex).getOption()].applySelection();
    }

    private void applyReplacementForChildDependentOnMeme(int c1,int c2,int memeIndex) {
        if(problem.getObjectiveFunctionValue(c1)>problem.getObjectiveFunctionValue(c2)){
            replacement[problem.getMeme(c1,memeIndex).getOption()].doReplacement();
        }
        else{
            replacement[problem.getMeme(c2,memeIndex).getOption()].doReplacement();
        }
    }

    private void applyInheritanceForChildDependentOnMeme(int p1, int p2, int c1, int c2, int memeIndex) {
        int index;
        if(problem.getObjectiveFunctionValue(p1)>problem.getObjectiveFunctionValue(p2)){
            index=p1;
        } else {
            index=p2;
        }
        inheritance[problem.getMeme(index,memeIndex).getOption()].performMemeticInheritance(p1,p2,c1,c2);
    }

    public void applyMutationForChildDependentOnMeme(int childIndex, int memeIndex) {

        mutation[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    public void applyCrossoverForChildDependentOnMeme(int p1,int p2,int c1,int c2,int memeIndex){
        int index;
        if(problem.getObjectiveFunctionValue(p1)>problem.getObjectiveFunctionValue(p2)){
            index=p1;
        }else {
            index=p2;
        }
        crossover[problem.getMeme(index,memeIndex).getOption()].applyHeuristic(p1,p2,c1,c2);
    }

    public void applyLocalSearchForChildDependentOnMeme(int childIndex, int memeIndex) {
        lss[problem.getMeme(childIndex,memeIndex).getOption()].applyHeuristic(childIndex);
    }

    public void performMutationOfMemeplex(int solutionIndex) {

        for(int i=0;i< problem.getNumberOfMemes();i++){
            if(rng.nextDouble()<innovationRate){
                int option;
                do{
                    option=rng.nextInt(problem.getMeme(solutionIndex,i).getTotalOptions());
                    if(problem.getMeme(solutionIndex,i).getTotalOptions()==1){
                        break;
                    }
                }
                while(option==problem.getMeme(solutionIndex,i).getOption());
                problem.getMeme(solutionIndex,i).setOption(option);
            }
        }
    }
}
