import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Client {
    static MulticastSocket socket = null;
    static DatagramPacket packet = null;
    InetAddress ip = null;
    Thread reading = new Thread(Client::readFromServer);
    Thread writing = new Thread(Client::sendToServer);
    static Scanner scan = null;

    public static String IPbyInt;
    public static void main(String[] args){
        if(args.length == 1){
            IPbyInt = args[0];
        } else {
            System.out.println("You need to put the server's ip address into the program argument");
            System.exit(-1);
        }

        Client client = new Client();
        client.initializeVariable();
        client.connecting();

    }
    private void initializeVariable(){
        try{
            socket = new MulticastSocket(Common.PORT);
            ip = InetAddress.getByName(Common.IP);
            scan = new Scanner(System.in);

        } catch (IOException e) {
            log("initializeVariable : " + e);
        }
    }
    private static String receiveData(){
        String line = "";
        try {
            byte[] buffer = new byte[Common.BUFFER_SIZE];
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            line = new String(packet.getData(),0, packet.getLength());

        }catch (IOException e){
            log("receiveData : " + e);
        }
        return line;
    }
    private void joinGroup(){
        try {
            socket.joinGroup(ip);
            log("Client Running...");
            send("connection");
        } catch (IOException e) {
            log("joinGroup : " + e);
        }
    }
    private void connecting(){
        joinGroup();
        reading.start();
        writing.start();
    }

    private static void readFromServer(){
        while (true){
            String line = receiveData();
            log("Client Received : " + line);
        }
    }
    private static String readFromKeyboard(){
        return scan.nextLine();
    }
    private static void send(String message) {
        try{
            byte[] buffer;
            InetAddress ip = InetAddress.getByName(IPbyInt);
            buffer = message.getBytes();
            System.out.println(IPbyInt + " " + ip);
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Common.PORT);
            socket.send(packetSend);
            log("Message Sent to server");
        } catch (IOException e){
            log("send : " + e);
        }
    }
    private static void sendToServer(){
        while (true){
            String data = readFromKeyboard();
            send(data);
        }
    }
    private static void log(String message){
        System.out.println(message);
    }
}
