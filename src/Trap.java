import java.awt.*;

public class Trap extends SolidSprite {
    public double damage;

    public Trap(double x, double y, Image image, double width, double height, double damage) {
        super(x, y, image, width, height);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }


}


