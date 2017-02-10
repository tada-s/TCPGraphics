/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpgraphic.Instruction;

import java.awt.Graphics2D;

/**
 *
 * @author Tadashi
 */
public class DrawLine extends Instruction{
    public double x1, y1, x2, y2;

    public DrawLine(double x1, double y1, double x2, double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g){
        g.drawLine(context.transformX(x1), context.transformY(y1),
                   context.transformX(x2), context.transformY(y2));
    
    }
}

