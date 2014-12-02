import java.net.*;
import java.io.*;

public class ChatClient
{  private Socket socket              = null;
  MessageObject objDataObject;
  ObjectInputStream objInputStream;
  ObjectOutputStream objOutputStream;
  public ChatClient(){}
   public ChatClient(String serverName, int serverPort)
   {  System.out.println("Establishing connection. Please wait ...");
      try
      {  objDataObject = new MessageObject();
    	  socket = new Socket(serverName, serverPort);
         System.out.println("Connected: " + socket);
          
         objDataObject.setMessage("hello");
         objOutputStream =
 				new ObjectOutputStream(socket.getOutputStream());

 			objInputStream =
 				new ObjectInputStream(socket.getInputStream());
 			//objOutputStream.writeObject(objDataObject);
 			System.out.println("Messaged sent : " + objDataObject.getMessage());
 			//objDataObject = (DataObject)objInputStream.readObject();

            System.out.println("Messaged received : " + objDataObject.getMessage());

      String line = "";
      while (!objDataObject.getMessage().equals("Exit"))
      {  try
         {  objDataObject = (MessageObject)objInputStream.readObject();
         	objOutputStream.writeObject(objDataObject);
            System.out.println("In client->"+objDataObject.getMessage());
            //streamOut.flush();
         }
         catch(IOException ioe)
         {  System.out.println("Sending error: " + ioe.getMessage());
         }
      }
      objOutputStream.close();	
		objInputStream.close();
		socket.close();
      }
      catch(UnknownHostException uhe)
      {  System.out.println("Host unknown: " + uhe.getMessage());
      }
      catch(Exception ioe)
      {  System.out.println("Unexpected exception: " + ioe.getMessage());
      }
   }
   public void start() throws IOException
   { 
   }

  // public static void main(String args[])
   public  void main1()
   {  ChatClient client = null;
      //if (args.length != 2)
        // System.out.println("Usage: java ChatClient host port");
    //  else
         client = new ChatClient("localhost",2020);
   }
}
