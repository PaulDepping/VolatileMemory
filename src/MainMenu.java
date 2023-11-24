import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {
    MainMenu() {
        setLayout(null);
        JButton start = new JButton();
        JButton quit = new JButton();
        JLabel label = new JLabel("Volatile Memory Main Menu", SwingConstants.CENTER);
        label.setBounds(0, 0, 1920, 50);

        label.setVisible(true);
        label.setForeground(Color.black);

        add(label);

        setFocusable(true);
        start.setBounds(400, 900, 150, 50);
        start.setText("Start Game!");
        start.setVisible(true);
        start.addActionListener(e -> GameController.GetGame().FireEvent(GameController.Action.START));

        quit.setBounds(1920 - 400 - 150, 900, 150, 50);
        quit.setText("Quit to Main Menu");
        quit.setVisible(true);
        quit.addActionListener(e -> GameController.GetGame().FireEvent(GameController.Action.QUIT));

        add(start);
        add(quit);
    }
}
