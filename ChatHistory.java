import java.io.*;
import java.sql.*;

public class ChatHistory {

	private Connection conn=null;
	String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=DBChatHistory.mdb;";
    MessageObject objDataObject;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
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
}
