package Problem;

public class Meme {
    public int getOption() {
        return option;
    }

    public boolean setOption(int option) {
        if(option<iMaxNumberOfStates)
            this.option = option;
        return option<iMaxNumberOfStates;
    }

    public int getTotalOptions() {
        return this.iMaxNumberOfStates;
    }

    private int option;
    private int iMaxNumberOfStates;

    public Meme(int defaulState,int numberOfStates){
        option=defaulState;
        iMaxNumberOfStates=numberOfStates;
    }

}
