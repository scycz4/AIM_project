package Problem;

import MyExceptions.WrongOptionException;

public class Meme {

    private int option;
    private int iMaxNumberOfStates;
    /**
     * get the current option of meme
     * @return the current option of meme
     */
    public int getOption() {
        return option;
    }

    /**
     * set the current option of meme
     * @param option the current option of meme
     */
    public void setOption(int option) {

        if(option<0)
            throw new WrongOptionException("option exceeds boundary");
        else if(option>iMaxNumberOfStates)
            throw new WrongOptionException("option should be greater than 0");
        else
            this.option = option;
    }

    /**
     * get the total number of options
     * @return the total number of options
     */
    public int getTotalOptions() {
        return this.iMaxNumberOfStates;
    }

    /**
     * create meme object
     * @param defaultState the default option of meme
     * @param numberOfStates the total number option of meme
     */
    public Meme(int defaultState,int numberOfStates){
        option=defaultState;
        iMaxNumberOfStates=numberOfStates;
    }

}
