import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide extends Frame implements  ActionListener, Runnable{
    TextField textField;
    TextArea textArea;
    Button send;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    public ServerSide() {
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
            serverSocket = new ServerSocket(12000);
            socket = serverSocket.accept();

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
        setLayout(null);
        setVisible(true);
        setTitle("Siva");
    }

    @Override
    public void run() {
//        String msg = textField.getText();
//        if(msg.charAt(msg.length() - 1) == '\n'){
//            send.addActionListener(this);
//        }
        while(true){
            try{
                String message = dataInputStream.readUTF();
                textArea.append("Sankar: "+message+"\n");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = textField.getText();
        textArea.append("Siva:"+message+"\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        new ServerSide();
    }
}
