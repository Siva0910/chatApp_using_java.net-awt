import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Frame implements  ActionListener, Runnable{
    TextField textField;
    TextArea textArea;
    Button send;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    public Client() {
        //super();
        //Frame frame = new Frame("chat app");
        textField = new TextField("");
        textArea = new TextArea("");
        send = new Button("Send");

        //loyouting the components
        textArea.setBounds(100,50,380,280);
        textField.setBounds(160,340,200,30);
        send.setBounds(210,380,80,27);

        try{

            socket = new Socket("localhost",12000);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        }catch (Exception e){
            e.printStackTrace();
        }

        send.addActionListener(this);
        add(textField);
        add(textArea);
        add(send);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setSize(600,500);
        setTitle("Sankar");
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void run() {
        try{
            while(true){
                String message = dataInputStream.readUTF();
                textArea.append("Siva: "+message+"\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = textField.getText();
        textArea.append("Sankar:"+message+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
