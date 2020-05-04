import java.io.Serializable;
import java.util.Arrays;

public class Sender implements Serializable {

    public String send, nick;

    public Sender() {

        this.send = "send";
        this.nick = "nick";
    }
    public Sender(String nick, String send) {

        this.send = send;
        this.nick = nick;
    }

    public  Sender (String send1){
        String[] comand1 =  send1.split(" ");
        this.nick=comand1[0];
        this.send=comand1[1] = String.join(" ", Arrays.copyOfRange(comand1,1, comand1.length));
    }

    public String getSend() {
        return send;
    }

    public String getNick() {
        return nick;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String soutText() {
        return this.nick + " " + this.send;
    }
}

