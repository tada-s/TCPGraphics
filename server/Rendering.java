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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import tcpgraphic.Instruction.Instruction;

/**
 *
 * @author Tadashi
 */
public class Rendering {
    
    private double gridSize = 1;
    
    private ViewContext context;
    
    private ArrayList<Instruction> instructions;
    
    private BufferedImage biBackground;
    private Graphics2D gBackground;
    private Graphics2D gOut;

    private Timer t;
    private TimerTask renderTask = new TimerTask(){
        @Override
        public void run(){
            // Clear Screen
            gBackground.setColor(Color.white);
            gBackground.fillRect(0, 0, biBackground.getWidth(), biBackground.getHeight());
            
            // Render Grid
            if(gridSize != 0){
                drawGrid();
            }
            
            // Render Objects
            gBackground.setColor(Color.black);
            for(int i = 0; i < instructions.size(); i++){
                instructions.get(i).setContext(context);
                instructions.get(i).execute(gBackground);
            }
            
            // Display rendered image
            gOut.drawImage(biBackground, 0, 0, null);
        }
    };
    
    public Rendering(Graphics2D graphics, ViewContext context){
        gOut = graphics;
        
        biBackground = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
        gBackground = (Graphics2D) biBackground.getGraphics();
        gBackground.setColor(Color.white);
        gBackground.fillRect(0, 0, 1024, 768);
        
        this.context = context;

        instructions = new ArrayList<>();
    }
    
    public void start(){
        t = new Timer();
        t.schedule(renderTask, 0, 10);
    }
    
    public void clear(){
        Color aux = gBackground.getColor();
        gBackground.setColor(Color.white);
        gBackground.fillRect(0, 0, biBackground.getWidth(), biBackground.getHeight());
        gBackground.setColor(aux);
        instructions.clear();
    }
    
    public void setAntialiasing(boolean flag){
        if(flag){
            gBackground.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }else{
            gBackground.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    public void addInstruction(Instruction i){
        instructions.add(i);
    }
    
    /* Private method */
    
    private void drawGrid(){
        gBackground.setColor(new Color(250, 250, 250));
        double delta = gridSize * context.getViewScaleFactor();
        for(int i = 0; i < (int)(context.getViewportWidth() / context.getViewScaleFactor()); i++){
            int x = (int)(context.getViewOriginX() % delta + delta * i);
            gBackground.drawLine(x, 0, x, context.getViewportHeight());
        }
        for(int i = 0; i < (int)(context.getViewportHeight() /context.getViewScaleFactor()); i++){
            int y = (int)(context.getViewOriginY() % delta + delta * i);
            gBackground.drawLine(0, y, context.getViewportWidth(), y);
        }
        gBackground.setColor(new Color(230, 230, 230));
        gBackground.drawLine((int)context.getViewOriginX(), 0, (int)context.getViewOriginX(), context.getViewportHeight());
        gBackground.drawLine(0, (int)context.getViewOriginY(), context.getViewportWidth(), (int)context.getViewOriginY());
    }
    
    /* Getter and Setters */
    
    public ArrayList<Instruction> getInstructions(){
        return this.instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions){
        this.instructions = instructions;
    }
    public void setGridSize(double gridSize){
        this.gridSize = gridSize;
    }
}
