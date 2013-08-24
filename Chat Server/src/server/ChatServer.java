package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.MessageToSend;

class ChatServer extends JFrame {
	// GUI component
	private static JTextArea jta_Status = new JTextArea();

	public static JTextArea getJta_Status() {
		return jta_Status;
	}

	public static void main(String[] args) {
		// Create a frame
		CustomFrameServer myFrame = new CustomFrameServer(jta_Status);

		// Set frame parameters
		myFrame.setTitle("Chat Room Server");
		myFrame.setSize(500, 300);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true); // It is necessary to show the frame here!
	}
}

class CustomFrameServer extends JFrame {
	// GUI components
	private JTextArea jta_Status;
	private JButton jbtn_Start, jbtn_Disconnect;

	int SOCKET_NUMBER = 7000;
	private Thread connectionThread;
	private ServerSocket serverSocket;

	CustomFrameServer(JTextArea jta_Status) {
		this.jta_Status = jta_Status;
		setupGUI();
	}

	private void setupGUI() {
		// Create layout
		this.setLayout(new BorderLayout());

		// Add text area
		jta_Status.setEditable(false);
		this.add(new JScrollPane(jta_Status), BorderLayout.CENTER);

		// New panel for buttons
		JPanel btnPanel = new JPanel();

		// Start Server Button
		jbtn_Start = new JButton("Start Server");
		jbtn_Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a server socket
				try {
					serverSocket = new ServerSocket(SOCKET_NUMBER);
					EstablishConnection startConnection = new EstablishConnection(
							serverSocket, jta_Status);
					connectionThread = new Thread(startConnection);
					connectionThread.start();

					// Disable start button and enable disconnect
					((JButton) e.getSource()).setEnabled(false);

					jta_Status.append(new Date() + ":\tSERVER STARTED\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.err.println("SERVER ERROR Opening Socket");
				}

			}
		});
		// Add the buttons
		btnPanel.add(jbtn_Start);
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}

class EstablishConnection implements Runnable {

	ServerSocket serverSocket;
	JTextArea jta_Status;
	static ArrayList<HandleAClient> arrayClients;

	EstablishConnection(ServerSocket serverSocket, JTextArea jta_Status) {
		// Server Socket and Text Area
		this.serverSocket = serverSocket;
		this.jta_Status = jta_Status;

		arrayClients = new ArrayList<HandleAClient>();
	}

	public static void repeatMessage(MessageToSend messageReceivde, Socket socket) {
		for (int n = 0; n < arrayClients.size(); n++) {
			if (arrayClients.get(n).getSocket() != socket)
				arrayClients.get(n).sendMultiChat(messageReceivde);
		}
	}

	// ---------- CONNECTIONS TO SERVER -----------
	@Override
	public void run() {
		try {
			// Number a client
			int clientNo = 1;
			// Run forever while connected

			while (true) {
				// Create a socket for a new person
				Socket socket = serverSocket.accept();

				// IP of client
				InetAddress inetAddress = socket.getInetAddress();

				// Display the client number
				jta_Status.append(new Date() + ":\tCLIENT " + clientNo
						+ " JOINED, IP " + inetAddress.getHostAddress() + '\n');

				// Create a new thread for the connection
				HandleAClient task = new HandleAClient(socket, clientNo);
				arrayClients.add(task);

				// Start the new thread
				new Thread(task).start();

				// Increment clientNo
				clientNo++;

			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			System.err
					.println("SERVER ERROR Waiting for Connection(Server Socket Closed)");
		}

	}
}

// Define the thread class for handling new connection
class HandleAClient implements Runnable {
	private Socket socket; // A connected socket

	public Socket getSocket() {
		return socket;
	}

	int clientNo;
	// IO streams
	private MessageToSend messageObject;
	// Create data input and output streams
	ObjectInputStream inputFromClient;
	ObjectOutputStream outputToClient;

	/** Construct a thread */
	public HandleAClient(Socket socket, int clientNo) {
		this.socket = socket;
		this.clientNo = clientNo;
	}

	public void sendMultiChat(MessageToSend messageReceivde) {
		try {
			outputToClient.writeObject(messageReceivde);
			outputToClient.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("SERVER ERROR: Repeat Message");
		}
	}

	/** Run a thread */
	public void run() {
		try {
			outputToClient = new ObjectOutputStream(socket.getOutputStream());
			inputFromClient = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("SERVER ERROR Stream");
		}
		// Continuously serve the client
		while (true) {
			try {
				messageObject = (MessageToSend) inputFromClient.readObject();
				ChatServer.getJta_Status().append(
						messageObject.getName() + ": "
								+ messageObject.getMessage() + '\n');

				EstablishConnection.repeatMessage(messageObject, socket);
			} catch (IOException e) {
				// IP of client
				InetAddress inetAddress = socket.getInetAddress();
				ChatServer.getJta_Status().append(
						new Date() + ":\tCLIENT " + clientNo + " LEFT, IP "
								+ inetAddress.getHostAddress() + '\n');
				break;
			} catch (ClassNotFoundException e) {
				System.err.println("SERVER ERROR Class Not Found");
			}
		}
	}
}
