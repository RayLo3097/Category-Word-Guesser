import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<String> coding_words = new ArrayList<>();
    private ArrayList<String> hardware_words = new ArrayList<>();
    private ArrayList<String> progLang_words = new ArrayList<>();
    private static final String[] coding_word_bank = {"pointer", "function", "conditional", "threads", "dereference", "arguments", "parameters", "algorithm"};
    private static final String[] hardware_word_bank = {"cpu", "ram", "gpu", "mouse", "keyboard", "monitor", "hdd", "sdd", "microphone", "webcam", "usb", "motherboard", "fans"};
    private static final String[] progLang_word_bank = {"cpp", "c", "java", "python", "rust", "typescript", "php", "ruby", "swift", "kotlin", "sql", "javascript", "haskell", "drracket"};
    public String category;
    private String word_to_guess;
    private String template;
    public ArrayList<Character> wrong_guesses = new ArrayList<>();
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
        category = "";
        word_to_guess = "";
        template = "";
        words_left_coding = 3;
        words_left_hardware = 3;
        words_left_progLang = 3;
        coding_guessed = false;
        hardware_guessed = false;
        progLang_guessed = false;
    }

    /**
     * Reset Game variables
     */
    public void reset_game(){
        coding_words.clear();
        hardware_words.clear();
        progLang_words.clear();
        wrong_guesses.clear();
        category = "";
        word_to_guess = "";
        template = "";
        words_left_coding = 3;
        words_left_hardware = 3;
        words_left_progLang = 3;
        coding_guessed = false;
        hardware_guessed = false;
        progLang_guessed = false;
    }

    /**
     * Resets Game variables for next round
     */
    public void reset_round(){
        category = "";
        word_to_guess = "";
        template = "";
        wrong_guesses.clear();
    }

    /**
     * fills arraylist with words related to selected category
     */
    public void fill_guessable_words(){
        if(!coding_words.isEmpty()){
            coding_words.clear();
        }
        if(!hardware_words.isEmpty()){
            hardware_words.clear();
        }
        if(!progLang_words.isEmpty()){
            progLang_words.clear();
        }

        int array_size = 0;
        array_size = coding_word_bank.length;
        for (int i = 0; i < array_size; i++) {
            coding_words.add(coding_word_bank[i]);
        }

        array_size = hardware_word_bank.length;
        for (int i = 0; i < array_size; i++) {
            hardware_words.add(hardware_word_bank[i]);
        }

        array_size = progLang_word_bank.length;
        for (int i = 0; i < array_size; i++) {
            progLang_words.add(progLang_word_bank[i]);
        }
    }

    /**
     * Randomly choose a word for the current selected category
     */
    public void choose_word(){
        Random rand = new Random();
        int rand_index = 0;
        switch (this.category){
            case "coding":{
                rand_index = rand.nextInt(coding_words.size());
                this.word_to_guess = coding_words.get(rand_index);
                coding_words.remove(rand_index);
                break;
            }
            case "hardware": {
                rand_index = rand.nextInt(hardware_words.size());
                this.word_to_guess = hardware_words.get(rand_index);
                hardware_words.remove(rand_index);
                break;
            }
            case "programming languages": {
                rand_index = rand.nextInt(progLang_words.size());
                this.word_to_guess = progLang_words.get(rand_index);
                progLang_words.remove(rand_index);
                break;
            }
        }
    }

    /**
     * Converts current word to guess into a template with underscores
     */
    public void word_template(){
        int word_size = word_to_guess.length();
        StringBuilder template = new StringBuilder(word_size);
        for(int i = 0; i < word_size; i++){
            template.append("_");
        }
        this.template = template.toString();
    }

    /**
     * Replaces underscore in the template with the correct guess
     * @param guess the guess to replace underscore with
     */
    public void fill_template(String guess){
        ArrayList<Integer> indices = new ArrayList<>();
        int word_size = word_to_guess.length();
        for(int i = 0; i < word_size; i++){ //loop through the word
            if(word_to_guess.charAt(i) == guess.charAt(0)){ //if guess matches a letter in word
                indices.add(i); //add the current index to arraylist
            }
        }

        int indices_size = indices.size();
        for(int i = 0; i < indices_size; i++){ //loop through indices and replace the underscore with the guess
            this.template = this.template.substring(0, indices.get(i)) + guess .charAt(0)+ this.template.substring(indices.get(i) + 1);
        }
    }

    /**
     * Checks if guess is correct
     * @param guess the guess to check
     * @return true/false if guess is in the current word
     */
    public boolean correct_guess(String guess){
        return word_to_guess.contains(guess);
    }

    /**
     * Adds the guess to arraylist of wrong guesses
     * @param guess the guess to add
     */
    public void add_wrong_guess(String guess){
        wrong_guesses.add(guess.charAt(0));
    }

    /**
     * Converts arraylist of wrong guesses into a formatted string
     * @return String containing all the wrong guesses in a, b, c, d format
     */
    public String format_wrong_guesses(){
        StringBuilder formated_string = new StringBuilder();
        int wrong_guesses_size = wrong_guesses.size();
        for(int i = 0; i < wrong_guesses_size; i++){ //loop through wrong_guesses
            if(i + 1 == wrong_guesses_size) { //if it is the last guess
                formated_string.append(wrong_guesses.get(i)); //add letter with no comma and no space
            }else{
                formated_string.append(wrong_guesses.get(i));//add letter with comma and space
                formated_string.append(", ");
            }
        }
        return formated_string.toString();
    }


    /**
     * Check if user uses all attempts for guessing
     * @return true/false if user guessed wrong 6 times
     */
    public boolean max_guess_reached(){
        return this.wrong_guesses.size() >= 6;
    }

    /**
     * Check if user guessed a category 3 times incorrectly
     * @return true/false
     */
    public boolean max_word_guess_reached(){
        if(this.words_left_coding <= 0){
            return true;
        }else if(this.words_left_hardware <= 0){
            return true;
        }else {
            return this.words_left_progLang <= 0;
        }
    }

    /**
     * Subtracts words left on current selected category
     */
    public void subtract_words_left(){
        switch (this.category){
            case "coding":{
                this.words_left_coding--;
                break;
            }
            case "hardware":{
                this.words_left_hardware--;
                break;
            }
            case "programming languages":{
                this.words_left_progLang--;
                break;
            }
        }
    }

    /**
     * Check if client guessed the word correctly and set appropriate game variables
     * @return true/false
     */
    public boolean guessed_word_correctly(){
        if(!(template.equals(word_to_guess))){ //if template doesn't match word
            return false;
        }
        switch (category){
            case "coding":
                this.coding_guessed = true;
                break;
            case "hardware":
                this.hardware_guessed = true;
                break;
            case "programming languages":
                this.progLang_guessed = true;
                break;
        }
        return true;
    }

    /**
     * Checks if client guessed all 3 categories correctly
     * @return true/false
     */
    public boolean game_won(){
        return this.coding_guessed && this.hardware_guessed && this.progLang_guessed;
    }

    /**
     * Gets the current template
     * @return template
     */
    public String get_template(){
        return this.template;
    }

    /**
     * Gets the formatted version of the template
     * @return the formatted template
     */
    public String get_formatted_template(){
        StringBuilder formatted_template = new StringBuilder();
        int template_size = template.length();
        for(int i = 0; i < template_size; i++){
            if(i + 1 == template_size){
                if(template.charAt(i) != '_'){ //if current char is not _
                    formatted_template.append(template.charAt(i)); //add letter
                }else { //otherwise add _
                    formatted_template.append("_");
                }
            }else{
                if(template.charAt(i) != '_'){ //if current char is not _
                    formatted_template.append(template.charAt(i)); //add letter
                }else{ //otherwise add _
                    formatted_template.append("_ ");
                }
            }
        }
        return formatted_template.toString();
    }

    /**
     * Gets the size of the word
     * @return size of word
     */
    public int get_word_size(){
        return this.word_to_guess.length();
    }
}
