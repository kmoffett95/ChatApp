import java.io.IOException;
import java.net.*;
import java.util.Scanner;
/*
    The class is used to send messages into a multicast group
*/
public class MulticastSender
{
    private int multicastSocketPort;
    private InetAddress hostAddress;

    // Constructor
    public MulticastSender(int port, String hostAddress)
    {
        this.multicastSocketPort = port;
        try {
            this.hostAddress = InetAddress.getByName(hostAddress);
        }
        catch (UnknownHostException e) {
        	e.printStackTrace();
            this.hostAddress = null;
        }
    }

    // Method to send messges into group
    public void transmitMessage(String message)
    {
        byte [] outBuf = message.getBytes();
        try {
	        DatagramSocket socket = new DatagramSocket();
    	    DatagramPacket outPacket = new DatagramPacket(outBuf, outBuf.length, this.hostAddress, this.multicastSocketPort);
        	socket.send(outPacket);
        }
        catch (IOException e) {
        	e.printStackTrace();
        }
    }
}
