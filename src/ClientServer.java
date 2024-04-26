import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientServer {
    InetAddress IP;
    MulticastSocket socket;
    Thread readThread;
    Server serverInstance;

    List<byte[]> data = new ArrayList<>();
    public ClientServer(InetAddress IP, MulticastSocket socket, Server server    /**to do : Client type**/){
        this.IP = IP;
        readThread = new Thread(readThread);
    }

    public void sentMessage(String message){
            byte[] buffer;
        try{
            InetAddress ip = IP;
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Common.PORT);
            Server.socket.send(packetSend);
            System.out.println("Message Sent");
        } catch (IOException e){
            System.out.println("send : " + e);
        }
    }

    public void listenThread(){
        try {
            String line = "";
            byte[] buffer = new byte[Common.BUFFER_SIZE];
            DatagramPacket packet;
            packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("caca1 " + socket.getInetAddress().toString());
            socket.receive(packet);
            System.out.println("caca2");
            line = new String(packet.getData(),0, packet.getLength());

             data.add(1, line.getBytes());
        }catch (IOException e){
            System.out.println("receiveData : " + e);
        }
    }

}
