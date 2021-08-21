import java.util.*;
import java.util.concurrent.TimeUnit;


public class Blackjack {
    public static void main(String[] args ) throws Exception {
		boolean gameOver = false;

		while(!gameOver) {
			int currentValue = 0; // Track value of player cards
			int dealerCurVal = 0; // Track value of dealer cards

			boolean userDone = false; // If user is done taking cards
			boolean roundOver = false; // If round is over

			currentValue = getCard(currentValue,2); // Starting 2 cards
			System.out.println("Your starting value is: " + currentValue);

			dealerCurVal = getCard(dealerCurVal,1);
			System.out.println("Dealers starting value is: " + dealerCurVal); // Dealers starting value

			while (!roundOver) { // While round still going
				while (!userDone) { // While user still taking cards
					System.out.println();
					while (true) { // Loop until user enters Y or N
						System.out.println("Your currentValue is: " + currentValue);
						System.out.println("Do you want another card? Y or N");

						boolean answer = askQ();

						if (answer) {
							int newCard = getCard(currentValue, 1); // Get card
							currentValue = currentValue + newCard; // Add on to value

							System.out.println("New card is worth " + newCard);
							System.out.println("Your total value is now " + currentValue + "\n");
							break; // Exit while loop
						} else if (!answer) {
							userDone = true; // User doesn't want any cards
							break;
						}
					}

					if (currentValue == 21) {
						System.out.println("You got 21!");
						userDone = true;
					} else if (currentValue > 21) { // User busted
						System.out.println("Value is over 21! Value was: " + currentValue);
						userDone = true;
					}
				}

				System.out.println();
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Dealers go, your cards are worth: " + currentValue);
				dealerCurVal += getCard(dealerCurVal, 1);
				System.out.println("Dealers cards are now worth " + dealerCurVal);

				while (true) {
					TimeUnit.SECONDS.sleep(1);
					if (currentValue > 21) { // Player busts
						if (dealerCurVal <= 21) { // Player bust, dealer didn't
							System.out.println("\nDealer wins, You went over 21");
							roundOver = true;
							break;
						} else if (dealerCurVal > 21) { // Both bust
							System.out.println("\nBoth player and dealer have got over 21");
							roundOver = true;
							break;
						}
					} else { // player didn't bust
						if (dealerCurVal < currentValue && dealerCurVal != 21) { // If dealer is less than players and not 21, get card
							dealerCurVal += getCard(dealerCurVal, 1);
							System.out.println("Dealer got another card. Now has " + dealerCurVal);
						} else if (dealerCurVal == currentValue) { // Both have 21
							System.out.println("\nTie, Both have 21");
							roundOver = true;
							break;
						} else if (dealerCurVal > currentValue && dealerCurVal <= 21) {
							System.out.println("\nDealer wins, Dealer got closer to 21");
							roundOver = true;
							break;
						} else if (dealerCurVal > 21) {
							System.out.println("Dealer went over 21. \nYou win!");
							roundOver = true;
							break;
						} else {
							System.out.println("\nShouldn't get here?");
							System.out.println("PLayers value: " + currentValue);
							System.out.println("Dealers value: " + dealerCurVal);
							roundOver = true;
							break;
						}

					}
				}
			}

			System.out.println("\nRound is over");
			System.out.println("Players value: " + currentValue);
			System.out.println("Dealers value: " + dealerCurVal);


			System.out.println("\nDo you want to stop playing?");
			gameOver = askQ(); // Ask

		}
    }

    public static int getCard(int currentValue, int numOfCards){
//    	System.out.println("Going to get card in getCard");
		int addOn = 0;
    	for(int i=0; i<numOfCards; i++) {
			int card = (int)(Math.random() * 13) + 1;
//			System.out.println("just got, value was: " + card);
			if (card > 10) { // If card is jack, queen, king
				card = 10;
			}else if(card ==1 && currentValue <10){ // Ask what value user wants ace to be worth if they have less than 10 points
					card = 11;
				}
			addOn += card;
			currentValue+= card; // Avoid first two cards both being aces and being worth 11 each
//			System.out.println("Got a card. Value was: " + card);
//			System.out.println("Added on cards value. Total now: " + addOn);
		}

//		System.out.println("End of getCard method. Total now: " + addOn);
    	return addOn;
	}

	public static boolean askQ(){
		Scanner scan = new Scanner(System.in);
		while (true) { // Loop until user enters Y or N
			char c = scan.next().charAt(0); // Get input
			c = Character.toUpperCase(c); // Change to uppercase in case user types y or n

			if (c == 'Y') {
				return true;
			} else if (c == 'N') {
				return false;
			} else {
				System.out.println("Enter Y or N");
			}
		}
	}
}