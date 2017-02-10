/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tcpgraphic;

/**
 *
 * @author Tadashi
 */
public interface ViewContext {
    
    public int transformX(double x);
    
    public int transformY(double y);
    
    public int transform(double n);
    
}
