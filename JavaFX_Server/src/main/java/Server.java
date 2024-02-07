import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server{
    public int port;
    int count = 1; //Counting clients
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;

    /**
     * Default constructor for Server
     * @param call things to update gui components
     * @param port port number for server
     */
    Server(Consumer<Serializable> call, int port){
        callback = call;
        this.port = port;
        server = new TheServer();
        server.start();
    }


    public class TheServer extends Thread{

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(port)){
                System.out.println("Server running");
                callback.accept("Server Running");

                while(true) {
                    ClientThread c = new ClientThread(mysocket.accept(), count); //create new client thread
                    clients.add(c); //add new client to arraylist of clients
                    c.start();
                    count++;

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{
        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;
        Game game_session = new Game();

        /**
         * Default constructor for ClientThread
         * @param s the socket for the client
         * @param count the number for the client
         */
        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        /**
         * Sends message to the client
         * @param message Object containing the message
         */
        public void send_message(Object message){
            try {
                out.writeObject(message);
            }
            catch(Exception e) {}
        }

        /**
         * Handles the select request sent by the client
         * @param client_data the data the client has sent
         */
        private void handle_select_request(Message client_data){
            game_session.reset_round();
            game_session.category = client_data.data;
            game_session.choose_word();
            game_session.word_template();
            Message msg = new Message("word chosen", game_session.get_formatted_template(), game_session.get_word_size());
            send_message(msg);
            callback.accept("Server sent \"" + game_session.get_formatted_template() + "\" to client " + count);
            callback.accept("Client " + count + " selected category: " + client_data.data);
        }

        /**
         * Handles the guess request sent by the client
         * @param client_data
         */
        private void handle_guess_request(Message client_data){
            Message msg = new Message();

            //Check if client guessed more than one character
            if (client_data.data.length() > 1) {
                System.out.println("Recieved more than a character");
                return;
            }

            //check if guess is correct or wrong
            String guess = client_data.data;
            boolean correct_guess = game_session.correct_guess(guess);
            if (correct_guess) {
                game_session.fill_template(guess);
                String template = game_session.get_formatted_template();
                msg.set_message("guess correct", template, game_session.get_word_size());
                send_message(msg);
                callback.accept("Server sent guess correct and " + template + " to client " + count);
                callback.accept("Client " + count + " guessed " + guess + " and it is correct");

            }else{
                game_session.add_wrong_guess(guess);
                String wrong_guesses = game_session.format_wrong_guesses();
                msg.set_message("wrong guess", wrong_guesses, game_session.wrong_guesses.size());
                send_message(msg);
                callback.accept("Server sent wrong guess and " + wrong_guesses + " to client " + count);
                callback.accept("Client " + count + " guessed " + guess + " and it is wrong");
            }
        }

        /**
         * handles the checking of the guess requested by the client
         */
        private void handle_guess_condition(){
            Message msg = new Message();
            //If client guessed the word
            boolean guessed_correct_word = game_session.guessed_word_correctly();

            //If client won the game
            boolean client_won_game = game_session.game_won();
            if(client_won_game){
                msg.set_message("won game", "" , 0);
                send_message(msg);
                callback.accept("Server sent \"won game\" to client " + count);
            }

            if(guessed_correct_word){
                msg.set_message("guessed word", "", 0);
                send_message(msg);
                game_session.reset_round();
                callback.accept("Server sent \"guessed correctly\" to client " + count);
                return;
            }
        }

        /**
         * handles the checking of the wrong guess requested by the client
         */
        private void handle_wrong_guess_condition(){
            Message msg = new Message();
            //If client has used all of their guessing attempts
            boolean max_guess_reached = game_session.max_guess_reached();
            if(max_guess_reached){
                game_session.subtract_words_left();
                boolean max_word_guess_reached = game_session.max_word_guess_reached();
                if(max_word_guess_reached){ //if client used all of their word guesses attempts
                    msg.set_message("max word", "", 0);
                    send_message(msg);
                    callback.accept("Server sent max word to client " + count);
                    callback.accept("Client " + count + " has reached the maximum word guesses");
                    return;
                }
                msg.set_message("max guesses", "", 0);
                send_message(msg);
                callback.accept("Server sent \"max guesses\" to client " + count);
                callback.accept("Client " + count + " has reached maximum letter guesses");
                game_session.reset_round();
            }
        }

        /**
         * handles the play again request by the client
         */
        private void handle_playAgain_request(){
            game_session.reset_game();
            game_session.fill_guessable_words();
        }

        /**
         * Handles the client request
         * @param client_data the data that the client has sent to the server
         */
        public void handle_client_action(Message client_data) {
            switch (client_data.action) {
                case "connect":
                    callback.accept("Client " + count + " has connected to server");
                    game_session.fill_guessable_words();
                    break;
                case "select":
                    handle_select_request(client_data);
                    break;
                case "guess":
                    handle_guess_request(client_data);
                    break;
                case "check guess":
                    handle_guess_condition();
                    break;
                case "check wrong guess":
                    handle_wrong_guess_condition();
                    break;
                case "play again":
                    handle_playAgain_request();
                    break;
            }
        }

        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            while(true) {
                try {
                    Message cData = (Message) in.readObject();
                    if(cData.action.equals("quit")) {
                        connection.close();
                        callback.accept("Client " + count + " has exited the game");
                    }
                    handle_client_action(cData);
                }
                catch(Exception e) {
                    callback.accept("Something went wrong with client " + count + ". Closing connection");
                    clients.remove(this);
                    break;
                }
            }
        }//end of run
    }//end of client thread
}






