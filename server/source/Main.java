package tcpgraphic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import tcpgraphic.Instruction.DrawLine;
import tcpgraphic.Instruction.DrawOval;
import tcpgraphic.Instruction.DrawRect;
import tcpgraphic.Instruction.DrawString;
import tcpgraphic.Instruction.FillOval;
import tcpgraphic.Instruction.FillRect;
import tcpgraphic.Instruction.SetColor;

/**
 *
 * @author Tadashi
 */
public class Main {
    
    private JFrameGraphic frame;
    
    private Thread socketThread = new Thread(){
        @Override
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
    
    private boolean manageStream(BufferedReader br) throws IOException {
        String clientCommand = br.readLine();
        if(clientCommand == null) return false;
        
        switch(clientCommand){
            case "clear":{
                frame.getRendering().clear();
                System.out.println("clear");
                break;  
            }case "drawLine":{
                double x1 = Double.parseDouble(br.readLine());
                double y1 = Double.parseDouble(br.readLine());
                double x2 = Double.parseDouble(br.readLine());
                double y2 = Double.parseDouble(br.readLine());
                frame.getRendering().addInstruction(new DrawLine(x1, y1, x2, y2));
                System.out.println("drawLine: (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
                break;
            }case "drawRect":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                frame.getRendering().addInstruction(new DrawRect(x, y, w, h));
                System.out.println("drawRect: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "drawOval":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                frame.getRendering().addInstruction(new DrawOval(x, y, w, h));
                System.out.println("drawOval: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "fillRect":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                frame.getRendering().addInstruction(new FillRect(x, y, w, h));
                System.out.println("fillRect: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "fillOval":{
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                double w = Double.parseDouble(br.readLine());
                double h = Double.parseDouble(br.readLine());
                frame.getRendering().addInstruction(new FillOval(x, y, w, h));
                System.out.println("fillOval: (" + x + ", " + y + "), width " + w + ", height " + h + ")");
                break;
            }case "setColor":{
                int r = Integer.parseInt(br.readLine());
                int g = Integer.parseInt(br.readLine());
                int b = Integer.parseInt(br.readLine());
                frame.getRendering().addInstruction(new SetColor(r, g, b));
                System.out.println("setColor: " + r + " " + g + " " + b + " ");
                break;
            }case "drawString":{
                String str = br.readLine();
                double x = Double.parseDouble(br.readLine());
                double y = Double.parseDouble(br.readLine());
                frame.getRendering().addInstruction(new DrawString(str, x, y));
                System.out.println("drawString: (" + x + ", " + y + "), string \"" + str + "\"");
                break;
            }case "setAntialiasing":{
                boolean antialiasingFlag = Boolean.parseBoolean(br.readLine());
                frame.getRendering().setAntialiasing(antialiasingFlag);
                System.out.println("Antialiasing: " + antialiasingFlag);
                break;
            }case "setGridSize":{
                double gridSize = Double.parseDouble(br.readLine());
                frame.getRendering().setGridSize(gridSize);
                System.out.println("setGridSize: " + gridSize);
                break;
            }case "setGridColor":{
                int r = Integer.parseInt(br.readLine());
                int g = Integer.parseInt(br.readLine());
                int b = Integer.parseInt(br.readLine());
                frame.getRendering().setGridColor(r, g, b);
                System.out.println("setGridColor: " + r + " " + g + " " + b + " ");
                break;
            }case "setAxisColor":{
                int r = Integer.parseInt(br.readLine());
                int g = Integer.parseInt(br.readLine());
                int b = Integer.parseInt(br.readLine());
                frame.getRendering().setAxisColor(r, g, b);
                System.out.println("setAxisColor: " + r + " " + g + " " + b + " ");
                break;
            }default:
                System.out.println("?? [" + clientCommand + "]");
                break;
        }
        return true;
    }
    
    public Main(){
        frame = new JFrameGraphic();
        frame.setVisible(true);
        socketThread.start();
    }
    
    public static void main(String args[]) {
        new Main();
    }
}
