import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.util.Collections.min;

public class SolidSprite extends Sprite{
    public SolidSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }


    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(x,y,(double) width ,(double) height);
//        return new Rectangle2D.Double(this.x, this.y, this.width * 1.5, this.height * 1.5);
    }

    public boolean intersect(Rectangle2D.Double hitBox){
//        Hitbox Dynamique, si on rentre dans une zone proche la hitbox augmente
//        if ((hitBox.getX() + hitBox.getWidth() + this.getX() + this.getWidth())/2 < 175) {
//            System.out.println("Dynamic hitbox applied! New width: " + (width * 1.5));
//            return new Rectangle2D.Double(this.x, this.y, this.width * 1.5, this.height * 1.5).intersects(hitBox);
//        }
//        System.out.println("Standard hitbox used.");
        return this.getHitBox().intersects(hitBox);
    }

    private boolean isProximityActive = false;

    // Détecte si le héro est proche d'un Trap
    public boolean isNearTrap(ArrayList<Sprite> environment) {// Rayon de détection
        for (Sprite sprite : environment) {
            if (sprite instanceof Trap) {
                double distanceX = Math.abs(this.x - sprite.getX());
                double distanceY = Math.abs(this.y - sprite.getY());
                if (distanceX <= sprite.getWidth() + 5 && distanceY <= sprite.getHeight() + 5) {
                    isProximityActive = true;
                    return true;
                }
            }
        }
        isProximityActive = false;
        return false;
    }

    // Détecte si le héro est proche d'un portail
    public boolean isNearPortail(ArrayList<Sprite> environment) {// Rayon de détection
        for (Sprite sprite : environment) {
            if (sprite instanceof Portail) {
                double distanceX = Math.abs(this.x - sprite.getX());
                double distanceY = Math.abs(this.y - sprite.getY());
                if (distanceX <= sprite.getWidth() + 10 && distanceY <= sprite.getHeight() + 10) {
                    isProximityActive = true;
                    return true;
                }
            }
        }
        isProximityActive = false;
        return false;
    }
}


