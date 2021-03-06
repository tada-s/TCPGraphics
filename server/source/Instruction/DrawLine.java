package tcpgraphic.Instruction;

import java.awt.Graphics2D;
import java.util.ArrayList;

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
    public void execute(Graphics2D g){
        g.drawLine(context.transformX(x1), context.transformY(y1),
                   context.transformX(x2), context.transformY(y2));
    }
}

