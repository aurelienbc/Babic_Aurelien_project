import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {

    JFrame displayZoneFrame;

    RenderEngine renderEngine;
    GameEngine gameEngine;
    PhysicEngine physicEngine;

    public static Timer renderTimer;
    public static Timer gameTimer;
    public static Timer physicTimer;


    public Main() throws Exception{
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(4000,6000);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Playground level = new Playground("./data/level1.txt");


        renderEngine = new RenderEngine(displayZoneFrame);
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(null, level, renderEngine, physicEngine);

        DynamicSprite hero = new DynamicSprite(250,256,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")),48,50,1000,gameEngine);

        gameEngine.setHero(hero);

        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);


        //SolidSprite testSprite = new DynamicSprite(100,100,test,0,0);
        renderEngine.addToRenderList(level.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
        physicEngine.setEnvironment(level.getSolidSpriteList());

        displayZoneFrame.addKeyListener(gameEngine);


        for (Sprite sprite : level.getSolidSpriteList()) {
            if (sprite instanceof Portail) {
                Portail portail = (Portail) sprite;
                System.out.println("Portail position : x=" + portail.getX() + ", y=" + portail.getY());
                System.out.println("Vérification Portail : " + portail);
            }
        }

    }

    public static void main (String[] args) throws Exception {
	// write your code here
        Main main = new Main();
    }
}
