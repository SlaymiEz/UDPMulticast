import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

import static java.rmi.server.LogStream.log;

public class ClientServer {
    byte[] IP;
    public ClientServer(byte[] IP/**to do : Client type**/){
        this.IP = IP;
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

}
