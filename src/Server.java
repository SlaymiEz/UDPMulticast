import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Server {
    MulticastSocket socket = null;
    byte[] buffer = null;
    DatagramSocket receivePacket = null;
    Scanner scan = null;
    public static void main(String[] args){
        Server server = new Server();
        server.initializeVariable();
        server.connecting();
    }
    private void initializeVariable(){
        try {
            socket = new MulticastSocket();
            buffer = new byte[Constants.BUFFER_SIZE];
            scan = new Scanner(System.in);
            log("Server Running...");
        } catch (SocketException e){
            log("initializeVariable : " + e.toString());
        }
        catch (IOException e){
            log("initializeVariable : " + e.toString());
        }
    }
    private String readFromKeyboard(){
        String line = scan.nextLine();
        return line;
    }
    private void send(String message) {
        try{
            InetAddress ip = InetAddress.getByName(Constants.IP);
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Constants.PORT);
            socket.send(packetSend);
            log("Message Sent");
        } catch (IOException e){
            log("send : " + e.toString());
        }
    }
    private void connecting(){
        while (true){
            String data = readFromKeyboard();
            send(data);
            buffer = new byte[Constants.BUFFER_SIZE];
        }
    }
    public void log(String message){
        System.out.println(message);
    }
}