package tcpgraphic.Instruction;

import java.awt.Graphics2D;

/**
 *
 * @author Tadashi
 */
public class DrawRect extends Instruction{
    public double x, y, w, h;

    public DrawRect(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void execute(Graphics2D g){
        g.drawRect(context.transformX(x), context.transformY(y + h), context.transform(w), context.transform(h));
    }
}
