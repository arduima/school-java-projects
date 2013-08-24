package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Client {

	// Text field for name and message
	private static JTextField jtf_Name = new JTextField(15);
	private static JLabel jlbl_serverStatus = new JLabel("Server OFFLINE");
	private static JTextArea jta_Message = new JTextArea(3, 27);

	// Text area to display contents
	private static JTextArea jta_Convo = new JTextArea();

	// Send button
	private static JButton jbt_Send = new JButton("Send");

	// IO streams
	// private static MessageToSend messageObject;
	private static ObjectOutputStream toServer;

	// Socket number
	private static int SOCKET_NUMBER = 7000;

	public static void main(String[] args) {
		// Start program
		login("Dimitri");
	}
	
	public Client(String name){
		this.login(name);
	}
	public static void login(String name) {
		// Setup GUI
		CustomFrameClient myFrame = new CustomFrameClient(jtf_Name,
				jta_Message, jta_Convo, jbt_Send, jlbl_serverStatus);
		myFrame.setTitle("Client");
		myFrame.setSize(480, 300);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true); // It is necessary to show the frame here!
		
		jtf_Name.setText(name);
		//jtf_Name.setEditable(false);
		
		Socket socket = null;
		while (socket == null) {
			try {
				// Create a socket to connect to the server
				socket = new Socket("localhost", SOCKET_NUMBER);
				jlbl_serverStatus.setForeground(Color.green);
				jlbl_serverStatus.setText("Server ONLINE");
				jbt_Send.setEnabled(true);

				// Socket socket = new Socket("130.254.204.36",
				// SOCKET_NUMBER);
				// Socket socket = new Socket("drake.Armstrong.edu",
				// SOCKET_NUMBER);

				// Create an output stream to send data to the server
				toServer = new ObjectOutputStream(socket.getOutputStream());
				toServer.flush();

				ListenForMessages getMessages = new ListenForMessages(socket);
				new Thread(getMessages).start();

				jbt_Send.addActionListener(new ButtonListener(toServer));
			} catch (IOException ex) {
				System.err.println("CLIENT ERROR Connecting to Socket");
				socket = null;
				jlbl_serverStatus.setForeground(Color.red);
				jlbl_serverStatus.setText("Server OFFLINE");
				jbt_Send.setEnabled(false);
			}
		}
	}

	public static JTextField getJtf_Name() {
		return jtf_Name;
	}

	public static JTextArea getJta_Message() {
		return jta_Message;
	}

	public static JTextArea getJta_Convo() {
		return jta_Convo;
	}

	public static ObjectOutputStream getToServer() {
		return toServer;
	}

	public static JLabel getJlbl_serverStatus() {
		return jlbl_serverStatus;
	}

	public static JButton getJbt_Send() {
		return jbt_Send;
	}
}
class ListenForMessages implements Runnable {
	// Socket to server
	public Socket socket;

	ListenForMessages(Socket socket) {
		this.socket = socket; 
	}

	@Override
	public void run() {
		// Create an input stream to receive data from the server
		ObjectInputStream fromServer = null;
		try {
			fromServer = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("CLIENT ERROR Getting Server Stream");
			// Client.getJlbl_serverStatus().setForeground(Color.red);
			// Client.getJlbl_serverStatus().setText("Server OFFLINE");
			// Client.getJbt_Send().setEnabled(false);
		}
		// Continuously serve the client
		while (true) {
			MessageToSend messageObject;
			try {
				messageObject = (MessageToSend) fromServer.readObject();

				// Display incoming messages
				Client.getJta_Convo().append(
						messageObject.getName() + ": "
								+ messageObject.getMessage() + '\n');

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

// Frame Class
class CustomFrameClient extends JFrame {
	// Text field for name and message
	private JTextField jtf_Name;
	private JLabel jlbl_serverStatus;
	private JTextArea jta_Message;

	// Text area to display contents
	private JTextArea jta_Convo;

	// Send button
	private JButton jbt_Send = new JButton("Send");

	CustomFrameClient(JTextField jtf_Name, JTextArea jta_Message,
			JTextArea jta_Convo, JButton jbt_Send, JLabel jlbl_serverStatus) {
		// Get parameters
		this.jtf_Name = jtf_Name;
		this.jta_Message = jta_Message;
		this.jlbl_serverStatus = jlbl_serverStatus;
		this.jta_Convo = jta_Convo;
		this.jbt_Send = jbt_Send;

		this.setupGUI();
	}

	private void setupGUI() {
		// Create layout
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		// Name panel
		JPanel jpl_Name = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpl_Name.add(new JLabel("Your Name: "));
		jpl_Name.add(jtf_Name);
		jpl_Name.add(jlbl_serverStatus);
		jlbl_serverStatus.setAlignmentX(RIGHT_ALIGNMENT);
		jlbl_serverStatus.setForeground(Color.red);

		// Message Panel
		JPanel jpl_Message = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jbt_Send.setEnabled(false);
		jpl_Message.add(new JLabel("Message: "));
		jta_Message.setLineWrap(true);
		jpl_Message.add(jta_Message);
		jpl_Message.add(jbt_Send);

		// Add all the components to Frame
		this.add(jpl_Name, BorderLayout.NORTH);
		this.add(new JScrollPane(jta_Convo), BorderLayout.CENTER);
		jta_Convo.setEditable(false);
		this.add(jpl_Message, BorderLayout.SOUTH);
	}
}

// Button Listener
class ButtonListener implements ActionListener {
	boolean firstTime = true;

	// IO streams
	private static MessageToSend outgoingMessage;

	private ObjectOutputStream toServer;

	ButtonListener(ObjectOutputStream toServer) {
		this.toServer = toServer;
	}

	public void actionPerformed(ActionEvent e) {
		try {
			outgoingMessage = new MessageToSend(Client.getJtf_Name().getText(),
					Client.getJta_Message().getText());
			Client.getJtf_Name().setEditable(false);
			// Display to the text area
			Client.getJta_Convo().append(
					outgoingMessage.getName() + ": "
							+ outgoingMessage.getMessage() + '\n');
			Client.getJta_Message().setText("");
			
			toServer.writeObject(outgoingMessage);
			toServer.flush();

		} catch (IOException ex) {
			System.err.println("CLIENT ERROR Sending Object");
		}
	}
}
