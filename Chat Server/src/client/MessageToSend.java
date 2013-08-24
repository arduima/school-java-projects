package client;

import java.io.Serializable;

public class MessageToSend implements Serializable {
	private String name;
	private String message;
	
	public MessageToSend(String name, String message){
		this.name = name;
		this.message = message;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}
	
	
}
