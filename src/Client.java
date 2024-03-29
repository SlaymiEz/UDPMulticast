import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

public class Client {
    static MulticastSocket socket = null;
    static byte[] buffer = null;
    static DatagramPacket packet = null;
    InetAddress ip = null;
    Thread reading = new Thread(Client::readFromServer);
    Thread writing = new Thread(Client::sendToServer);
    static Scanner scan = null;
    public static void main(String[] args){
        Client client = new Client();
        client.initializeVariable();
        client.connecting();
    }
    private void initializeVariable(){
        try{
            socket = new MulticastSocket(Constants.PORT);
            ip = InetAddress.getByName(Constants.IP);
            buffer = new byte[Constants.BUFFER_SIZE];
            scan = new Scanner(System.in);
        } catch (IOException e) {
            log("initializeVariable : " + e);
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
    private void joinGroup(){
        try {
            socket.joinGroup(ip);
            log("Client Running...");
        } catch (IOException e) {
            log("joinGroup : " + e);
        }
    }
    private void connecting(){
        joinGroup();
        //reading.start();
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
            InetAddress ip = InetAddress.getByName(Constants.IP);
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Constants.PORT);
            socket.send(packetSend);
            log("Message Sent to servert");
        } catch (IOException e){
            log("send : " + e);
        }
    }
    private static void sendToServer(){
        while (true){
            String data = readFromKeyboard();
            send(data);
            buffer = new byte[Constants.BUFFER_SIZE];
        }
    }
    private static void log(String message){
        System.out.println(message);
    }
}
