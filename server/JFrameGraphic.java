/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpgraphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import tcpgraphic.Instruction.DrawLine;
import tcpgraphic.Instruction.DrawOval;
import tcpgraphic.Instruction.DrawRect;
import tcpgraphic.Instruction.DrawString;
import tcpgraphic.Instruction.FillRect;
import tcpgraphic.Instruction.Instruction;
import tcpgraphic.Instruction.SetColor;

/**
 *
 * @author Tadashi
 */
public class JFrameGraphic extends javax.swing.JFrame {

    ArrayList<Instruction> instructions;

    BufferedImage biBackground;
    Graphics2D gFrame;
    Graphics2D gBackground;
    
    public int viewPortWidth;
    public int viewPortHeight;
    public double viewOriginX = 0;
    public double viewOriginY = 0;
    public double viewScaleFactor = 20;

    public double gridSize = 1;
    
    int mouseX = 0;
    int mouseY = 0;
    
    ViewContext context = new ViewContext(){
        @Override
        public int transformX(double x){
            return (int)(viewScaleFactor * x + viewOriginX);
        }

        @Override
        public int transformY(double y){
            return (int)(-viewScaleFactor * y + viewOriginY);
        }

        @Override
        public int transform(double n){
            return (int) (viewScaleFactor * n);
        }
    };
        
    TimerTask renderTask = new TimerTask(){
        @Override
        public void run(){
            gBackground.setColor(Color.white);
            gBackground.fillRect(0, 0, biBackground.getWidth(), biBackground.getHeight());
            
            if(gridSize != 0){
                gBackground.setColor(new Color(250, 250, 250));
                double delta = gridSize * viewScaleFactor;
                for(int i = 0; i < (int)(viewPortWidth /viewScaleFactor); i++){
                    int x = (int)(viewOriginX % delta + delta * i);
                    gBackground.drawLine(x, 0, x, viewPortHeight);
                }
                for(int i = 0; i < (int)(viewPortHeight /viewScaleFactor); i++){
                    int y = (int)(viewOriginY % delta + delta * i);
                    gBackground.drawLine(0, y, viewPortWidth, y);
                }
                gBackground.setColor(new Color(230, 230, 230));
                gBackground.drawLine((int)viewOriginX, 0, (int)viewOriginX, viewPortHeight);
                gBackground.drawLine(0, (int)viewOriginY, viewPortWidth, (int)viewOriginY);
            }
            
            gBackground.setColor(Color.black);
            for(int i = 0; i < instructions.size(); i++){
                instructions.get(i).setContext(context);
                instructions.get(i).draw(gBackground);
            }
            
            gFrame.drawImage(biBackground, 0, 0, null);
            
        }
    };
    
    Thread socketThread = new Thread(){
        public void run(){
            try{
                String clientCommand;
                ServerSocket welcomeSocket = new ServerSocket(6789);

                while(true)
                {
                    Socket connectionSocket = welcomeSocket.accept();
                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    try{
                        while(true){
                            if(!manageStream(inFromClient)) break;
                        }
                    }catch(IOException e){}
                }
            }catch(IOException e){}
        }
    };
    
    /**
     * Creates new form JFrameGraphic
     */
    public JFrameGraphic() {
        initComponents();
        gFrame = (Graphics2D) this.getGraphics();
        biBackground = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
        gBackground = (Graphics2D) biBackground.getGraphics();
        gBackground.setColor(Color.white);
        gBackground.fillRect(0, 0, 1024, 768);
        gBackground.setColor(new Color(0, 0, 0));
        
        viewPortWidth = this.getWidth();
        viewPortHeight = this.getHeight();
        
        viewOriginX = this.getWidth() / 2;
        viewOriginY = this.getHeight() / 2;
        
        instructions = new ArrayList<>();

        Timer t = new Timer();
        t.schedule(renderTask, 0, 10);
        
        socketThread.start();
    }
    
    
    boolean manageStream(BufferedReader br) throws IOException {
        String clientCommand = br.readLine();
        if(clientCommand == null) return false;
        
        switch(clientCommand){
            case "clear":{
                Color aux = gBackground.getColor();
                gBackground.setColor(Color.white);
                gBackground.fillRect(0, 0, biBackground.getWidth(), biBackground.getHeight());
                gBackground.setColor(aux);
                instructions.clear();
                System.out.println("clear");
                break;  
            }case "drawLine":{
                double x1 = Double.parseDouble(br.readLine());
                double y1 = Double.parseDouble(br.readLine());
                double x2 = Double.parseDouble(br.readLine());
                double y2 = Double.parseDouble(br.readLine());
                instructions.add(new DrawLine(x1, y1, x2, y2));
                System.out.println("drawLine: (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
                break;
            }case "drawRect":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                instructions.add(new DrawRect(x, y, w, h));
                System.out.println("drawRect: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "drawOval":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                instructions.add(new DrawOval(x, y, w, h));
                System.out.println("drawOval: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "fillRect":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                instructions.add(new FillRect(x, y, w, h));
                System.out.println("fillRect: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "setColor":{
                int r = Integer.parseInt(br.readLine());
                int g = Integer.parseInt(br.readLine());
                int b = Integer.parseInt(br.readLine());
                instructions.add(new SetColor(r, g, b));
                System.out.println("setColor: " + r + " " + g + " " + b + " ");
                break;
            }case "drawString":{
                String str = br.readLine();
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                instructions.add(new DrawString(str, x, y));
                System.out.println("drawString: (" + x + ", " + y + "), string \"" + str + "\"");
                break;
            }case "setAntialiasing":{
                boolean aliasing = Boolean.parseBoolean(br.readLine());
                if(aliasing){
                    gBackground.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                }else{
                    gBackground.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                }
                System.out.println("Antialiasing: " + aliasing);
                break;
            }case "setGridSize":{
                double gridSize = Double.parseDouble(br.readLine());
                this.gridSize = gridSize;
                System.out.println("setGrid: " + gridSize);
                break;
            }default:
                System.out.println("?? [" + clientCommand + "]");
                break;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 789, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int deltaX = evt.getX() - mouseX;
        int deltaY = evt.getY() - mouseY;
        
        viewOriginX += deltaX;
        viewOriginY += deltaY;
        
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_formMouseDragged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_formMouseMoved

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // TODO add your handling code here:
        double rotation = evt.getPreciseWheelRotation();
        double scaleFactor;
        if(rotation > 0){
            scaleFactor = 1 / 1.5;
        }else{
            scaleFactor = 1.5;
        }
        int scaleOriginX = evt.getX();
        int scaleOriginY = evt.getY();
        
        viewOriginX = (viewOriginX - scaleOriginX) * scaleFactor + scaleOriginX;
        viewOriginY = (viewOriginY - scaleOriginY) * scaleFactor + scaleOriginY;
        viewScaleFactor = viewScaleFactor * scaleFactor;
        
    }//GEN-LAST:event_formMouseWheelMoved

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameGraphic().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
