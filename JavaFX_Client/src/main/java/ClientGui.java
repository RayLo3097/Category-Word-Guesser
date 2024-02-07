import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ClientGui extends Application {
	public static Client client_session;
	private Game game = new Game();
	private static final int  pref_height = 300;
	private static final int pref_width = 550;

	//Connection Screen
	private BorderPane serverConnection_Screen;
	private HBox serverConnection_info;
	private Text connectionScreen_Title;
	private Text server_ip_txt;
	private Text server_port_txt;
	private TextField server_port_txtfld;
	private Button connect_server_btn;


	//Categories Screen
	private BorderPane CategoriesScreen;
	private Text categories_txt;
	private HBox categories_selection_field;
	private VBox category1_vbox;
	private VBox category2_vbox;
	private VBox category3_vbox;
	private Text category1;
	private Button category1_btn;
	private Text category2;
	private Button category2_btn;
	private Text category3;
	private Button category3_btn;

	//Game Screen
	private BorderPane game_screen;
	private VBox guessing_field_vbox;
	private HBox wrong_guesses_field;
	private HBox submit_guess_field;
	private HBox game_info;
	private Text category_chosen_txt;
	private Text display_template_txt;
	private Text wrong_guesses_txt;
	private TextField display_wrong_guesses_txtfld;
	private TextField userGuess_txtfld;
	private Button submit_guess;
	private Text num_letters_txt;
	private Text guesses_left_txt;
	private Text word_guesses_left_txt;

	//End round screen
	private BorderPane round_over_screen;
	private Text round_over_txt;
	private HBox round_over_button_field;
	private Button choose_another_category_btn;
	private Button continue_guessing_btn;

	//Game over screen
	private  BorderPane game_over_screen;
	private HBox game_over_field;
	private Text display_game_results_txt;
	private Button play_again_btn;
	private Button quit_game_btn;


	//Guessed word correctly screen
	private BorderPane word_guessed_screen;
	private Text word_guessed_title;
	private Button select_another_category_btn;

	/**
	 * Builds the connection screen with GUI components
	 * @return connection screen as a scene
	 */
	private Scene build_connection_screen(){
		//Set up GUI components
		connectionScreen_Title = new Text("Connect to Server");
		server_ip_txt = new Text("Ip Address: 127.0.0.1");
		server_port_txt = new Text("Port: ");
		server_port_txtfld = new TextField("Enter port");
		connect_server_btn = new Button("Connect");

		Region region1 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		serverConnection_info = new HBox(server_ip_txt, region1, server_port_txt, server_port_txtfld);

		//Set up borderpane
		serverConnection_Screen = new BorderPane();
		serverConnection_Screen.setTop(connectionScreen_Title);
		serverConnection_Screen.setCenter(serverConnection_info);
		serverConnection_Screen.setBottom(connect_server_btn);

		//styling for connection screen title
		BorderPane.setAlignment(connectionScreen_Title, Pos.CENTER);
		Insets inset1 = new Insets(10, 10, 10, 10);
		BorderPane.setMargin(connectionScreen_Title, inset1);
		connectionScreen_Title.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

		//styling for Hbox Server connection info
		serverConnection_info.setAlignment(Pos.CENTER);
		Insets inset2 = new Insets(10, 20, 10, 20);
		BorderPane.setMargin(serverConnection_info, inset2);
		serverConnection_info.setStyle("-fx-font-size: 13;");

		//styling for connect server button
		BorderPane.setAlignment(connect_server_btn, Pos.CENTER);
		BorderPane.setMargin(connect_server_btn, inset1);

		return new Scene(serverConnection_Screen, pref_width, pref_height);
	}

	/**
	 * Builds the category screen with GUI components
	 * @return category screen as a scene
	 */
	private Scene build_categories_screen(){
		//set up components
		categories_txt = new Text("Categories");
		category1 = new Text("Coding");
		category1_btn = new Button("Select");
		category2 = new Text("Hardware");
		category2_btn = new Button("Select");
		category3 = new Text("Programing Languages");
		category3_btn = new Button("Select");

		//Set up categories VBox
		category1_vbox = new VBox(category1, category1_btn);
		category2_vbox = new VBox(category2, category2_btn);
		category3_vbox = new VBox(category3, category3_btn);

		//Set up category selection screen HBox
		Region region1 = new Region();
		Region region2 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		HBox.setHgrow(region2, Priority.ALWAYS);
		categories_selection_field = new HBox(category1_vbox, region1, category2_vbox, region2, category3_vbox);
		categories_selection_field.setAlignment(Pos.CENTER);

		//VBox alignment
		category1_vbox.setAlignment(Pos.CENTER);
		category1_vbox.setSpacing(10);
		category2_vbox.setAlignment(Pos.CENTER);
		category2_vbox.setSpacing(10);
		category3_vbox.setAlignment(Pos.CENTER);
		category3_vbox.setSpacing(10);

		//Set up border pane
		CategoriesScreen = new BorderPane();
		CategoriesScreen.setTop(categories_txt);
		CategoriesScreen.setCenter(categories_selection_field);

		//Border pane styling
		BorderPane.setAlignment(categories_txt, Pos.CENTER);
		Insets inset = new Insets(10, 0, 10, 0);
		Insets inset2 = new Insets(10, 30, 10, 30);
		BorderPane.setMargin(categories_txt, inset);
		BorderPane.setMargin(categories_selection_field, inset2);
		categories_txt.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

		return new Scene(CategoriesScreen, pref_width, pref_height);
	}

	/**
	 * Builds the game screen with GUI components
	 * @return game screen as a scene
	 */
	private Scene build_game_screen(){
		//Set up GUI components
		category_chosen_txt =  new Text("Category");
		display_template_txt = new Text("Word Here");
		wrong_guesses_txt = new Text("Wrong guesses: ");
		display_wrong_guesses_txtfld = new TextField("");
		userGuess_txtfld = new TextField("Enter letter");
		submit_guess = new Button("Guess");
		num_letters_txt = new Text("Number of letters: ");
		guesses_left_txt = new Text("Letter guesses Left: ");
		word_guesses_left_txt = new Text("Word guesses left: ");

		//Set element attributes
		display_wrong_guesses_txtfld.setEditable(false);

		//Set up VBox and HBox
		wrong_guesses_field = new HBox(wrong_guesses_txt, display_wrong_guesses_txtfld);
		submit_guess_field = new HBox(userGuess_txtfld, submit_guess);
		guessing_field_vbox = new VBox(display_template_txt, wrong_guesses_field, submit_guess_field);

		Region region1 = new Region();
		Region region2 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		HBox.setHgrow(region2, Priority.ALWAYS);
		game_info = new HBox(num_letters_txt, region1, guesses_left_txt, region2, word_guesses_left_txt);

		//Alignment for VBox and HBox
		wrong_guesses_field.setAlignment(Pos.CENTER);
		submit_guess_field.setAlignment(Pos.CENTER);
		guessing_field_vbox.setAlignment(Pos.CENTER);

		wrong_guesses_field.setSpacing(10);
		submit_guess_field.setSpacing(10);
		guessing_field_vbox.setSpacing(10);

		//Set up border pane
		game_screen = new BorderPane();
		game_screen.setTop(category_chosen_txt);
		game_screen.setCenter(guessing_field_vbox);
		game_screen.setBottom(game_info);

		//Set border pane style
		Insets inset1 = new Insets(15, 0, 0, 0);
		Insets inset2 = new Insets(10, 10, 10, 10);
		BorderPane.setMargin(guessing_field_vbox, inset1);
		BorderPane.setMargin(game_info, inset2);
		BorderPane.setAlignment(category_chosen_txt, Pos.CENTER);
		category_chosen_txt.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");

		return new Scene(game_screen, pref_width, pref_height);
	}

	/**
	 * Builds the round over screen with GUI components
	 * @return round over screen as a scene
	 */
	private Scene build_round_over_screen(){
		round_over_txt = new Text("Maximum guesses reached");
		round_over_txt.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

		//set up button field
		choose_another_category_btn = new Button("Select Another Category");
		continue_guessing_btn = new Button("Guess again");
		Region region1 = new Region();
		Region region2 = new Region();
		Region region3 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		HBox.setHgrow(region2, Priority.ALWAYS);
		HBox.setHgrow(region3, Priority.ALWAYS);
		round_over_button_field = new HBox(region1, choose_another_category_btn, region2, continue_guessing_btn, region3);
		round_over_button_field.setAlignment(Pos.CENTER);

		//set up border pane
		round_over_screen = new BorderPane();
		round_over_screen.setTop(round_over_txt);
		round_over_screen.setCenter(round_over_button_field);
		BorderPane.setAlignment(round_over_txt, Pos.CENTER);
		Insets insets = new Insets(10,0,10,0);
		BorderPane.setMargin(round_over_txt, insets);

		return new Scene(round_over_screen, pref_width, pref_height);
	}

	/**
	 * Builds the game over screen with GUI components
	 * @return game over screen as a scene
	 */
	private Scene build_game_over_screen(){
		//Set up gui components
		display_game_results_txt = new Text();
		play_again_btn = new Button("Play again");
		quit_game_btn = new Button("Quit");

		//Set up HBox
		Region region1 = new Region();
		Region region2 = new Region();
		Region region3 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);
		HBox.setHgrow(region2, Priority.ALWAYS);
		HBox.setHgrow(region3, Priority.ALWAYS);
		game_over_field = new HBox(region1, play_again_btn, region2, quit_game_btn, region3);
		game_over_field.setAlignment(Pos.CENTER);

		//Set up border pane
		game_over_screen = new BorderPane();
		game_over_screen.setTop(display_game_results_txt);
		game_over_screen.setCenter(game_over_field);
		Insets inset = new Insets(10,0,10,0);
		BorderPane.setAlignment(display_game_results_txt, Pos.CENTER);
		BorderPane.setMargin(display_game_results_txt, inset);

		//Styling
		display_game_results_txt.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

		return new Scene(game_over_screen, pref_width, pref_height);
	}

	/**
	 * Builds the word guessed screen with GUI components
	 * @return word guessed screen as a scene
	 */
	private Scene build_word_guessed_screen(){
		//Set up gui components
		word_guessed_title = new Text("You guessed the word correctly!");
		select_another_category_btn = new Button("Guess another cagetory");

		//Set up border pane
		word_guessed_screen = new BorderPane();
		word_guessed_screen.setTop(word_guessed_title);
		word_guessed_screen.setCenter(select_another_category_btn);
		Insets inset = new Insets(10, 0, 10, 0);

		//Styling and Alignment
		BorderPane.setAlignment(word_guessed_title, Pos.CENTER);
		BorderPane.setAlignment(select_another_category_btn, Pos.CENTER);
		BorderPane.setMargin(word_guessed_title, inset);
		word_guessed_title.setStyle("-fx-font-weight: bold; -fx-font-size: 16");

		return new Scene(word_guessed_screen, pref_width, pref_height);
	}

	/**
	 * Resets the category screen
	 */
	private void reset_categories_screen(){
		if(game.coding_guessed) {
			category1_btn.setText("Guessed");
			category1_btn.setDisable(true);
		}else{
			category1_btn.setText("Select");
			category1_btn.setDisable(false);
		}

		if(game.hardware_guessed) {
			category2_btn.setText("Guessed");
			category2_btn.setDisable(true);
		}else {
			category2_btn.setText("Select");
			category2_btn.setDisable(false);
		}

		if(game.progLang_guessed){
			category3_btn.setText("Guessed");
			category3_btn.setDisable(true);
		}else {
			category3_btn.setText("Select");
			category3_btn.setDisable(false);
		}
	}

	/**
	 * Checks if the given port is valid
	 * @param port the port to check
	 * @return true/false
	 */
	public static boolean validPort(String port){
		int port_size = port.length();
		for(int i = 0; i < port_size; i++){
			if((int)port.charAt(i) < 48 || (int)port.charAt(i) > 57){ //if char is out of ASCII value of numeric values
				return false;
			}
		}
		return true;
	}

	/**
	 * Reset game variables and GUI components to a proper state
	 */
	private void reset_game_round(){
		game.user_guesses.clear();
		display_template_txt.setText("Word Here");
		display_wrong_guesses_txtfld.clear();
		category_chosen_txt.setText("Category: ");
		num_letters_txt.setText("Number of letters: ");
		guesses_left_txt.setText("Letter guesses left: 6/6");
		word_guesses_left_txt.setText("Word guesses left: 3/3");
	}

	/**
	 * Update the text to display word guesses left
	 */
	private void update_display_words_left(){
		switch (game.category){
			case "Coding":
				word_guesses_left_txt.setText("Word guesses left: " + game.words_left_coding + "/3");
				break;
			case "Hardware":
				word_guesses_left_txt.setText("Word guesses left: " + game.words_left_hardware + "/3");
				break;
			case "Programming Languages":
				word_guesses_left_txt.setText("Word guesses left: " + game.words_left_progLang + "/3");
				break;
		}
	}

	/**
	 * Clears the guesses textfield
	 */
	private void clear_guesses_txtfld(){
		String user_guess = userGuess_txtfld.getText();
		if(user_guess.equals("Enter letter") || user_guess.equals("Invalid! Guess a letter.") || user_guess.equals("Guessed already.")){
			userGuess_txtfld.clear();
		}
	}

	/**
	 * Gets the user's guess to send to server if valid
	 */
	private void submit_user_guess(){
		String guess = userGuess_txtfld.getText().toLowerCase(); //convert guess to lowercase
		userGuess_txtfld.clear();
		boolean valid_input = game.valid_guess(guess);
		if(!valid_input){ //check if guess is valid
			userGuess_txtfld.setText("Invalid! Guess a letter.");
			userGuess_txtfld.selectAll();
			return;
		}
		boolean guessed_letter = game.guessed_already(guess);
		if(guessed_letter){ //check if user already guessed the letter
			userGuess_txtfld.setText("Guessed already.");
			userGuess_txtfld.selectAll();
			return;
		}

		//Valid guess input, send to server
		game.user_guesses.add(guess);
		Message msg = new Message("guess", guess);
		client_session.send_message(msg);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Project 3 Game Client");
		Scene server_connection_scene = build_connection_screen();
		Scene categories_scene = build_categories_screen();
		Scene game_scene = build_game_screen();
		Scene round_over_scene = build_round_over_screen();
		Scene game_over_scene = build_game_over_screen();
		Scene word_guessed_scene = build_word_guessed_screen();
		primaryStage.setScene(server_connection_scene);
		primaryStage.show();

		//Event handlers
		//Clears the server port textfield
		server_port_txtfld.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String text = server_port_txtfld.getText();
				if(text.equals("Enter port") || text.equals("Invalid port")){
					server_port_txtfld.clear();
				}
			}
		});

		//clears the user guess textfield
		userGuess_txtfld.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				clear_guesses_txtfld();
			}
		});

		//submits user's guess
		submit_guess.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				submit_user_guess();
			}
		});

		//submit user's guess
		userGuess_txtfld.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ENTER){
					submit_user_guess();
				}
			}
		});

		//connect to server
		connect_server_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String port_num = server_port_txtfld.getText();
				if(validPort(port_num)) { //check if port is valid
					int port = Integer.parseInt(port_num); //convert port to integer
					connect_server_btn.setDisable(true);
					ClientGui.client_session = new Client(data -> {
						Platform.runLater(()->{
							Message msg = (Message)data;
                            switch (msg.action) {
                                case "word chosen":
                                    display_template_txt.setText(msg.data);
									num_letters_txt.setText("Number of letters: " + msg.get_data_size());
									guesses_left_txt.setText("Letter guesses left: 6/6");
									update_display_words_left();
                                    break;
                                case "guess correct":
                                    display_template_txt.setText(msg.data);
									client_session.send_message(new Message("check guess"));
                                    break;
                                case "wrong guess":
                                    display_wrong_guesses_txtfld.setText(msg.data);
									guesses_left_txt.setText("Letter guesses left: " + Integer.toString(6 - msg.data_size) + "/6");
									client_session.send_message(new Message("check wrong guess"));
                                    break;
								case "max guesses":
									game.update_words_left();
									primaryStage.setScene(round_over_scene);
									break;
								case "max word":
									display_game_results_txt.setText("You lost! You failed to guess a category 3 times");
								 	primaryStage.setScene(game_over_scene);
									break;
								case "guessed word":
									game.update_word_guessed();
									primaryStage.setScene(word_guessed_scene);
									break;
								case "won game":
									display_game_results_txt.setText("You Won! You guessed all 3 categories.");
									primaryStage.setScene(game_over_scene);
									break;
                            }
						});}, port);

					ClientGui.client_session.start();
					long millis = 0;
					//loop until client has connected to server
					while(ClientGui.client_session.socketClient == null && millis < 10000){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							System.out.println("Error waiting to connect to the server");
						}
						millis += 1000;
					}

					if(millis >= 5000){ //if over 5 seconds has passed client has failed connecting
						connect_server_btn.setText("Failed!");
						PauseTransition pause = new PauseTransition(Duration.seconds(2));
						pause.setOnFinished(e -> {connect_server_btn.setDisable(false); connect_server_btn.setText("Connect");});
						pause.play();
						return;
					}
					if(ClientGui.client_session.socketClient.isConnected()){ //if client has connected
						primaryStage.setScene(categories_scene);
						Message msg = new Message("connect", ""); //notify server client has connected
						ClientGui.client_session.send_message(msg);
					}
				}
			}
		});

		category1_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Message msg = new Message("select", "coding");
				ClientGui.client_session.send_message(msg);
				category1_btn.setText("Selected");
				category1_btn.setDisable(true);
				category2_btn.setDisable(true);
				category3_btn.setDisable(true);
				game.category = "Coding";
				category_chosen_txt.setText("Category: " + game.category);
				word_guesses_left_txt.setText("Word guesses left: " + game.words_left_coding);
				display_wrong_guesses_txtfld.clear();
				primaryStage.setScene(game_scene);
			}
		});

		category2_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Message msg = new Message("select", "hardware");
				ClientGui.client_session.send_message(msg);
				category2_btn.setText("Selected");
				category1_btn.setDisable(true);
				category2_btn.setDisable(true);
				category3_btn.setDisable(true);
				game.category = "Hardware";
				category_chosen_txt.setText("Category: " + game.category);
				word_guesses_left_txt.setText("Word guesses left: " + game.words_left_hardware);
				primaryStage.setScene(game_scene);
			}
		});

		category3_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Message msg = new Message("select", "programming languages");
				ClientGui.client_session.send_message(msg);
				category3_btn.setText("Selected");
				category1_btn.setDisable(true);
				category2_btn.setDisable(true);
				category3_btn.setDisable(true);
				game.category = "Programming Languages";
				category_chosen_txt.setText("Category: Programming Languages");
				word_guesses_left_txt.setText("Word guesses left: " + game.words_left_progLang);
				primaryStage.setScene(game_scene);
			}
		});

		choose_another_category_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reset_categories_screen();
				reset_game_round();
				primaryStage.setScene(categories_scene);
			}
		});

		continue_guessing_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Message msg = new Message("select", game.category.toLowerCase());
				String save_category = game.category;
				reset_game_round();
				client_session.send_message(msg);
				category_chosen_txt.setText("Category: " + save_category);
				primaryStage.setScene(game_scene);
			}
		});

		play_again_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.reset_game();
				Message msg = new Message("play again");
				client_session.send_message(msg);
				display_wrong_guesses_txtfld.clear();
				reset_categories_screen();
				primaryStage.setScene(categories_scene);
			}
		});

		quit_game_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Message msg = new Message("quit");
				client_session.send_message(msg);
				System.exit(1);
			}
		});

		select_another_category_btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				reset_game_round();
				reset_categories_screen();
				primaryStage.setScene(categories_scene);
			}
		});

		//when user closes the app
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Message msg = new Message("quit");
				client_session.send_message(msg);
				System.exit(1);
			}
		});

	}

}
