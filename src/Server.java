import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    public static MulticastSocket socket;
    DatagramSocket receivePacket = null;
    static Scanner scan = null;
    Thread write = new Thread(Server::sendToClient);
    Thread read = new Thread(Server::readFromClient);

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
        read.start();
        log("Started the read thread");
    }

    private static void sendToClient(){
        while (true){
            String data = readFromKeyboard();
            //to do impletment sending packet
        }
    }
    private static String receiveData(){
        String line = "";
        try {
            byte[] buffer = new byte[Common.BUFFER_SIZE];
            DatagramPacket packet;
            packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("gg1 " + socket.getInetAddress().toString());
            socket.receive(packet);
            System.out.println("gg2");
            line = new String(packet.getData(),0, packet.getLength());

            if(line.equals("connection")){
                System.out.println("A client connected");
                clientList.add(new ClientServer(packet.getAddress().getAddress()));
            }

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