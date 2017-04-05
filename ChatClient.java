public class ChatClient
{
    public MulticastReceiver receiver;
    public MulticastSender transmitter;

    public ChatClient(int port, String hostAddress, String name)
    {
        this.receiver = new MulticastReceiver(port, hostAddress);
        this.transmitter = new MulticastSender(port, hostAddress, name);
    }

    /*
        The main method reads in 3 arguments from the command line.
        1st arg: port number, 2nd arg: host address, 3rd arg: name to identify by in group chat
        eg. java ChatClient 8888 224.2.2.3 name1
        The program terminates when the terminal string in entered (currently 'goodbye')
    */
    public static void main(String [] args)
    {
        ChatClient client = new ChatClient(Integer.parseInt(args[0]), args[1], args[2]);
        Thread receiverThread = new Thread(client.receiver);
        Thread transmitterThread = new Thread(client.transmitter);
        receiverThread.start();
        transmitterThread.start();

        while (transmitterThread.isAlive()) {}
        client.receiver.keepListening = false;
    }
}
