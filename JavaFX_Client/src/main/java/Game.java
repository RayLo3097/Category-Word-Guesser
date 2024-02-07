import java.util.ArrayList;

public class Game {
    public ArrayList<String> user_guesses;
    public String category;
    public int words_left_coding;
    public int words_left_hardware;
    public int words_left_progLang;
    public boolean coding_guessed;
    public boolean hardware_guessed;
    public boolean progLang_guessed;

    /**
     * Default constructor for Game
     */
    public Game(){
        this.user_guesses = new ArrayList<String>();
        this.category = "";
        this.words_left_coding = 3;
        this.words_left_hardware = 3;
        this.words_left_progLang = 3;
        this.coding_guessed = false;
        this.hardware_guessed = false;
        this.progLang_guessed = false;
    }

    /**
     * Reset all variables for the game
     */
    public void reset_game(){
        this.user_guesses.clear();
        this.category = "";
        this.words_left_coding = 3;
        this.words_left_hardware = 3;
        this.words_left_progLang = 3;
        this.coding_guessed = false;
        this.hardware_guessed = false;
        this.progLang_guessed = false;
    }

    /**
     * Checks if guess is valid
     * @param guess guess to check
     * @return true/false
     */
    public boolean valid_guess(String guess){
        int guess_size = guess.length();
        if(guess_size != 1){ //if guess is not exactly 1 character
            return false;
        }

        return (int) guess.charAt(0) >= 97 && (int) guess.charAt(0) <= 122; //check if character is a lowercase letter
    }

    /**
     * If user guessed a letter already
     * @param guess guess to check
     * @return  true/false
     */
    public boolean guessed_already(String guess){
        return user_guesses.contains(guess);
    }

    /**
     * Update how many words left to guess
     */
    public void update_words_left(){
        switch (this.category){
            case "Coding":
                this.words_left_coding--;
                break;
            case "Hardware":
                this.words_left_hardware--;
                break;
            case "Programming Languages":
                this.words_left_progLang--;
                break;
        }
    }

    /**
     * Update which categories have been guessed
     */
    public void update_word_guessed(){
        switch (this.category){
            case "Coding":
                this.coding_guessed = true;
                break;
            case "Hardware":
                this.hardware_guessed = true;
                break;
            case "Programming Languages":
                this.progLang_guessed = true;
                break;
        }
    }
}
