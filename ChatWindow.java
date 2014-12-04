import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.text.*;

import javax.swing.*;

/**
 * 
 * @author rosh
 */
public class ChatWindow extends JFrame implements Runnable {

	// private String strServer ="localhost";
	private String strServer = "10.98.219.182";
	private int iPort = 2020;
	public StringBuffer sb = new StringBuffer();
	public ArrayList<String> arrListUserNames = new ArrayList<String>();
	private JButton jButtonClear;
	private JButton jButtonConnect;
	private JButton jButtonChatHistory;
	private JButton jButtonDisconnect;
	private JButton jButtonRemove;
	private JButton jButtonSend;
	private JList jListUsers;
	private JPanel jPanel1;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JTextField jTextFieldChat;
	private JTextField jTextUserName;
	private JPasswordField jPasswordField;
	private JLabel jLabelUserName;
	private JLabel jLabelPassWord;
	private javax.swing.JLabel jLabelUserList;
	private DrawPanel drawPanel1;
	private JTextPane jtextPane;
	boolean bIsConnected;
	public ChatClient objChatClient;
	private StyledDocument styleDoc;
	private SimpleAttributeSet sAttrSet;
	private Socket socket = null;
	private MessageObject objDataObject;
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
	private DefaultListModel listModel;
	public String strCurrentUser = "";

	public ChatWindow() {
		initComponents();
	}
	public ChatWindow(Socket s,ObjectInputStream objInputStream,ObjectOutputStream objOutputStream) {
		this.socket = s;
		this.objInputStream = objInputStream;
		this.objOutputStream = objOutputStream;
	}
	@SuppressWarnings("unchecked")
	private void initComponents() {

		listModel = new DefaultListModel();
		jPanel1 = new javax.swing.JPanel();
		jTextFieldChat = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		jButtonSend = new javax.swing.JButton();
		jButtonClear = new javax.swing.JButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		// jListUsers = new javax.swing.JList();
		jButtonRemove = new javax.swing.JButton();
		jButtonConnect = new javax.swing.JButton();
		jButtonChatHistory = new javax.swing.JButton();
		jButtonDisconnect = new javax.swing.JButton();
		jTextUserName = new javax.swing.JTextField();
		jPasswordField  = new javax.swing.JPasswordField();
		jLabelUserName = new javax.swing.JLabel();
		jLabelPassWord = new javax.swing.JLabel();
		jLabelUserList = new javax.swing.JLabel();
		jButtonConnect.setEnabled(false);
		jtextPane = new JTextPane();
		jtextPane.setEditable(false);
		styleDoc = jtextPane.getStyledDocument();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Chat Window");
		try {
			socket = new Socket(strServer, iPort);
			objOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception ex) {
			ex.toString();
		}
		drawPanel1 = new DrawPanel(socket,objInputStream,objOutputStream);
		sAttrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(sAttrSet, Color.BLUE);
		StyleConstants.setBackground(sAttrSet, Color.YELLOW);

        jTextFieldChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldChatKeyPressed(evt);
            }
        });

        jScrollPane1.setViewportView(jtextPane);

        jButtonSend.setText("Send");
        jButtonSend.setActionCommand("btnSend");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonRemove.setText("Remove");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });
        jTextUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextUserNameKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClear)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonRemove)
                        .addGap(431, 431, 431))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(drawPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(drawPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSend)
                    .addComponent(jButtonClear)
                    .addComponent(jButtonRemove)))
        );

        jButtonConnect.setText("Connect");
        jButtonConnect.setToolTipText("");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });
        
        jButtonChatHistory.setText("ChatHistory");
        jButtonChatHistory.setToolTipText("");
        jButtonChatHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	jButtonChatHistoryActionPerformed(evt);
            }
        });

        jButtonDisconnect.setText("Disconnect");
        jButtonDisconnect.setActionCommand("jButtonDisconnect");
        jButtonDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDisconnectActionPerformed(evt);
            }
        });

        jLabelUserName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelUserName.setText("User Name");
        jLabelPassWord.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelPassWord.setText("Password");
        jLabelUserList.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelUserList.setText("UserList");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonConnect)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDisconnect)
                         .addGap(18, 18, 18)
                        .addComponent(jButtonChatHistory)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabelUserName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
               // .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addComponent(jLabelPassWord)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelUserList)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelUserName)
                        .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelPassWord))
                    .addComponent(jLabelUserList, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDisconnect)
                    .addComponent(jButtonConnect)
                    .addComponent(jButtonChatHistory)))
        );

        pack();
	}

	private void jButtonSendActionPerformed(ActionEvent evt) {

		String strMessage, strUserName = "";
		if (isConnected()) {
			if (!jTextFieldChat.getText().trim().equals("")) {
				strUserName = jTextUserName.getText();

				if (sb.indexOf(strUserName) == -1) {
					sb.append(strUserName + " :>");
				}
				strMessage = strUserName + " :>" + jTextFieldChat.getText();
				sb.append(strMessage + "\n");
				setChatArea(0, strMessage + "\n");
				objDataObject = new MessageObject();
				objDataObject.setMessage(strMessage);
				objDataObject.setStrUserName(strUserName);
				objDataObject.setbActiveUser(true);
				sendMessage(objDataObject);
				jTextFieldChat.setText("");
			}
		}
	}
	private void jTextFieldChatKeyPressed(KeyEvent evt) {
		
		int key = evt.getKeyCode();
		if (evt.getSource() == jTextFieldChat) {
			if (key == KeyEvent.VK_ENTER) {
				String strMessage, strUserName = "";
				if (!jTextFieldChat.getText().trim().equals("")) {
					strUserName = jTextUserName.getText();

					if (sb.indexOf(strUserName) == -1) {
						sb.append(strUserName + " :>");
					}
					strMessage = strUserName + " :>" + jTextFieldChat.getText();
					sb.append(strMessage + "\n");
					setChatArea(0, strMessage + "\n");
					//System.out.println("sb.toString()->" + sb.toString());
					objDataObject = new MessageObject();
					objDataObject.setMessage(strMessage);
					objDataObject.setStrUserName(strUserName);
					objDataObject.setbActiveUser(true);
					sendMessage(objDataObject);
					jTextFieldChat.setText("");
				}
			}
		}
	}
	public void setChatArea(int iSource, String strTempMsg) {

		try {
			// 0 :current user
			if (iSource == 0) {
				styleDoc.insertString(styleDoc.getLength(), strTempMsg,
						sAttrSet);
			} else {
				styleDoc.insertString(styleDoc.getLength(), strTempMsg, null);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void jButtonRemoveActionPerformed(ActionEvent evt) {
		

		try {
			int i = jListUsers.getSelectedIndex();
			if (i > 0) {
				listModel.remove(i);
				jScrollPane2.setViewportView(jListUsers);
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	private void jButtonConnectActionPerformed(ActionEvent evt) {
		String strUserName, strMessage = "";
		// listModel.addElement("");
		strUserName = jTextUserName.getText();
		strUserName = jTextUserName.getText();
		if (!strUserName.equals("")) {
			if (!arrListUserNames.contains(strUserName)) {
				arrListUserNames.add(strUserName);
			}
			jButtonConnect.setEnabled(true);
			jTextUserName.setEditable(false);
			jListUsers = new JList(listModel);
			listModel.addElement(strUserName);
			jScrollPane2.setViewportView(jListUsers);
			connect(strUserName);
			strMessage = "**User " + strUserName + " joined ";
			sb.append(strMessage + "\n");
			setChatArea(0, strMessage + "\n");

			objDataObject = new MessageObject();
			objDataObject.setMessage(strMessage);
			objDataObject.setStrUserName(strUserName);
			objDataObject.setbActiveUser(true);
			sendMessage(objDataObject);
			jButtonConnect.setEnabled(false);
			strCurrentUser = strUserName;
		} else {
			jButtonConnect.setEnabled(false);
			System.out.println("User Name is empty, Cannot connect.");
		}

	}
	private void jButtonDisconnectActionPerformed(ActionEvent evt) {
		int ch = JOptionPane.showConfirmDialog(null, "Confirm", "Disconnect",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		String strMessage, strUserName = "";
		if (ch == JOptionPane.OK_OPTION) {
			System.out.println("Closed the connection");
			strUserName = jTextUserName.getText();
			strMessage = "**User " + strUserName + " left ";
			sb.append(strMessage + "\n");
			setChatArea(0, strMessage + "\n");

			setConnected(false);
			jButtonRemoveActionPerformed(evt);
			objDataObject = new MessageObject();
			objDataObject.setMessage(strMessage);
			objDataObject.setStrUserName(strUserName);
			objDataObject.setbActiveUser(false);
			objDataObject.setSbChatHistory(sb);
			sendMessage(objDataObject);
			maintainUserList(objDataObject);
			disconnect();
			jButtonConnect.setEnabled(true);

		} else if (ch == JOptionPane.CANCEL_OPTION) {
			System.out.println("Still Connected");
		}
	}

	public void disconnect() {
		System.out.println("Disconnecting the connection to server.");
		try {
			if (socket != null) {
				socket.close();
			}
			if (objInputStream != null) {
				objInputStream.close();
			}
			if (objOutputStream != null) {
				objOutputStream.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception: method :disconnect() "
					+ ex.getMessage());
		}
	}
	public void connect(String strUsername) {
		setConnected(true);

		System.out.println("Connecting to Server:" + strServer + ", Port:"
				+ iPort + ", User:" + strUsername);
		try {
			new Thread(this).start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void sendMessage(Object obj) {

		try {
			if(obj == null){
				System.out.println("obj->" + obj);
			}
			objOutputStream.writeObject(obj);
			System.out.println("obj->" + obj);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception in Method SendMessage->"
					+ ex.toString());

		}
	}
	public void run() {
		try {
			objInputStream = new ObjectInputStream(socket.getInputStream());
			while (true) {
				Object obj = objInputStream.readObject();
				if(obj instanceof MessageObject){
					MessageObject objMessageObject = (MessageObject) objInputStream
							.readObject();
					if (objMessageObject != null) {
						String strmessage = objMessageObject.getMessage();
						System.out.println("In client :strmessage-> "
								+ objMessageObject);
						if (!objMessageObject.getStrUserName().equals(
								strCurrentUser)) {
							sb.append(strmessage + "\n");
							setChatArea(1, strmessage + "\n");
						}
						maintainUserList(objMessageObject);
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		} finally {
			try {
				objInputStream.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void maintainUserList(Object obj) {

		MessageObject objMessageObject = (MessageObject) obj;
		boolean bUserExists = false;
		int iIdex = 0;
		for (int i = 0; i < listModel.size(); i++) {
			if (listModel.getElementAt(i).toString()
					.equals(objMessageObject.getStrUserName())) {
				bUserExists = true;
				iIdex = i;
				break;
			}
		}
		if (!bUserExists && objMessageObject.isbActiveUser()) {
			listModel.addElement(objMessageObject.getStrUserName());
			jScrollPane2.setViewportView(jListUsers);
		}
		if (bUserExists && !objMessageObject.isbActiveUser()) {
			listModel.remove(iIdex);
			jScrollPane2.setViewportView(jListUsers);
		}

	}

	public void setConnected(boolean c) {
		bIsConnected = c;
	}

	public boolean isConnected() {
		return bIsConnected;
	}

	private void jButtonChatHistoryActionPerformed(ActionEvent evt) {
	
	}

	private void jButtonClearActionPerformed(ActionEvent evt) {
		
		jtextPane.setText("");
		sb.delete(0, sb.length());
	}

	private void jListUsersMouseClicked1(MouseEvent evt) {
		
	}

	private void jTextUserNameKeyTyped(java.awt.event.KeyEvent evt) {
		
		jButtonConnect.setEnabled(true);
	}

	

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ChatWindow().setVisible(true);
			}
		});
	}

}
