package Heuristic.HyperHeuristic;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;

public class RouletteWheelSelection {
        public HeuristicPair[] heuristicPairs;

        public LinkedHashMap<HeuristicPair,Integer> heuristicScores;

        public final int upperBound;

        public final int lowerBound;

        public final int defaultScore;

        public final Random rng;

        public RouletteWheelSelection(HeuristicPair[] hs,int defaultScore,int lower,int upper,Random random){
            this(new LinkedHashMap<HeuristicPair, Integer>(), hs, defaultScore, lower, upper, random);
        }

        public RouletteWheelSelection(LinkedHashMap<HeuristicPair, Integer> heuristic_scores, HeuristicPair[] hs,
                                  int default_score, int lower_bound, int upper_bound, Random rng) {

            this.heuristicPairs=hs;
            this.heuristicScores=heuristic_scores;
            this.upperBound=upper_bound;
            this.lowerBound=lower_bound;
            this.defaultScore=default_score;
            this.rng=rng;

            for(HeuristicPair h:hs){
                this.heuristicScores.put(h,default_score);
            }

        }

        public int getScore(HeuristicPair h){
            return heuristicScores.get(h);
        }

        public void incrementScore(HeuristicPair h){
            int value=heuristicScores.get(h);
            value++;
            if(value==upperBound){
                return;
            }
            heuristicScores.put(h,value);
        }

        public void decrementScore(HeuristicPair h){
            int value=heuristicScores.get(h);
            value--;
            if(value==lowerBound){
                return;
            }
            heuristicScores.put(h,value);
        }

        public int getTotalScore(){
            int total=0;
            for(HeuristicPair hs:heuristicScores.keySet()){
                total+=heuristicScores.get(hs);
            }
            return total;
        }

        public HeuristicPair performRouletteWheelSelection(){
            int total=getTotalScore();
            int value=rng.nextInt(total);
            int cumulative_score=0;
            Set<HeuristicPair> s=heuristicScores.keySet();
            Iterator<HeuristicPair> it=s.iterator();
            HeuristicPair chosen_heuristic=heuristicPairs[rng.nextInt(heuristicPairs.length)];
            while(cumulative_score<value){
                HeuristicPair h=it.next();
                chosen_heuristic=h;
                cumulative_score+=heuristicScores.get(h);
            }
            return chosen_heuristic;
        }
}
