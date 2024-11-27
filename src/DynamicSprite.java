import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.Timer;

public class DynamicSprite extends SolidSprite{
    protected Direction direction = Direction.EAST;
    protected double speed = 5;
    protected double timeBetweenFrame = 250;
    protected boolean isWalking =true;
    protected final int spriteSheetNumberOfColumn = 10;
    protected double life;
    private GameEngine gameEngine;


    public DynamicSprite(double x, double y, Image image, double width, double height, double life, GameEngine gameEngine) {
        super(x, y, image, width, height);
        this.life = life;
        this.gameEngine = gameEngine;
        if (this.gameEngine == null) {
            System.out.println("GameEngine is NULL in DynamicSprite constructor!");
        }
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment){
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch(direction){
            case EAST: moved.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:  moved.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:  moved.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(moved)){
                    return false;
                }
            }
        }
        return true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move(){
        switch (direction){
            case NORTH -> {
                this.y-=speed;
            }
            case SOUTH -> {
                this.y+=speed;
            }
            case EAST -> {
                this.x+=speed;
            }
            case WEST -> {
                this.x-=speed;
            }
        }
    }

    public void moveIfPossible(ArrayList<Sprite> environment){
        if (isMovingPossible(environment)){
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);
    }

    // Dégat au héro
    public void hit(Trap trap) {
        if (this.life - trap.getDamage() < 0) {
            this.life = 0;
        } else {
            this.life = this.life - trap.getDamage();
        }
    }

    // Méthode qui definie comment faire des dégats des pièges vers le héro
    public void setDamage(ArrayList<Sprite> environment) {
            System.out.println("Points de vie " + this.life);
            for (Sprite sprite : environment) {
                if (sprite instanceof Trap) {
                    Trap trap = (Trap) sprite;
                    System.out.println("Hero hitbox : " + this.getHitBox());
                    System.out.println("Trap hitbox : " + trap.getHitBox());
                    System.out.println("Trap dégats : " + trap.getDamage());
                    if (this.isNearTrap(environment)) {
                        this.hit(trap);
                    }
                }
            }
    }

    // méthode qui définit le comportement du portail level1-->level2
    public void setPortail(ArrayList<Sprite> environment) {
        for (Sprite sprite : environment) {
            if (sprite instanceof Portail) {
                System.out.println("portail hitbox : " + ((SolidSprite) sprite).getHitBox());
                if (this.isNearPortail(environment)) {
                    System.out.println("Portail touché !");
                    System.out.println("Portail détecté vers : " + ((Portail) sprite).getDestinationLevel());
                    // Change de niveau
                    gameEngine.changeLevel("./data/level2.txt");
                    break;
                }
            }
        }
    }

    // Passe la vitesse du héro à 0 quand il est dans une certaine zone, et lui fait des dégats.
    // Cette zone peut être plus ou moins grande en fonction de Cx et Cy du Trap
    public void Stun(LongRange longRange) {
        if ((this.getX())*(longRange.getCx()) > longRange.getX() && (this.getX())*(longRange.getCx()) < (longRange.getX()+longRange.getWidth())) {
            System.out.println("Le héro est dans la zone de stun");
            this.speed = 0;
            this.life = this.life - 100;
        }
        if ((this.getY())*(longRange.getCy()) > longRange.getY() && (this.getY())*(longRange.getCy()) < (longRange.getY()+longRange.getHeight())) {
            System.out.println("Le héro est dans la zone de stun");
            this.speed = 0;
            this.life = this.life - 100;
        }
    }

    // Munit les LongRanges de la méthode Stun ci-dessus
    public void SetStun(ArrayList<Sprite> environment) {
        for (Sprite sprite : environment) {
            if (sprite instanceof LongRange) {
                System.out.println("La TDE est en : " + ((LongRange) sprite).getHitBox());
                this.Stun((LongRange) sprite);
            }
        }
    }

    public double getLife(){
        return this.life;
    }
}
