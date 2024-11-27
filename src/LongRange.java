import java.awt.*;

// On aurait pu l'extend de Trap pour plus de rigueur
public class LongRange extends SolidSprite{
    public double cx;
    public double cy;

    public LongRange(double x, double y, Image image, double width, double height, double cx, double cy){
        super(x, y, image, width, height);
        this.cx = cx;
        this.cy = cy;
    }

    public double getCx(){
        return cx;
    }

    public double getCy(){
        return cy;
    }
}
