import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Portail extends SolidSprite {
    private String destinationLevel; // Nom ou identifiant du niveau de destination

    public Portail(double x, double y, Image image, int width, int height, String destinationLevel) {
        super(x, y, image, width, height);
        this.destinationLevel = destinationLevel;
    }

    public String getDestinationLevel() {
        return destinationLevel;
    }
}
