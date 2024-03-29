import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Server {
    static MulticastSocket socket = null;
    static byte[] buffer = null;
    DatagramSocket receivePacket = null;
    static DatagramPacket packet = null;
    static Scanner scan = null;
    Thread write = new Thread(Server::sendToClient);
    Thread read = new Thread(Server::readFromClient);
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
        } catch (IOException e){
            log("initializeVariable : " + e);
        }
    }
    private static String readFromKeyboard(){
        return scan.nextLine();
    }
    private static void send(String message) {
        try{
            InetAddress ip = InetAddress.getByName(Constants.IP);
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Constants.PORT);
            socket.send(packetSend);
            log("Message Sent");
        } catch (IOException e){
            log("send : " + e);
        }
    }
    private void connecting(){
        write.start();
        log("Started the write thread");
        read.start();
        log("Started the read thread");
    }

    private static void sendToClient(){
        while (true){
            String data = readFromKeyboard();
            send(data);
            buffer = new byte[Constants.BUFFER_SIZE];
        }
    }
    private static String receiveData(){
        String line = "";
        try {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            line = new String(packet.getData(),0, packet.getLength());

        }catch (IOException e){
            log("receiveData : " + e);
        }
        return line;
    }
    private static void readFromClient(){
        String line = receiveData();
        log("Server Received : " + line);
    }
    public static void log(String message){
        System.out.println(message);
    }
}