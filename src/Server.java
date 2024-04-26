import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Server {
    public static MulticastSocket socket;
    DatagramSocket receivePacket = null;
    static Scanner scan = null;
    Thread write = new Thread(Server::sendGlobalMessageFromKeyboard);
    Thread waitforconnection = new Thread(Server::waitforconnection);
    static boolean isRunning;

    public static ArrayList<ClientServer> clientList;


    private static void waitforconnection() {
        while (isRunning) {
            try {
                byte[] buffer = new byte[Common.BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                System.out.println("wait for a connection");
                socket.receive(packet);
                String[] wordList = new String(packet.getData()).split(" ", 1000);
                System.out.println(wordList.length + " " + wordList[0] + " " + Integer.getInteger(wordList[1]));
                if(wordList.length == 2 && wordList[0] == "connexion" && Integer.getInteger(wordList[1]) >= 0){
                    System.out.println("Got a new connexion from : " + packet.getAddress());
                    //clientList.add()
                }
            } catch (IOException e) {
                log("receiveData : " + e);
            }
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.initializeVariable();
        server.connecting();
    }
    private void initializeVariable(){
        isRunning = true;
        try {
            InetAddress group = InetAddress.getByName("230.0.0.1");
            socket = new MulticastSocket(Common.PORT);
            socket.joinGroup(group);
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
        waitforconnection.start();
        log("Started the write thread");
        log("Started the read thread");
    }

    private static void sendGlobalMessageFromKeyboard(){
        while (true){
            String data = readFromKeyboard();
            for(int i = 0; i >= clientList.size(); i++){
                clientList.get(i).sentMessage(data);
            }
        }
    }

    public static void log(String message){
        System.out.println(message);
    }
}