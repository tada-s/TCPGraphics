package tcpgraphic.Instruction;

import java.awt.Graphics2D;

/**
 *
 * @author Tadashi
 */
public class FillOval extends Instruction{
    public double x, y, w, h;

    public FillOval(double x, double y, double w, double h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    @Override
    public void execute(Graphics2D g){
        g.fillOval(context.transformX(x), context.transformY(y + h), context.transform(w), context.transform(h));
    }
}
