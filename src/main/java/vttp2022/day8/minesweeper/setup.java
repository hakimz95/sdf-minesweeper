package vttp2022.day8.minesweeper;

import java.util.Random;
import java.util.Scanner;

public class setup {
     //Initialize two 2d arrays
     protected int[][] visibleField = new int [10] [10]; 
     protected int[][] hiddenField = new int [10] [10];

     /*
      * Setup the minesweeper playfield
      * Run the game until the player wins/loses
      */
     public void startGame() {
        System.out.println("\n\n================Welcome to Minesweeper ! ================\n");
        setupField(1);

        boolean flag = true;
        while(flag) {
            displayVisible();
            flag = playMove();
            if(checkWin()) {
                displayHidden();
                System.out.println("\n================Congratulations! You won!================");
                break;
            }
        }
     }

     /* Generate random integers from 0-9 for row and column values 
      * Place a bomb at the random locations
      */


     public void setupField(int diff) {
        int var = 0;
        while(var!=10) {
            Random randomBomb = new Random();
            int i = randomBomb.nextInt(10);
            int j = randomBomb.nextInt(10);
            hiddenField[i][j] = 100;
            var++;
        }
        buildHidden(); 
     }

     /* After setting up the mines, build the hidden matrix
      * consisting of the mine proximity neighbour numbers and mines
      * We will choose each cell and count the number of bombs present 
      * in all of its neighbouring cells
      * The value will be saved in the hidden matrix cell
      */
     public void buildHidden() {
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int cnt = 0;
                if(hiddenField[i][j] != 100) {
                    if(i != 0) {
                        if(hiddenField[i-1][j] == 100) cnt++;
                        if(j != 0) {
                            if(hiddenField[i-1][j-1] == 100) cnt++;
                        }
                    }

                    if(i != 9) {
                        if(hiddenField[i+1][j] == 100) cnt++;
                        if(j != 0) {
                            if(hiddenField[i+1][j-1] == 100) cnt++;
                        }
                    }

                    if(j != 0) {
                        if(hiddenField[i][j-1] == 100) cnt++;
                        if(i != 0) {
                            if(hiddenField[i-1][j-1] == 100) cnt++;
                        }
                    }

                    if(j != 9) {
                        if(hiddenField[i][j+1] == 100) cnt++;
                        if(i != 9) {
                            if(hiddenField[i+1][j+1] == 100) cnt++;
                        }
                    }

                    hiddenField[i][j] = cnt;
                    
                }
            }
        }
     }

     //Display current progress of the game to the player after each move
     public void displayVisible() {
        System.out.print("\t");
        for(int i = 0; i < 10; i++) 
            System.out.print(" " + i + " ");
        System.out.print("\n");
        for(int i = 0; i < 10; i++) {
            System.out.print(i + "\t| ");
            for(int j = 0; j < 10; j++) {
                if(visibleField[i][j] == 0)
                    System.out.print("?");
                else if(visibleField[i][j] == 50)
                    System.out.print("?");
                else 
                    System.out.print(visibleField[i][j]);
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
     }

     //Allow a player to select a cell, expose the selected cell and its neighbours
     public boolean playMove() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Row Number: ");
        int i = sc.nextInt();
        System.out.print("Enter Column Number: ");
        int j= sc.nextInt();

        if(i<0 || i>9 || j<0 || j>9 || visibleField[i][j]!=0) {
            System.out.print("\nIncorrect Input");
            return true;
        }

        if(hiddenField[i][j] == 100) {
            displayHidden();
            System.out.print("Oops! You stepped on a mine!\n============GAME OVER============");
            return false;
        }

        else if(hiddenField[i][j] == 0) 
            fixVisible(i, j);
        else
            fixNeighbours(i, j);

        return true;
     }

     public void fixVisible(int i, int j)
    {
        visibleField[i][j] = 50;
        if(i!=0)
        {
            visibleField[i-1][j] = hiddenField[i-1][j];
            if(visibleField[i-1][j]==0) visibleField[i-1][j] = 50;
            if(j!=0)
            {
                visibleField[i-1][j-1] = hiddenField[i-1][j-1];
                if(visibleField[i-1][j-1]==0) visibleField[i-1][j-1]=50;

            }
        }
        if(i!=9)
        {
            visibleField[i+1][j]=hiddenField[i+1][j];
            if(visibleField[i+1][j]==0) visibleField[i+1][j]=50;
            if(j!=9)
            {
                visibleField[i+1][j+1]= hiddenField[i+1][j+1];
                if(visibleField[i+1][j+1]==0) visibleField[i+1][j+1] = 50;
            }
        }
        if(j!=0)
        {
            visibleField[i][j-1]=hiddenField[i][j-1];
            if(visibleField[i][j-1]==0) visibleField[i][j-1] = 50;
            if(i!=9)
            {
                visibleField[i+1][j-1]=hiddenField[i+1][j-1];
                if(visibleField[i+1][j-1]==0) visibleField[i+1][j-1] = 50;
            }
        }
        if(j!=9)
        {
            visibleField[i][j+1]=hiddenField[i][j+1];
            if(visibleField[i][j+1]==0) visibleField[i][j+1] = 50;
            if(i!=0)
            {
                visibleField[i-1][j+1]=hiddenField[i-1][j+1];
                if(visibleField[i-1][j+1]==0) visibleField[i-1][j+1] = 50;
            }
        }
    }

    public void fixNeighbours(int i, int j)
    {
        Random randomBomb = new Random();
        int x = randomBomb.nextInt()%4;

        visibleField[i][j] = hiddenField[i][j];

        if(x==0)
        {
            if(i!=0)
            {
                if(hiddenField[i-1][j]!=100)
                {
                    visibleField[i-1][j] = hiddenField[i-1][j];
                    if(visibleField[i-1][j]==0)  visibleField[i-1][j] = 50;
                }
            }
            if(j!=0)
            {
                if(hiddenField[i][j-1]!=100)
                {
                    visibleField[i][j-1] = hiddenField[i][j-1];
                    if(visibleField[i][j-1]==0)  visibleField[i][j-1] = 50;
                }

            }
            if(i!=0 && j!=0)
            {
                if(hiddenField[i-1][j-1]!=100)
                {
                    visibleField[i-1][j-1] = hiddenField[i-1][j-1];
                    if(visibleField[i-1][j-1]==0)  visibleField[i-1][j-1] = 50;
                }

            }
        }
        else if(x==1)
        {
            if(i!=0)
            {
                if(hiddenField[i-1][j]!=100)
                {
                    visibleField[i-1][j] = hiddenField[i-1][j];
                    if(visibleField[i-1][j]==0)  visibleField[i-1][j] = 50;
                }
            }
            if(j!=9)
            {
                if(hiddenField[i][j+1]!=100)
                {
                    visibleField[i][j+1] = hiddenField[i][j+1];
                    if(visibleField[i][j+1]==0)  visibleField[i][j+1] = 50;
                }

            }
            if(i!=0 && j!=9)
            {
                if(hiddenField[i-1][j+1]!=100)
                {
                    visibleField[i-1][j+1] = hiddenField[i-1][j+1];
                    if(visibleField[i-1][j+1]==0)  visibleField[i-1][j+1] = 50;
                }
            }
        }
        else if(x==2)
        {
            if(i!=9)
            {
                if(hiddenField[i+1][j]!=100)
                {
                    visibleField[i+1][j] = hiddenField[i+1][j];
                    if(visibleField[i+1][j]==0)  visibleField[i+1][j] = 50;
                }
            }
            if(j!=9)
            {
                if(hiddenField[i][j+1]!=100)
                {
                    visibleField[i][j+1] = hiddenField[i][j+1];
                    if(visibleField[i][j+1]==0)  visibleField[i][j+1] = 50;
                }

            }
            if(i!=9 && j!=9)
            {
                if(hiddenField[i+1][j+1]!=100)
                {
                    visibleField[i+1][j+1] = hiddenField[i+1][j+1];
                    if(visibleField[i+1][j+1]==0)  visibleField[i+1][j+1] = 50;
                }
            }
        }
        else
        {
            if(i!=9)
            {
                if(hiddenField[i+1][j]!=100)
                {
                    visibleField[i+1][j] = hiddenField[i+1][j];
                    if(visibleField[i+1][j]==0)  visibleField[i+1][j] = 50;
                }
            }
            if(j!=0)
            {
                if(hiddenField[i][j-1]!=100)
                {
                    visibleField[i][j-1] = hiddenField[i][j-1];
                    if(visibleField[i][j-1]==0)  visibleField[i][j-1] = 50;
                }

            }
            if(i!=9 && j!=0)
            {
                if(hiddenField[i+1][j-1]!=100)
                {
                    visibleField[i+1][j-1] = hiddenField[i+1][j-1];
                    if(visibleField[i+1][j-1]==0)  visibleField[i+1][j-1] = 50;
                }
            }
        }
    }

    //Check if player has evaded all mines on the field
    public boolean checkWin()
    {
        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++)
                if(visibleField[i][j]==0)
                    if(hiddenField[i][j]!=100)
                        return false;
        
        return true; //Return a boolean value to the startGame method
    }

    public void displayHidden()
    {
        System.out.print("\t ");
        for(int i=0; i<10; i++)
            System.out.print(" " + i + "  ");
        System.out.print("\n");
        for(int i=0; i<10; i++)
        {
            System.out.print(i + "\t| ");
            for(int j=0; j<10; j++)
            {
                if(hiddenField[i][j]==0)
                {
                    System.out.print(" ");
                }
                else if(hiddenField[i][j]==100)
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print(hiddenField[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }

}
