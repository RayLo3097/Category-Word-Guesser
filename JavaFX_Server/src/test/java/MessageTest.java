import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    @Test
    void default_constructor_01(){
        Message msg = new Message();
        assertEquals("", msg.action);
        assertEquals("", msg.data);
        assertEquals(0, msg.data_size);
    }

    @Test
    void parameter_1_constructor_01(){
        Message msg = new Message("guess");
        assertEquals("guess", msg.action);
        assertEquals("", msg.data);
        assertEquals(0, msg.data_size);
    }

    @Test
    void parameter_2_constructor_01(){
        Message msg = new Message("guess", "a");
        assertEquals("guess", msg.action);
        assertEquals("a", msg.data);
        assertEquals(0, msg.data_size);
    }

    @Test
    void parameter_3_constructor_01(){
        Message msg = new Message("wrong guess", "a, b, c, d", 4);
        assertEquals("wrong guess", msg.action);
        assertEquals("a, b, c, d", msg.data);
        assertEquals(4, msg.data_size);
    }
}
