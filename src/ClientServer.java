import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import static java.rmi.server.LogStream.log;

public class ClientServer {
    int IP;
    public ClientServer(int IP/**to do : Client type**/){
        this.IP = IP;
    }

    public void sentMessage(String message){
            byte[] buffer;
        try{
            InetAddress ip = InetAddress.getByName(Constants.IP);
            buffer = message.getBytes();
            DatagramPacket packetSend = new DatagramPacket(buffer, buffer.length, ip, Constants.PORT);
            Server.socket.send(packetSend);
            log("Message Sent");
        } catch (IOException e){
            log("send : " + e);
        }
    }

}
