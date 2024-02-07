import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTest {

	@Test
	void default_constructor_01() {
		Game game = new Game();
		assertNotNull(game.user_guesses);
		assertEquals(0, game.user_guesses.size());
		assertEquals("", game.category);
		assertEquals(3, game.words_left_coding);
		assertEquals(3, game.words_left_hardware);
		assertEquals(3, game.words_left_progLang);
		assertFalse(game.coding_guessed);
		assertFalse(game.hardware_guessed);
		assertFalse(game.progLang_guessed);
	}

	@Test
	void reset_game_01(){
		Game game = new Game();
		game.category = "coding";
		game.words_left_coding = 1;
		game.words_left_hardware = 2;
		game.words_left_progLang = 3;
		game.coding_guessed = false;
		game.hardware_guessed = false;
		game.progLang_guessed = true;

		game.reset_game();

		assertNotNull(game.user_guesses);
		assertEquals(0, game.user_guesses.size());
		assertEquals("", game.category);
		assertEquals(3, game.words_left_coding);
		assertEquals(3, game.words_left_hardware);
		assertEquals(3, game.words_left_progLang);
		assertFalse(game.coding_guessed);
		assertFalse(game.hardware_guessed);
		assertFalse(game.progLang_guessed);
	}

	@Test
	void valid_guess_01(){
		Game game = new Game();
		boolean valid = game.valid_guess("word");
		assertFalse(valid);
	}

	@Test
	void valid_guess_02(){
		Game game = new Game();
		boolean valid = game.valid_guess("a");
		assertTrue(valid);
	}

	@Test
	void valid_guess_03(){
		Game game = new Game();
		boolean valid = game.valid_guess("1");
		assertFalse(valid);
	}

	@Test
	void valid_guess_04(){
		Game game = new Game();
		boolean valid = game.valid_guess("!");
		assertFalse(valid);
	}

	@Test
	void update_words_left_01(){
		Game game = new Game();
		game.category = "Hardware";
		game.update_words_left();
		assertEquals(3,game.words_left_coding);
		assertEquals(2, game.words_left_hardware);
		assertEquals(3, game.words_left_progLang);
	}

	@Test
	void update_words_left_02(){
		Game game = new Game();
		game.category = "Coding";
		game.words_left_coding = 2;
		game.update_words_left();
		assertEquals(1,game.words_left_coding);
		assertEquals(3, game.words_left_hardware);
		assertEquals(3, game.words_left_progLang);
	}

	@Test
	void update_words_left_03(){
		Game game = new Game();
		game.category = "Programming Languages";
		game.update_words_left();
		assertEquals(3,game.words_left_coding);
		assertEquals(3, game.words_left_hardware);
		assertEquals(2, game.words_left_progLang);
	}

	@Test
	void update_word_guessed_01(){
		Game game = new Game();
		game.category = "Coding";
		game.update_word_guessed();
		assertTrue(game.coding_guessed);
		assertFalse(game.hardware_guessed);
		assertFalse(game.progLang_guessed);
	}

	@Test
	void update_word_guessed_02(){
		Game game = new Game();
		game.category = "Hardware";
		game.update_word_guessed();
		assertFalse(game.coding_guessed);
		assertTrue(game.hardware_guessed);
		assertFalse(game.progLang_guessed);
	}
}
