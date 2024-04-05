import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static MulticastSocket socket;
    DatagramSocket receivePacket = null;
    static Scanner scan = null;
    Thread write = new Thread(Server::sendToClient);

    public static ArrayList<ClientServer> clientList;

    public static void main(String[] args){
        Server server = new Server();
        server.initializeVariable();
        server.connecting();
    }
    private void initializeVariable(){
        try {
            socket = new MulticastSocket(Common.PORT);
            scan = new Scanner(System.in);
            log("Server Running...");
        } catch (IOException e){
            log("initializeVariable : " + e);
        }
    }
    private static String readFromKeyboard(){
        return scan.nextLine();
    }
    private void connecting(){
        write.start();
        log("Started the write thread");
        log("Started the read thread");
    }

    private static void sendToClient(){
        while (true){
            String data = readFromKeyboard();
            //to do impletment sending packet
        }
    }
    private static String receiveData(){
        return "44";
    }
    private static void readFromClient(){
        String line = receiveData();
        log("Server Received : " + line);
    }
    public static void log(String message){
        System.out.println(message);
    }
}