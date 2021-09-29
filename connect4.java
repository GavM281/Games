import java.util.*;

public class connect4 {
    public static void main(String args[] ) throws Exception {
        Scanner scan = new Scanner(System.in);
        Boolean roundWon = false; // Tracks if someone has won
        Boolean validInput = false; // Tracks if users input was valid
        Boolean twoPlayer= true; // Track weather game is with two players or computer
        char enter = 'X'; // Character to enter
        int turns = 0; // Tracks number of turns
        int pos = 0; // Position to enter character
        String player1name;
        String player2name;

        // Used for stats. Printed at end of game
        int totalTurns = 0;
        int totalRounds = 0;
        int xWins = 0;
        int oWins = 0;
        long startTime = System.currentTimeMillis(); // Get current time

        char gameBoard[][] = makeArray(); // Create 2d array for game board


        System.out.println("Do you want to play against the computer? Y or N"); // Ask for name
        while(true){ // Loops until user chooses y or n
            char c = scan.next().charAt(0); // Get input
            c = Character.toUpperCase(c); // Change to uppercase in case user types y or n
            if(c =='Y'){
                twoPlayer = false; // Game is player vs computer
                break; // Exit while loop
            }else if(c == 'N'){
                twoPlayer = true; // Game is two people
                break;
            }else{ // User didn't input Y or N
                System.out.println(c + " is an invalid input. Please enter Y or N.");
            }
        }

        // System.out.println("Before names. p1:  " + player1name + "   p2.  " + player2name);
        scan.nextLine();
        System.out.println("Enter name of player 1."); // Ask for name
        player1name = scan.nextLine();

        if(twoPlayer==true){ // If playing against person
            System.out.println("\n" + "Enter name of player 2."); // Ask for name
            player2name = scan.nextLine();
        }else{
            player2name = "Computer"; // Set name to be Computer
        }

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
            
            printGame(gameBoard, activePlayer, roundWon); // Print board

            if(activePlayer == player1name || twoPlayer == true){
                System.out.println("\n" + activePlayer + ", enter the column to place " + enter); // Ask activePlayer for position
            }
            
            // Check user has entered valid position
            while(validInput == false){ // Loops until input is valid
                if(twoPlayer == true){ 
                    // System.out.println("Real person asked for input in if");
                    pos = scan.nextInt();
                }else if(activePlayer == player2name && twoPlayer == false){
                    // System.out.println("Computer asked for input in else if");
                    pos = (int)(Math.random() * 8)-1;
                    System.out.println(pos);
                }else if (activePlayer == player1name && twoPlayer == false){
                    // System.out.println("Real person asked for input in else");
                    pos = scan.nextInt();
                }else{
                    System.out.println("Probably shouldn't get here, twoPlayer: " + twoPlayer + "  activePlayer: "+ activePlayer);
                }
                validInput = checkInput(pos, gameBoard, activePlayer); // Check if input is valid.
            }
            

            gameBoard = placeInput(pos, gameBoard, enter); // Enter character at selected position
            roundWon = checkWin(gameBoard, enter, pos); // Check if someone has won

            // If someone has won the round or the board is full
            if(roundWon == true || turns == 42){ // If somebody won or board is full
                if(turns == 42 && roundWon == false){ // If the board is full and nobody has won
                    System.out.println("Game has ended in a draw!"); // Game is a draw
                }else{
                     System.out.println("\n" + activePlayer + " wins!"); // Game has ended and isn't a draw. Print winner
                     if(activePlayer == player1name){
                        xWins++; // Increase number of time X has won.
                     }else{
                        oWins++; // Increase number of time O has won.
                     }
                     
                }

                totalTurns += turns; // Add turns from round to totalTurns, for ending stats
                totalRounds++; // Increase number of rounds, for ending stats
                printGame(gameBoard, activePlayer,roundWon); // Print out game board 
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
    public static Boolean checkInput(int col, char game[][], String name){
        // System.out.println(" Checking input:  " + col + "  is valid");
        // Position entered is not a number 1-9
            if(col <1 || col > 6){ 
                if(name != "Computer"){
                    System.out.println(col + " is an invalid position. Pick another position.");
                }
                return false;
            }

            if(game[0][col-1] != ' '){ // Checks top row, if it's not empty column is full
                if(name != "Computer"){
                    System.out.println(col + " is full Pick another column.");
                }
                return false;
            }
        
        return true; // Position is valid
    }

    // Check if users position is valid
    public static char[][] placeInput(int col, char game[][], char enter){
        for(int row = 6; row>=0;row--){
            if(game[row][col-1] == ' '){
                game[row][col-1] = enter;
                break;
            }
        }
        
        return game; // Position is valid
    }
    

    // Create game board
    public static char[][] makeArray(){
        System.out.println("\n" + "===== Starting Game =====" + "\n");          
         char [][] gameBoard = new char[7][6]; // Create 7X6 character array    
         for(int row = 0; row<7; row++){
             for(int col = 0; col<6; col++){
                 gameBoard[row][col] = ' ';
             }
         }   
         return gameBoard; // Return filled array
    }


    // Print game board
    public static void printGame(char game[][], String name, Boolean won){
        if(name != "Computer" || won == true){
            System.out.println("  ||_1_|_2_|_3_|_4_|_5_|_6_|");
            for(int row= 0; row<7; row++){
                System.out.print((row+1) + " || ");
                for(int col = 0; col<6; col++){
                    System.out.print(game[row][col] + " | ");
                }
                System.out.println();
             }
        }
         return;
    }


    // Check if a player has won
    public static Boolean checkWin(char game[][], char enter, int col){
        // Need int col to know where char was placed, get row
        col-=1;
        int row = 0;
        for(int i = 0; i<7;i++){
            if(game[i][col] == enter){
                game[i][col] = enter;
                row = i;
                break;
            }
        }
        // Now know exact position character placed at

        // Check 4 in vertical row
        if(row<4 && (game[row][col] == enter) && (game[row+1][col]== enter) && (game[row+2][col] == enter) && (game[row+3][col]) == enter) {
            return true;
        }

        // Check 4 in horizontal row
        for(int x = 0; x<3;x++){
            if((game[row][x] == enter) && (game[row][x+1]== enter) && (game[row][x+2] == enter) && (game[row][x+3]) == enter){
                return true;
            }
        }

        // #||_0_|_1_|_2_| 3 | 4 | 5 
        // 0|| X | O | O |   |   |
        // 1|| X | X | O | O |   |  
        // 2|| X | X | X | O | O |
        // 3|| X | X | X | X | O | O
        // 4||   | X | X | X | X | O
        // 5||   |   | X | X | X | X
        // 6||   |   |   | X | X | X

        // X represents first part below
        // O is second part below

        // top left bottom right
        for(int rowStart = 0; rowStart <= 3; rowStart++){
            int count = 0;
            // int row;
            for( row = rowStart, col = 0; row <= 6 && col <= 5; row++, col++ ){
                if(game[row][col] == enter){
                    count++;
                    if(count >= 4) return true;
                }
                else {
                    count = 0;
                }
            }
        }
        for(int colStart = 1; colStart <= 2; colStart++){
            int count = 0;
            for( row = 0, col = colStart; row <= 6 && col <= 5; row++, col++ ){
                if(game[row][col] == enter){
                    count++;
                    if(count >= 4) return true;
                }
                else {
                    count = 0;
                }
            }
        }



        //top right bottom left
        for(int rowStart = 0; rowStart <= 3; rowStart++){
            int count = 0;
            // int row;
            for( row = rowStart, col = 5; row <= 6 && col >=0 ; row++, col-- ){
                if(game[row][col] == enter){
                    // System.out.println("In 3rd check. count: " + count);
                    count++;
                    if(count >= 4) return true;
                }
                else {
                    count = 0;
                }
            }
        }
        for(int colStart = 5; colStart >= 3; colStart--){
            int count = 0;
            for( row = 0, col = colStart; row <=6 && col >=0; row++, col-- ){
                if(game[row][col] == enter){
                    // System.out.println("In 4th check. count: " + count);
                    count++;
                    if(count >= 4) return true;
                }
                else {
                    count = 0;
                }
            }
        }

        return false; // Nobody has won yet
    }

    
    // Print out game stats
    public static void endGame(long startTime, int totalRounds, int totalTurns, int xWins, int oWins, String player1name, String player2name){
        long gameTime = System.currentTimeMillis() - startTime; // Get length of game in milliseconds
        long minutes = (gameTime / 1000)  / 60; // Get game length in minutes
        int calSeconds = (int)((gameTime / 1000) % 60); // Get remaining seconds of game length

        String seconds = "";
        if(calSeconds<10){ // If seconds less than 10
            seconds = "0" + Integer.toString(calSeconds); // Add 0 in front, eg. remaining time will be 1:04 instead if 1:4
        }else{
            seconds = Integer.toString(calSeconds);
        }
        System.out.println(seconds);

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
