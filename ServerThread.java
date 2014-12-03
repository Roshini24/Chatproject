import java.net.*;
import java.util.*;
import java.io.*;
import java.sql.*;

public class ServerThread extends Thread{
  Socket socket;
  BufferedReader br;
  PrintWriter pw;
  String temp;
  ArrayList<ServerThread>clientRequests;
  MessageObject objDataObject;
  ObjectInputStream objectInputStream;
  ObjectOutputStream objectOutputStream;
  private Connection conn=null;
  String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=DBChatHistory.mdb;";
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
			saveChatHistory(objDataObject);
			sendToAll(objDataObject);
		}	
    }catch(Exception ioe){
      System.out.println(" run()-->"+ioe.getMessage());      
    }finally{
    }
  }
  public void saveChatHistory(Object obj){
	  String strQuery,strUserName,strMessage ="";
	  StringBuffer sbChatHistory ;
	  Statement stmt=null;
	  try
      {
    	  objDataObject = (MessageObject) obj;
    	  strUserName = objDataObject.getStrUserName().toUpperCase();
    	  sbChatHistory = objDataObject.getSbChatHistory();
    	  strMessage = objDataObject.getMessage();
		  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	      conn = DriverManager.getConnection(database, "", "");

          stmt = conn.createStatement();
          // Fetch table
          String selTable = "SELECT * FROM UserList" ;
          stmt.execute(selTable);
          ResultSet rs = stmt.getResultSet();
          boolean bUserExists =false;
          String strtemp="";
          while((rs!=null) && (rs.next()))
          {
        	  strtemp =rs.getString(2);
              System.out.println(rs.getString(1) + " : " + strtemp);
              if(strtemp.toUpperCase().equals(strUserName)){
            	  bUserExists =true;
            	  break;
              }
          }
          if(!bUserExists){
        	  strQuery = "INSERT INTO UserList(Username,Createdtime)  VALUES ('"+strUserName+"', Now())";
              stmt.execute(strQuery);
          }
          if(!objDataObject.isbActiveUser()){
	          strQuery = "INSERT INTO ChatHistory(UserName,ChatMessage,Createddate)  VALUES " +
	          		"('"+strUserName+"','"+sbChatHistory.toString()+"',Now())";
	          stmt.execute(strQuery);
          }
      }
      catch(Exception ex)
      {
    	  System.out.println(ex.getMessage());
          ex.printStackTrace();
      }finally{
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
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
