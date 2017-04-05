import java.io.IOException;
import java.net.*;

/*
    The class implements the Runnable interface.
    This means we must define a run method.
    It can be later used to be run as part of a thread
*/
public class MulticastReceiver implements Runnable
{
    public boolean keepListening = true;
    private int multicastSocketPort;
    private InetAddress hostAddress;

    // Constructor
    public MulticastReceiver(int port, String hostAddress)
    {
        this.multicastSocketPort = port;
        try {
            this.hostAddress = InetAddress.getByName(hostAddress);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // Definition of abstract run method
    public void run()
    {
        this.listen();
    }

    // Listening for incoming messages
    public void listen()
    {
        DatagramPacket inPacket = null;
        byte [] inBuf = new byte[256];
        String msg = "";
        try {
            MulticastSocket socket = getSocketConnection();
            while (this.keepListening) {
                inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);
                msg = new String(inBuf, 0, inPacket.getLength());
                System.out.println(msg);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MulticastSocket getSocketConnection() throws IOException
    {
        MulticastSocket socket = new MulticastSocket(this.multicastSocketPort);
        socket.joinGroup(this.hostAddress);
        return socket;
    }
}
