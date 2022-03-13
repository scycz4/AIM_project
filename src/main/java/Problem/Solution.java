package Problem;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Solution {
    private Instance[] instance;
    private InputStream file;
    private final Meme[] memes;
    private int[] memeStates=null;
    private int boundary;

    public Solution(Instance[] instance,int boundary,int numberOfMemes,int[] memeStates){
        this.instance=instance;
        this.boundary=boundary;
        this.memes=new Meme[numberOfMemes];

        for(int i=0;i<this.memes.length;i++){
            this.memes[i]=new Meme(new Random().nextInt(memeStates[i]),memeStates[i]);
        }

        if(this.memeStates==null){
            this.memeStates=memeStates;
        }
    }

    private Solution(Instance[] instance,int boundary, Meme[] memes, int[] memeStates) {
        this.instance=instance;
        this.boundary=boundary;
        this.memes = memes;
        this.memeStates = memeStates;
    }

    public int getWeight(){
        int value=0;
        for(int i=0;i<this.instance.length;i++){
            int judge=this.instance[i].isState()?1:0;
            value+=judge*this.instance[i].getWeight();
        }
        return value;
    }

    public String getSolutionAsString(){
        StringBuilder s=new StringBuilder();

        for(int i=0;i<instance.length;i++){
            Instance is=instance[i];
            String s1=is.isState()?"1":"0";
            s.append(s1);
        }
        return s.toString();
    }

    public int getObjectiveValue(){
        int value=0;
        for(int i=0;i<instance.length;i++){
            Instance is=instance[i];
            int judge=is.isState()?1:0;
            value+=judge*is.getProfit();
        }
        return value;
    }

    public int getNumberOfInstance(){
        return instance.length;
    }

    public int getBoundary() {
        return boundary;
    }

    public void setBoundary(int boundary) {
        this.boundary = boundary;
    }

    public Instance[] getInstance() {
        return instance;
    }

    public void setInstance(Instance[] instance) {
        this.instance = instance;
    }

    public void bitFlip(int index){
        Instance i=instance[index];
        i.setState(!i.isState());
        instance[index]=i;
    }

    public Solution deepCopy(){
        Instance[] instances=new Instance[this.instance.length];
        Meme[] memes=new Meme[this.memes.length];

        for(int i=0;i<this.instance.length;i++){
            int profit=instance[i].getProfit();
            int weight=instance[i].getWeight();
            boolean state=instance[i].isState();
            Instance is=new Instance(profit,weight);
            is.setState(state);
            instances[i]=is;
        }

        for(int i=0;i<memes.length;i++){
            memes[i]=new Meme(this.memes[i].getOption(),this.memeStates[i]);
        }

        return new Solution(instances,this.boundary,memes,this.memeStates);
    }
}
