/*
set up 3X3 2d array
fill from 1-9
#||_0_|_1_|_2_
0|| 1 | 2 | 3
1|| 4 | 5 | 6
2|| 7 | 8 | 9

ask what number to replace with X 
enter X at index
print array
check(array) method

check if 3 in a row
for(int row =1; row<=3; row++){ selects row 
	if(arr[row][1] = X && arr[row][2] = X && arr[row][3] = X){
		X wins!!
	if(arr[row][1] = O && arr[row][2] = O && arr[row][3] = O){
		O wins

ask number to replace with O 
enter X at index
print array

repeat until someone wins
if 9 inputs and nobody wins 
	print draw!, ask to reset game
 if somebody wins
	Winner! Ask to reset game


================================================================================================================
TODO:
	CHECK IF POSITION HAS ALREADY BEEN USED ===
	ASK USER FOR POSITION IF NOT NUMBER 1-9 ===
	Ask user again if they want to play when they don't input y or n ===
	Improve Check method?? ===
	Track turns, wins etc and print when user quits?? XXX
	Replace empty print statements with \n to make code neater ===
	STOP USER ENTERING LETTERS ===
================================================================================================================


Notes: 
	Loser starts next round
*/

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class TicTacToe {
    public static void main(String args[] ) throws Exception {
        Scanner scan = new Scanner(System.in);
        Boolean roundWon; // Tracks if someone has won
        Boolean validInput = false; // Tracks if users input was valid
        char enter = 'X'; // Character to enter
        int turns = 0; // Tracks number of turns
        int pos = 0; // Position to enter character

        // Used for stats. Printed at end of game
        int totalTurns = 0;
        int totalRounds = 0;
        int xWins = 0;
        int oWins = 0;
        long startTime = System.currentTimeMillis(); // Get current time

        char gameBoard[][] = makeArray(); // Create 2d array

		System.out.println("Enter name of player 1."); // Ask for name
    	String player1name = scan.nextLine();

    	System.out.println("\n" + "Enter name of player 2."); // Ask for name
    	String player2name = scan.nextLine();

    	String activePlayer = player2name;

        while(true){ // While game is still active, set to false at end of game if player chooses to end
        	// Swap player turn
        	if(activePlayer == player2name){ // If it's player 1s turn X will be entered onto board
        		enter = 'X';
        		activePlayer = player1name; // Change active player name
        	}else{ // It's player 2s turn so O will be entered onto board
        		enter = 'O';
        		activePlayer = player2name;
        	}
            validInput = false; 
            turns++; // Increase number of turns
            
        	printGame(gameBoard); // Print board

        	System.out.println("\n" + activePlayer + ", enter a position to place " + enter); // Ask activePlayer for position
            // Check user has entered valid position
            while(validInput == false){ // Loops until input is valid
        		while (!scan.hasNextInt()) { // If user didn't enter an integer.
                    System.out.println("Invalid position. Please enter a position between 1 and 9.");
                    scan.next();
                }
                pos = scan.nextInt();
                validInput = checkInput(pos, gameBoard); // Check if input is valid.
            }
            

        	gameBoard[(pos-1)/3][(pos-1)%3] = enter; // Enter character at selected positon
        	roundWon = checkWin(gameBoard, enter); // Check if someone has won

        	// If someone has won the round or the board is full
        	if(roundWon == true || turns == 9){
        	    if(turns == 9 && roundWon == false){ // If the board is full and nobody has won
        	    	System.out.println("Game has ended in a draw!");
        	    }else{
        	    	 System.out.println("\n" + activePlayer + " wins!"); // Game has ended and isn't a draw. Print winner
        	    	 sortName(activePlayer); // Sort winners name alphabetically.
        	    	 if(activePlayer == player1name){
        	    	 	xWins++; // Increase number of time X has won.
        	    	 }else{
        	    	 	oWins++; // Increase number of time O has won.
        	    	 }
        	    	 
        	    }

        	    totalTurns += turns; // Add turns from round to tatalTurns, for ending stats
        	    totalRounds++; // Increase number of rounds, for ending stats
        	    printGame(gameBoard); // Print out game board 
        	    System.out.println("\n" + "Do you want to play again? Enter Y or N."); // Ask if player wants to play again
        	    
        	    
        	    while(true){ // Loops until user chooses to play again or quit
        	    	char c = scan.next().charAt(0); // Get input
        	    	c = Character.toUpperCase(c); // Change to uppercase in case user types y or n

	        	    if(c =='Y'){
	        	    	gameBoard = makeArray(); // Remake array, starting game over
	        	    	turns = 0; // Reset number of turns
	        	    	break; // Exit while loop
	        	    }else if(c == 'N'){
	        	    	endGame(startTime, totalRounds, totalTurns, xWins, oWins, player1name, player2name); // Call endGame method, prints stats and ends game
	        	    }else{ // User didn't input Y or N
	        	        System.out.println(c + " is an invalid input. Please enter Y or N.");
	        	    }
	        	}
        	}
        }
	}


    // Check if users position is valid
	public static Boolean checkInput(int pos, char game[][]){
     	// Position entered is not a number 1-9
     	if(pos <1 || pos > 9){ 
    		System.out.println(pos + " is an invalid position. Pick another position.");
    		return false;
    	}
    	
    	// Check if position they entered has already been used
    	if(game[((pos-1)/3)][((pos-1)%3)] == 'X' || game[((pos-1)/3)][((pos-1)%3)] == 'O'){
    		System.out.println(pos + " has already been chosen. Pick another position.");
    		return false;
    	}
    	return true; // Position is valid
	}
	

	// Create array and fill with numbers 1-9 to help user select input position
	public static char[][] makeArray(){
	    System.out.println("\n" + "===== Starting Game =====" + "\n");			
	 	 char [][] gameBoard = new char[3][3]; // Create 3X3 character array    
	 	 int num = 1; // Number to be entered
	 	 for(int row = 0; row<3; row++){
	 	 	for(int col = 0; col<3; col++){
	 	 		gameBoard[row][col] = (char)(num + '0'); // Changing num to char to enter into gameBoard character array
	 	 		num++; // Increase number to be entered next loop
	 	 	}
	 	 }   
	 	 return gameBoard; // Return filled array
	}


	// Print game board
	public static void printGame(char game[][]){
     	System.out.println("-------------");
     	for(int row= 0; row<3; row++){
     		System.out.print("| ");
	 	 	for(int col = 0; col<3; col++){
	 	 		System.out.print(game[row][col] + " | ");
	 	 	}
	 	 	System.out.println("\n" + "-------------");
	 	 }
	 	 return;
	}


	// Check if a player has won
	public static Boolean checkWin(char gameBoard[][], char enter){
        for(int x =0; x<3; x++){ // check if theres a horizontal or vertical win
			if(gameBoard[x][0] == enter && gameBoard[x][1] == enter && gameBoard[x][2] == enter){ // Check rows
				return true;
			}
			if(gameBoard[0][x] == enter && gameBoard[1][x] == enter && gameBoard[2][x] == enter){ // Check columns
				return true;
			}
		}

		// Check top left to bottom right diagonal
		if(gameBoard[0][0] == enter && gameBoard[1][1] == enter && gameBoard[2][2] == enter){
			return true;
		}

		// Check bottom left to top right diagonal
		if(gameBoard[0][2] == enter && gameBoard[1][1] == enter && gameBoard[2][0] == enter){
			return true;
		}

		return false; // Nobody has won yet
	}

	
	// Sort winners name alphabetically
	public static void sortName(String name){
		name = name.toUpperCase(); // Change name to uppercase
		char charArray[] = name.toCharArray(); // Enter name into character array
		int size = charArray.length; // length of array
		char temp = 'a';
		// Bubble sort name
		for(int i = 0; i < size; i++ ) {
            for(int x = i+1; x < size; x++) {
                if(charArray[i]>charArray[x]) {
                   temp = charArray[i];
                   charArray[i] = charArray[x];
                   charArray[x] = temp;
                }
            }
        }
        
        System.out.print("Fact: " + name + " sorted alphabetically is: ");
        for(int i = 0; i < size; i++ ) {
            System.out.print(charArray[i]); // Print out sorted name
        }
        System.out.println(); // Empty line for formating
        return;
	}

    
    // Print out game stats
	public static void endGame(long startTime, int totalRounds, int totalTurns, int xWins, int oWins, String player1name, String player2name){
     	long gameTime = System.currentTimeMillis() - startTime; // Get length of game in milliseconds
    	long minutes = (gameTime / 1000)  / 60; // Get game length in minutes
		int seconds = (int)((gameTime / 1000) % 60); // Get remaining seconds of game length

        System.out.println("======= Game Stats =======");
        System.out.println("Game length: " + minutes + ":"+ seconds); // Print game length in minutes and seconds
        System.out.println("Total number of rounds played: " + totalRounds);
        System.out.println("Total number of turns: " + totalTurns);
        System.out.println(player1name + " won " + xWins + " time(s).");
        System.out.println(player2name + " won " + oWins + " time(s).");
        if(xWins>oWins){
        	int difference = xWins-oWins;
        	System.out.println(player1name + " won " + difference + " more round(s) so they are the winner!");
        }else if(oWins>xWins){
        	int difference = oWins-xWins;
        	System.out.println(player2name + " won " + difference + " more round(s) so they are the winner!");
        }else{
        	System.out.println("The game is a draw!");
        }

        System.exit(0); // Quits game
        return;
	}
}