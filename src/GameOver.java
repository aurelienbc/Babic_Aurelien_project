import javax.swing.*;
import java.awt.*;

public class GameOver extends JPanel {

    public GameOver() {
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        this.add(gameOverLabel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Quitter");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.addActionListener(e -> System.exit(0));
        this.add(exitButton, BorderLayout.SOUTH);
    }


}
