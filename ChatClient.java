import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient
{
	public String name;
	public JFrame frame;
    private MulticastReceiver receiver;
    private MulticastSender transmitter;
    private JTextArea textArea;

    public ChatClient(int port, String hostAddress, String name)
    {
    	this.textArea = new JTextArea(20, 20);
        this.receiver = new MulticastReceiver(port, hostAddress, this.textArea);
        this.transmitter = new MulticastSender(port, hostAddress);
        this.name = name;
        setUpFrame();
    }

    private void setUpFrame()
    {
    	this.frame = new JFrame("JFrame Example");
    	final JTextField textField = new JTextField(20);
		this.textArea.setEditable(false);

		addListenerToTextField(textField);
		JPanel panel = getPanel(textField);
		this.frame.add(panel);

		addSizeAndLocationOfFrame();
    }

    private void addListenerToTextField(final JTextField textField)
    {
    	textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transmitter.transmitMessage(name + ": " + textField.getText());
				textField.setText("");
			}
		});
    }

    private JPanel getPanel(final JTextField textField)
    {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Logged in as " + this.name + ". Enter message: ");
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(textField);
		panel.add(this.textArea);
		return panel;
    }

    private void addSizeAndLocationOfFrame()
    {
		this.frame.setSize(300, 300);
		this.frame.setLocationRelativeTo(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

	/*
		To run the main method, a name is provided as an argument
		e.g. java ChatClient YourName
		A GUI is displayed displaying the received message.
		The program terminates when the GUI is closed.
	*/
    public static void main(String [] args)
    {
		String name = args.length == 0 ? "Default Name" : args[0];
    	ChatClient client = new ChatClient(8888, "224.2.2.3", name);

	    Thread receiverThread = new Thread(client.receiver);
        receiverThread.start();

		client.frame.setVisible(true);
    }
}
