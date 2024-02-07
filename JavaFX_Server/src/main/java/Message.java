import java.io.Serializable;

public class Message implements Serializable {
    String action; //what the client did
    String data; //what the client sent
    int data_size;

    /**
     * Default constructor for Message
     */
    public Message(){
        this.action = "";
        this.data = "";
        this.data_size = 0;
    }

    /**
     * Parameterized constructor for Message
     * @param action the action to send
     */
    public Message(String action){
        this.action = action;
        this.data = "";
        this.data_size = 0;
    }

    /**
     * Parameterized constructor for Message
     * @param action the action to send
     * @param data the data associated with the action
     */
    public Message(String action, String data){
        this.action = action;
        this.data = data;
        this.data_size = 0;
    }

    /**
     * Parameterized constructor for Message
     * @param action the action to send
     * @param data the data associated with the action
     * @param data_size optional size of data to send
     */
    public Message(String action, String data, int data_size){
        this.action = action;
        this.data = data;
        this.data_size = data_size;
    }

    /**
     * Set message action, data, and data size
     * @param action the action to send
     * @param data the data associated with the action
     * @param data_size optional size of data to send
     */
    public void set_message(String action, String data, int data_size){
        this.action = action;
        this.data = data;
        this.data_size = data_size;
    }
    public String get_data_size(){
        return Integer.toString(this.data_size);
    }
}