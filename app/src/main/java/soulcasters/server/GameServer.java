package soulcasters.server;

import java.awt.*;
import javax.swing.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import soulcasters.server.game.GameController;
import soulcasters.server.game.PlayerData;
import soulcasters.shared.*;

public class GameServer extends AbstractServer {
  // Data fields for this chat server.
  private JTextArea log;
  private JLabel status;
  private boolean running = false;
  private Database database = new Database();

  private ArrayList<GameController> concurrentGames;
  private ArrayList<SessionToken> sessionTokens;

  // Constructor for initializing the server with default settings.
  public GameServer() {
    super(12345);
    this.setTimeout(500);

    concurrentGames = new ArrayList<>();
    sessionTokens = new ArrayList<>();
  }

  /**
   * Generates a new session token and associates it with the player's ID.
   * 
   * @param playerId The ID of the player.
   * @return The new session token.
   */
  public long createSessionToken(long playerId, GameController game) {
    long token = ThreadLocalRandom.current().nextLong();
    sessionTokens.add(new SessionToken(playerId, token, game));
    return token;
  }

  public void setDatabase(Database database) {
    this.database = database;
  }

  // Getter that returns whether the server is currently running.
  public boolean isRunning() {
    return running;
  }

  // Setters for the data fields corresponding to the GUI elements.
  public void setLog(JTextArea log) {
    this.log = log;
  }

  public void setStatus(JLabel status) {
    this.status = status;
  }

  // When the server starts, update the GUI.
  public void serverStarted() {
    running = true;
    status.setText("Listening");
    status.setForeground(Color.GREEN);
    log.append("Server started\n");
  }

  // When the server stops listening, update the GUI.
  public void serverStopped() {
    status.setText("Stopped");
    status.setForeground(Color.RED);
    log.append("Server stopped accepting new clients - press Listen to start accepting new clients\n");
  }

  // When the server closes completely, update the GUI.
  public void serverClosed() {
    running = false;
    status.setText("Close");
    status.setForeground(Color.RED);
    log.append("Server and all current clients are closed - press Listen to restart\n");
  }

  // When a client connects or disconnects, display a message in the log.
  public void clientConnected(ConnectionToClient client) {
    log.append("Client " + client.getId() + " connected\n");
  }

  // When a message is received from a client, handle it.
  public void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {

    if (arg0 instanceof DataRequest) {
      DataRequest dataReq = (DataRequest) arg0;
      for (SessionToken sessionToken : sessionTokens) {
        // Validate client ID and the sent session token
        if (sessionToken.networkId == arg1.getId() && sessionToken.sessionToken == dataReq.sessionToken) {

          // Check request type, retrieve available data for client
          if (dataReq.request.equals("reqEntityData")) {
            CombinedEntityData ced = sessionToken.game.retrieveEntityData(arg1.getId());
            try {
              arg1.sendToClient(ced);
            } catch (IOException e) {
              e.printStackTrace();
            }
            return;
          }
        }
      }
    }

    if (arg0 instanceof SelectedOptionData) {
      SelectedOptionData selectedOptionData = (SelectedOptionData) arg0;
      for (SessionToken sessionToken : sessionTokens) {
        // Validate client ID and the sent session token
        if (sessionToken.networkId == arg1.getId() && sessionToken.sessionToken == selectedOptionData.sessionToken) {
          sessionToken.game.recieveSelectedOption(selectedOptionData.entityId, selectedOptionData.selectedOption);
        }
      }
    }

    // If we received LoginData, verify the account information and send session token if successful.
    if (arg0 instanceof LoginData) {
      Long newToken = null;
      // Check the username and password with the database.
      LoginData data = (LoginData) arg0;
      if (database.verifyAccount(data.getUsername(), data.getPassword())) {
        log.append("Client " + arg1.getId() + " successfully logged in as " + data.getUsername() + "\n");

        boolean playerAdded = false;

        for (GameController game : concurrentGames) {
          if (game.getPlayerCount() == 1) {
            game.addPlayer(arg1.getId(), data.getUsername());
            playerAdded = true;
            newToken = createSessionToken(arg1.getId(), game);
          }
        }

        if (!playerAdded) {
          GameController newGame = new GameController(this);
          concurrentGames.add(newGame);
          newGame.addPlayer(arg1.getId(), data.getUsername());
          newGame.start();
          newToken = createSessionToken(arg1.getId(), newGame);
        }
      } else {
        log.append("Client " + arg1.getId() + " failed to log in\n");
      }

      // Send Session Token to client
      try {
        if (newToken != null) {
          arg1.sendToClient(new ClientToken(newToken));
          log.append("Client " + arg1.getId() + " given token: " + newToken);
        }
        else {
          throw new Exception();
        }
      } catch (Exception e) {
        log.append("Could not create session token for Client " + arg1.getId() + "\n");
        e.printStackTrace();
      }
    }

    // If we received CreateAccountData, create a new account.
    else if (arg0 instanceof CreateAccountData) {
      // Try to create the account.
      CreateAccountData data = (CreateAccountData) arg0;
      Object result;
      if (database.createNewAccount(data.getUsername(), data.getPassword())) {
        result = "CreateAccountSuccessful";
        log.append("Client " + arg1.getId() + " created a new account called " + data.getUsername() + "\n");
      } else {
        result = new GameDataError("The username is already in use.", "CreateAccount");
        log.append("Client " + arg1.getId() + " failed to create a new account\n");
      }

      // Send the result to the client.
      try {
        arg1.sendToClient(result);
      } catch (IOException e) {
        return;
      }
    }
  }

  // Method that handles listening exceptions by displaying exception information.
  public void listeningException(Throwable exception) {
    running = false;
    status.setText("Exception occurred while listening");
    status.setForeground(Color.RED);
    log.append("Listening exception: " + exception.getMessage() + "\n");
    log.append("Press Listen to restart server\n");
  }
}
