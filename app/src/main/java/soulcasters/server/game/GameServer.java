package soulcasters.server.game;

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class GameServer extends AbstractServer
{
	private JTextArea log;
	private JLabel status;
	private boolean running = false;
	//Uncomment after database has been implemented
	//private Database database;

	public GameServer()
	{
		super(12345);
		this.setTimeout(500);
	}

	public boolean isRunning()
	{
		return running;
	}

	public void setLog(JTextArea log)
	{
		this.log = log;
	}
	public void setStatus(JLabel status)
	{
		this.status = status;
	}
	/*
	void setDatabase(Database database)
	{
		this.database = database;
	}
	*/

	public void serverStarted()
	{
		running = true;
		//Update display once server starts
	}

	public void serverStopped()
	{
		//Update display if server is stopped
	}

	public void serverClosed()
	{
		running = false;
		//Update display once server stops
	}

	public void clientConnected(ConnectionToClient client)
	{
		//Update display once client connects
	}

	// When a message is received from a client, handle it.
	public void handleMessageFromClient(Object arg0, ConnectionToClient arg1)
	{
		/*
		database = new Database();
		// If we received LoginData, verify the account information.
		if (arg0 instanceof LoginData)
		{
			// Check the username and password with the database.
			LoginData data = (LoginData)arg0;
			Object result;
			if (database.verifyAccount(data.getUsername(), data.getPassword()))
			{
				result = "LoginSuccessful";
				log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");
			}
			else
			{
				result = new Error("The username and password are incorrect.", "Login");
				log.append("Client " + arg1.getId() + " failed to log in\n");
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(result);
			}
			catch (IOException e)
			{
				return;
			}
		}

		// If we received CreateAccountData, create a new account.
		else if (arg0 instanceof CreateAccountData)
		{
			// Try to create the account.
			CreateAccountData data = (CreateAccountData)arg0;
			Object result;
			if (database.createNewAccount(data.getUsername(), data.getPassword()))
			{
				result = "CreateAccountSuccessful";
				log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
			}
			else
			{
				result = new Error("The username is already in use.", "CreateAccount");
				log.append("Client " + arg1.getId() + " failed to create a new account\n");
			}

			// Send the result to the client.
			try
			{
				arg1.sendToClient(result);
			}
			catch (IOException e)
			{
				return;
			}
		}
		*/
	}

	// Method that handles listening exceptions by displaying exception information.
	public void listeningException(Throwable exception) 
	{
		running = false;
		status.setText("Exception occurred while listening");
		status.setForeground(Color.RED);
		log.append("Listening exception: " + exception.getMessage() + "\n");
		log.append("Press Listen to restart server\n");
	}
}