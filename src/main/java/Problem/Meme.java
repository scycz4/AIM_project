package Problem;

import MyExceptions.WrongOptionException;

public class Meme {
    public int getOption() {
        return option;
    }

    public boolean setOption(int option) {

        if(option<0)
            throw new WrongOptionException("option exceeds boundary");
        else if(option>iMaxNumberOfStates)
            throw new WrongOptionException("option should be greater than 0");
        else
            this.option = option;
        return option<iMaxNumberOfStates;
    }

    public int getTotalOptions() {
        return this.iMaxNumberOfStates;
    }

    private int option;
    private int iMaxNumberOfStates;

    public Meme(int defaultState,int numberOfStates){
        option=defaultState;
        iMaxNumberOfStates=numberOfStates;
    }

}
