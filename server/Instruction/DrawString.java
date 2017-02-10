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
public class DrawString extends Instruction{
    public String str;
    public double x, y;

    public DrawString(String str, double x, double y){
        this.str = str;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics2D g){
        g.drawString(str, context.transformX(x), context.transformY(y));
    }
}
