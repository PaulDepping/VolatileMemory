import javax.swing.*;
import java.awt.*;

public class PauseMenu extends JPanel {
    PauseMenu() {
        setLayout(null);
        setBackground(new Color(255, 183, 0, 170));

        JButton menu = new JButton();
        add(menu);
        menu.setText("Main Menu");
        menu.setBounds(200, 100, 150, 50);
        menu.setVisible(true);
        menu.addActionListener(e -> GameController.GetGame().FireEvent(GameController.Action.MENU));
    }
}
