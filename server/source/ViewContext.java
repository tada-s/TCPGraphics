package tcpgraphic;

/**
 *
 * @author Tadashi
 */
public class ViewContext {
    private int viewportWidth = 0;
    private int viewportHeight = 0;
    private double viewOriginX = 0;
    private double viewOriginY = 0;
    private double viewScaleFactor = 20;
    
    /* Linear transformation to java window coordinate */
    
    public int transformX(double x){
        return (int)(viewScaleFactor * x + viewOriginX);
    }

    public int transformY(double y){
        return (int)(-viewScaleFactor * y + viewOriginY);
    }

    public int transform(double n){
        return (int) (viewScaleFactor * n);
    }
    
    /* Getter and Setters */
    
    public int getViewportWidth() {
        return viewportWidth;
    }

    public void setViewportWidth(int viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    public int getViewportHeight() {
        return viewportHeight;
    }

    public void setViewportHeight(int viewportHeight) {
        this.viewportHeight = viewportHeight;
    }

    public double getViewOriginX() {
        return viewOriginX;
    }

    public void setViewOriginX(double viewOriginX) {
        this.viewOriginX = viewOriginX;
    }

    public double getViewOriginY() {
        return viewOriginY;
    }

    public void setViewOriginY(double viewOriginY) {
        this.viewOriginY = viewOriginY;
    }

    public double getViewScaleFactor() {
        return viewScaleFactor;
    }

    public void setViewScaleFactor(double viewScaleFactor) {
        this.viewScaleFactor = viewScaleFactor;
    }
}
