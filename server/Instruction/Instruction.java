/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpgraphic.Instruction;

import java.awt.Color;
import java.awt.Graphics2D;
import tcpgraphic.ViewContext;

/**
 *
 * @author Tadashi
 */
public abstract class Instruction{
    
    public ViewContext context;
    
    abstract public void draw(Graphics2D g);

    public void setContext(ViewContext context){
        this.context = context;
    }
    

    

}


