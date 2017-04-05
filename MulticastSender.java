import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/*
    The class implements the Runnable interface.
    This means we must define a run method.
    It can be later used to be run as part of a thread
*/
public class MulticastSender implements Runnable
{
    private String terminalString = "goodbye";  // The program will terminate if your enter this string
    private String name = "";
    private int multicastSocketPort;
    private InetAddress hostAddress;

    // Constructor
    public MulticastSender(int port, String hostAddress, String name)
    {
        this.multicastSocketPort = port;
        this.name = name;
        try {
            this.hostAddress = InetAddress.getByName(hostAddress);
        }
        catch (UnknownHostException e) {
            System.out.println(e);
        }
    }

    // Definition of abstract run method
    public void run()
    {
        Scanner sc = new Scanner(System.in);
        String message = "";
        while (!message.trim().equals(this.terminalString)) {
            try {
                message = sc.nextLine();
                this.transmitMessage(this.name + ": " + message);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Method to send messges into group
    public void transmitMessage(String message) throws IOException, SocketException
    {
        byte [] outBuf = message.getBytes();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket outPacket = new DatagramPacket(outBuf, outBuf.length, this.hostAddress, this.multicastSocketPort);
        socket.send(outPacket);
    }
}
