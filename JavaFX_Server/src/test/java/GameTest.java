import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest{

	@Test
	void default_constructor_01(){
		Game defaultGame = new Game();
		assertEquals("", defaultGame.category);
		assertEquals("", defaultGame.get_template());
		assertEquals(3, defaultGame.words_left_coding);
		assertEquals(3, defaultGame.words_left_hardware);
		assertEquals(3, defaultGame.words_left_progLang);
		assertFalse(defaultGame.coding_guessed);
		assertFalse(defaultGame.hardware_guessed);
		assertFalse(defaultGame.progLang_guessed);
	}

	@Test
	void max_guesses_reached_01() {
		Game newGame = new Game();
		newGame.add_wrong_guess("a");
		assertFalse(newGame.max_guess_reached());
	}

	@Test
	void max_guesses_reached_02(){
		Game newGame = new Game();
		newGame.add_wrong_guess("a");
		newGame.add_wrong_guess("b");
		newGame.add_wrong_guess("c");
		newGame.add_wrong_guess("d");
		newGame.add_wrong_guess("e");
		newGame.add_wrong_guess("f");
		assertTrue(newGame.max_guess_reached());
	}

	@Test
	void max_word_guess_reached_01(){
		Game newGame = new Game();
		newGame.words_left_coding = 2;
		newGame.words_left_hardware = 3;
		newGame.words_left_progLang = 3;
		assertFalse(newGame.max_word_guess_reached());
	}

	@Test
	void max_word_guess_reached_02(){
		Game newGame = new Game();
		newGame.words_left_coding = 3;
		newGame.words_left_hardware = 0;
		newGame.words_left_progLang = 3;
		assertTrue(newGame.max_word_guess_reached());
	}

	@Test
	void subtract_words_left_01(){
		Game newGame = new Game();
		newGame.category = "coding";
		newGame.subtract_words_left();
		assertEquals(2, newGame.words_left_coding);
		assertEquals(3, newGame.words_left_hardware);
		assertEquals(3, newGame.words_left_progLang);
	}

	@Test
	void subtract_words_left_02(){
		Game newGame = new Game();
		newGame.category = "hardware";
		newGame.subtract_words_left();
		assertEquals(3, newGame.words_left_coding);
		assertEquals(2, newGame.words_left_hardware);
		assertEquals(3, newGame.words_left_progLang);
	}

}
