import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Client {
    MulticastSocket socket = null;
    byte[] buffer = null;
    DatagramPacket packet = null;
    InetAddress ip = null;
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
        } catch (SocketException e) {
            log("initializeVariable : " + e.toString());
        } catch (IOException e) {
            log("initializeVariable : " + e.toString());
        }
    }
    private String receiveData(){
        String line = "";
        try {
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            line = new String(packet.getData(),0, packet.getLength());

        }catch (IOException e){
            log("receiveData : " + e.toString());
        }
        return line;
    }
    private void joinGroup(){
        try {
            socket.joinGroup(ip);
            log("Client Running...");
        } catch (IOException e) {
            log("joinGroup : " + e.toString());
        }
    }
    private void connecting(){
        joinGroup();
        while (true){
            String line = receiveData();
            log("Client Received : " + line);
        }
    }
    private void log(String message){
        System.out.println(message);
    }
}
