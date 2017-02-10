/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpgraphic.Instruction;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Tadashi
 */
public class SetColor extends Instruction{
    public int r, g, b;

    public SetColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(new Color(this.r, this.g, this.b));
    }
}