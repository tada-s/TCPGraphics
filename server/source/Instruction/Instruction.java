package tcpgraphic.Instruction;

import java.awt.Graphics2D;
import java.util.ArrayList;
import tcpgraphic.ViewContext;

/**
 *
 * @author Tadashi
 */
public abstract class Instruction{
    
    public ViewContext context;
    
    abstract public void execute(Graphics2D g);

    public void setContext(ViewContext context){
        this.context = context;
    }
    
}


