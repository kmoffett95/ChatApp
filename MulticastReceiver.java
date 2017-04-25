import java.io.IOException;
import java.net.*;
import javax.swing.JTextArea;
/*
    The class implements the Runnable interface.
    This means we must define a run method.
    It can be later used to be run as part of a thread
*/
public class MulticastReceiver implements Runnable
{
    private int multicastSocketPort;
    private InetAddress hostAddress;
	private JTextArea textArea;

    // Constructor
    public MulticastReceiver(int port, String hostAddress, JTextArea textArea)
    {
        this.multicastSocketPort = port;
        this.textArea = textArea;
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
            while (true) {
                inPacket = new DatagramPacket(inBuf, inBuf.length);
                socket.receive(inPacket);
                msg = new String(inBuf, 0, inPacket.getLength());
                this.textArea.append(msg + "\n");
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
