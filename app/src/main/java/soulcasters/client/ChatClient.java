package soulcasters.client;

import ocsf.client.AbstractClient;
import soulcasters.shared.ClientToken;
import soulcasters.shared.CombinedEntityData;
import soulcasters.shared.GameDataError;

public class ChatClient extends AbstractClient
{
  // Private data fields for storing the GUI controllers.
  private LoginControl loginControl;
  private CreateAccountControl createAccountControl;
  private GamePanelControl gamePanelControl;

  public boolean inGame;

  // Setters for the GUI controllers.
  public void setLoginControl(LoginControl loginControl)
  {
    this.loginControl = loginControl;
  }
  public void setCreateAccountControl(CreateAccountControl createAccountControl)
  {
    this.createAccountControl = createAccountControl;
  }
  public void setGamePanelControl(GamePanelControl gamePanelControl) {
    this.gamePanelControl = gamePanelControl;
  }

  // Constructor for initializing the client with default settings.
  public ChatClient()
  {
    super("localhost", 8300);
    inGame = false;
    
  }
  
  // Method that handles messages from the server.
  public void handleMessageFromServer(Object arg0)
  {

    if (arg0 instanceof CombinedEntityData) {
      CombinedEntityData combinedEntityData = (CombinedEntityData) arg0;
      System.out.println(combinedEntityData.visibleEntities.size());
      
      gamePanelControl.recieveCombinedEntityData(combinedEntityData);
    }

    // If a ClientToken is recieved, login is successful and the game panel can be displayed.
    if (arg0 instanceof ClientToken) {
      ClientToken clientToken = (ClientToken) arg0;

      System.out.println("Session Token: " + clientToken.sessionToken);
      gamePanelControl.setSessionData(clientToken.sessionToken, clientToken.playerId);
      loginControl.loginSuccess();
      inGame = true;
    }

    // If we received a String, figure out what this event is.
    else if (arg0 instanceof String)
    {
      // Get the text of the message.
      String message = (String)arg0;
      
      // If we successfully created an account, tell the create account controller.
      if (message.equals("CreateAccountSuccessful"))
      {
        createAccountControl.createAccountSuccess();
      }
    }
    
    // If we received an Error, figure out where to display it.
    else if (arg0 instanceof GameDataError)
    {
      // Get the Error object.
      GameDataError error = (GameDataError)arg0;
      
      // Display login errors using the login controller.
      if (error.getType().equals("Login"))
      {
        loginControl.displayError(error.getMessage());
      }
      
      // Display account creation errors using the create account controller.
      else if (error.getType().equals("CreateAccount"))
      {
        createAccountControl.displayError(error.getMessage());
      }
    }
  }
}
