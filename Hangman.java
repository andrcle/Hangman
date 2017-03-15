
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;

public class Hangman {

    private String secretWord;
    private ArrayList<Character> correctLetters;
    private ArrayList<Character> incorrectLetters;
    private Scanner sc = new Scanner(System.in);
    
    //Hangman constructor; chooses a word randomly from a text file  
    public Hangman() {
        try {
            Random rand = new Random();
            ArrayList<String> words = new ArrayList<String>(); //stores all words from file
            int count = 0;
            Scanner in = new Scanner(new FileReader("wordbank.txt"));
            
            while (in.hasNextLine()) {
                words.add(in.nextLine());
                count++;
            }
            
            secretWord = words.get(rand.nextInt(words.size()));
            this.correctLetters = new ArrayList<Character>();
            
            for (int i = 0; i < this.secretWord.length(); i++) {
                this.correctLetters.add('_');
            }
            
            this.incorrectLetters = new ArrayList<Character>();
        
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }
    
    //main game loop; prints out the Hangman and the correct and incorrect 
    //guesses and asks for user guess
    public void gameLoop() {
        while(!gameOver()) {
            printHangman();
            
            //Print the correct letters of the secret word
            for (int i = 0; i < this.correctLetters.size(); i++) {
                System.out.print(this.correctLetters.get(i) + " ");
            }
            
            System.out.print("\nIncorrect guesses: ");
            //Print the incorrect guesses of the secret word
            for (int i = 0; i < this.incorrectLetters.size(); i++) {
                System.out.print(this.incorrectLetters.get(i) + " ");
            }
            
            System.out.println();
            System.out.print("\nEnter your guess: ");
            String guess = sc.nextLine();
            userGuess(guess.charAt(0));
        }
        
        System.out.println("The secret word is " + secretWord);
        
        if (gameWon()) {
            System.out.println("Congratulations, you won!");
        }
        else {
            System.out.println("Sorry, you lost");
            printHangman();
        }
    }
    
    //processes the user's guess; if the word contains the character then 
    //correctLetters is updated else incorrectLetters is updated
    private void userGuess(char c) {
        if (correctLetters.contains(c) || incorrectLetters.contains(c)) {
            System.out.println("Already guessed " + c);
        }
        else {
            boolean found = false;
            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == c) {
                    found = true;
                    correctLetters.remove(i);
                    correctLetters.add(i, c);
                }
            }
            if (found == false) {
                incorrectLetters.add(c);
            }
        }
    }
    
    //checks if user won 
    private boolean gameWon() {
        return (!correctLetters.contains('_'));
    }
    
    //checks if user lost
    private boolean gameLost() {
        return (incorrectLetters.size() == 7);
    }
    
    //checks if any of the game win or game lost conditions are met
    private boolean gameOver() {
        return (gameWon() || gameLost());
    }
    
    //prints out Hangman based on number of incorrect guesses
    private void printHangman() {
        int poleLines = 6;   // number of lines for hanging pole
        System.out.println("  ____");
        System.out.println("  |  |");

        int badGuesses = this.incorrectLetters.size();
        if (badGuesses == 7) {
            System.out.println("  |  |");
            System.out.println("  |  |");
        }

        if (badGuesses > 0) {
            System.out.println("  |  O");
            poleLines = 5;
        }
        
        if (badGuesses > 1) {
            poleLines = 4;
            if (badGuesses == 2) {
                System.out.println("  |  |");
            } else if (badGuesses == 3) {
                System.out.println("  | \\|");
            } else if (badGuesses >= 4) {
                System.out.println("  | \\|/");
            }
        }
        
        if (badGuesses > 4) {
            poleLines = 3;
            if (badGuesses == 5) {
                System.out.println("  | /");
            } else if (badGuesses >= 6) {
                System.out.println("  | / \\");
            }
        }
        
        if (badGuesses == 7) {
            poleLines = 1;
        }

        for (int k = 0; k < poleLines; k++) {
            System.out.println("  |");
        }
        
        System.out.println("__|__");
        System.out.println();
    }
    
    public static void main(String[] args) {
        Hangman game = new Hangman();
        game.gameLoop();
    }
    
}
