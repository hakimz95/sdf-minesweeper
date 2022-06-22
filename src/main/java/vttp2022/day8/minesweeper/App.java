package vttp2022.day8.minesweeper;
/*
 * Minesweeper consist of a matrix of cells behind which several mines are hidden.
 * To make the game field, use two 2d arrays
 * First one will contain all the numbers and bombs
 * Second one will contain only the data that will be displayed on the screen
 * Set up and placement of the bombs will be randomized
 * The player will be prompted to input a row and column number
 * Selected cell will  get exposed, bomb and game over if not the cell's neighbour will be displayed
 */

public class App extends setup
{
    public static void main( String[] args )
    //When the code is run, the main method will create an object belonging to class 
    {
        //setupGame M = new setupGame();
        setup M = new setup();
        M.startGame();
    }
}
