import javax.swing.*;
import java.awt.*;

public class Game_Panel extends JPanel {
    Game_Panel() {
        setLayout(null);
        setDoubleBuffered(true);
        setBackground(new Color(70, 70, 70));
        // setBounds(1920, 1080);
        // JLabel label = new JLabel("Game (ESC: Pause Menü)", SwingConstants.CENTER);
        // add(label);
        // label.setBounds(0, 50, 1920, 50);
        // label.setVisible(true);
        // label.setForeground(Color.black);
        setFocusable(false);

        // Create memory card jbuttons
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                JButton memory_card = new MemoryCard(i, j, Integer.toString((i * 6 + j) % 18));
                memory_card.setBounds(((1920 / 2) - 3 * 100 - 2 * 50 - 25) + (j * 150), 100 + (i * 150), 100, 100);
                add(memory_card);
                memory_card.setVisible(true);
            }
        }
    }
}
