import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameEngine implements Engine,KeyListener {
    DynamicSprite hero;
    private Playground level;
    private RenderEngine renderEngine;
    private PhysicEngine physicEngine;
    private boolean isGameOver = false;

    public GameEngine(DynamicSprite hero, Playground level, RenderEngine renderEngine, PhysicEngine physicEngine) {
        this.hero = hero;
        this.level = level;
        this.renderEngine = renderEngine;
        this.physicEngine = physicEngine;
    }


    public void triggerGameOver() {
        if (isGameOver) return; // Si le jeu est déjà terminé, ne fait rien
        isGameOver = true; // Marquer le jeu comme terminé

        if (Main.renderTimer != null) Main.renderTimer.stop();
        if (Main.gameTimer != null) Main.gameTimer.stop();
        if (Main.physicTimer != null) Main.physicTimer.stop();
        System.out.println("Game Over!");
        // Afficher l'interface "Game Over"
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(400, 300);
        gameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameOverFrame.setContentPane(new GameOver());
        gameOverFrame.setVisible(true);
    }

    @Override
    public void update() {
        hero.setDamage(level.getSolidSpriteList());
        hero.setPortail(level.getSolidSpriteList());
        hero.SetStun(level.getSolidSpriteList());
        if (hero.getLife() <= 0) {
            triggerGameOver();
        }
    }

        @Override
        public void keyTyped (KeyEvent e){

        }

        @Override
        public void keyPressed (KeyEvent e){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    hero.setDirection(Direction.NORTH);
                    break;
                case KeyEvent.VK_DOWN:
                    hero.setDirection(Direction.SOUTH);
                    break;
                case KeyEvent.VK_LEFT:
                    hero.setDirection(Direction.WEST);
                    break;
                case KeyEvent.VK_RIGHT:
                    hero.setDirection(Direction.EAST);
                    break;

            }
        }

        @Override
        public void keyReleased (KeyEvent e){

        }

        public void changeLevel (String levelPath){
            try {
                System.out.println("Changement vers le niveau : " + levelPath);

                // Chargez le nouveau niveau
                Playground newLevel = new Playground(levelPath);

                // Mettre à jour les composants
                renderEngine.renderList = new ArrayList<>();
                renderEngine.addToRenderList(newLevel.getSpriteList());
                renderEngine.addToRenderList(hero);

                physicEngine.setEnvironment(newLevel.getSolidSpriteList());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setHero (DynamicSprite hero){
            this.hero = hero;
        }


    }

