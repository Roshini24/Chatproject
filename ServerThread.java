import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread{
  Socket socket;
  BufferedReader br;
  PrintWriter pw;
  String temp;
  ArrayList<ServerThread>clientRequests;
  MessageObject objDataObject;
  ObjectInputStream objectInputStream;
  ObjectOutputStream objectOutputStream;
  public ServerThread(Socket socket,ArrayList<ServerThread> clientRequests){
    this.socket = socket;
	this.clientRequests = clientRequests;
	clientRequests.add(this);
  }  
  public void run(){
    try{
    	 objectInputStream =
				new ObjectInputStream(socket.getInputStream());
		 objectOutputStream =
				new ObjectOutputStream(socket.getOutputStream());

		while(true){
			objDataObject = (MessageObject) objectInputStream.readObject();
			//System.out.println("in server:objDataObject->" + objDataObject);
			sendToAll(objDataObject);
		}	
    }catch(Exception ioe){
      System.out.println(" run()-->"+ioe.getMessage());      
    }finally{
    }
  }
  public synchronized void sendToAll(Object obj){
	  System.out.println("**Entering Class Serverthread: method: sendToAll*****");
		Iterator<ServerThread> it = clientRequests.iterator();
		while(it.hasNext()){
			ServerThread objServerThread = it.next();
			try{
				objServerThread.objectOutputStream.writeObject(obj);
				System.out.println("in server:obj->" + obj);
				objServerThread.objectOutputStream.reset();
			}catch(IOException e){
				System.out.println(e.getMessage());
			}
		}
		 System.out.println("**Leaving Class Serverthread: method: sendToAll*****");
	}
  	
    public void close() throws IOException
    {  if (socket != null)    socket.close();
       if (objectInputStream != null)  objectInputStream.close();
       if (objectOutputStream != null) objectOutputStream.close();
    }
 
    
  }
