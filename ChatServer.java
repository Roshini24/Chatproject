import java.net.*;
import java.util.*;
import java.io.*;

public class ChatServer{
  Socket socket;
  int port = 2020;
  MessageObject objDataObject;
  ObjectInputStream objInputStream;
  ObjectOutputStream objOutputStream;
  ServerThread objServerThread;
  ArrayList<ServerThread>handlers;

public ChatServer(){
      System.out.println("Starting the server please wait...................");
    try{
      System.out.println("Binding to port " + port);
      ServerSocket ss = new ServerSocket(port);
      System.out.println("Server started............. "+ss);
      ArrayList<ServerThread>clientRequests = new ArrayList<ServerThread>();
      boolean done = false;
      while(true)
      {  try
         {  
    	  socket = ss.accept();
    	  System.out.println( "Connection from "+socket );
    	  objServerThread = new ServerThread(socket,clientRequests);
    	  objServerThread.start();
         }
         catch(Exception ioe)
         {  done = false;
         }
      }
        
    }catch(IOException ioe){
      System.out.println(ioe.getMessage());
    }catch (Exception ex){
    	System.out.println(ex.getMessage());
    }
    
  }
  public static void main(String[] args){
	    ChatServer server = new ChatServer(); 
  } 
}





