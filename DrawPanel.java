/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

/**
 *
 * @author rosh
 */
public class DrawPanel extends javax.swing.JPanel {

    /**
     * Creates new form DrawPanel
     */
	ArrayList<DrawObject> linelist;
	int lastX, lastY;
	ChatWindow objChatWindow;
	DrawObject objDrawObject;
	private Socket socket = null;
	private ObjectInputStream objInputStream;
	private ObjectOutputStream objOutputStream;
    public DrawPanel() {

    }
	public DrawPanel(Socket s,ObjectInputStream objInputStream,ObjectOutputStream objOutputStream) {
        linelist = new ArrayList<DrawObject>();
        initComponents();
		this.socket = s;
		this.objInputStream = objInputStream;
		this.objOutputStream = objOutputStream;
	}
public static void main1(String[] args) {
    JFrame frame = new JFrame();
    frame.getContentPane().add(new DrawPanel());

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400,400);
    frame.setVisible(true);
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonClear = new javax.swing.JButton();
        jButtonSend = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonSend.setText("SendDrawing");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSend)
                .addContainerGap(270, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClear)
                    .addComponent(jButtonSend))
                .addGap(0, 277, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        String strline= "";
    }//GEN-LAST:event_formMouseClicked

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        int endX = evt.getX();
		int endY = evt.getY();
		DrawObject line = new DrawObject(lastX, lastY, endX, endY);
		linelist.add(line);
		lastX = endX;
		lastY = endY;
		repaint();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseExited

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        lastX = evt.getX();
		lastY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseMoved

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        // TODO add your handling code here:
		linelist.clear();
		repaint();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        // TODO add your handling code here:
        objChatWindow = new ChatWindow(socket,objInputStream,objOutputStream);
        Iterator<DrawObject> it = linelist.iterator();  
        while(it.hasNext()){
          objDrawObject= it.next();
          objChatWindow.sendMessage(objDrawObject);
        }

    }//GEN-LAST:event_jButtonSendActionPerformed

public void paintComponent(Graphics g){
		super.paintComponent(g);
		Iterator<DrawObject> it = linelist.iterator();
		while(it.hasNext()){
			DrawObject objDrawObject = it.next();
			g.drawLine(objDrawObject.getStartX(), objDrawObject.getStartY(),
					objDrawObject.geteX(), objDrawObject.getEndY());
		}
		//LineMessage lm = new LineMessage();
		//lm.setMessage(linelist);
		//container.sendMessage(lm);
		g.drawString("",100,100);
	}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonSend;
    // End of variables declaration//GEN-END:variables
}
