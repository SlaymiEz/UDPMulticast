import java.io.IOException;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.rmi.server.LogStream.log;

public class ClientServer {
    InetAddress IP;
    MulticastSocket socket;
    Thread readThread;

    List<byte[]> data = new ArrayList<>();
    public ClientServer(InetAddress IP, MulticastSocket socket    /**to do : Client type**/){
        this.IP = IP;
        readThread = new Thread(readThread);
    }

    public void sentMessage(String message){
            byte[] buffer;
        try{
            InetAddress ip = InetAddress.getByName(Common.IP);
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Common.PORT);
            Server.socket.send(packetSend);
            log("Message Sent");
        } catch (IOException e){
            log("send : " + e);
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
            log("receiveData : " + e);
        }
    }

}
