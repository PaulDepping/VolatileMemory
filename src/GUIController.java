import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUIController implements Runnable {

    private JFrame window; // Das Fenster in dem alles passiert

    // einzelne Fenster/Komponenten des Spiels
    private MainMenu main_menu;
    private Game_Panel game_panel;
    private PauseMenu pause_menu;

    private GameController game_ref; // Referenz zum Gamecontroller

    private boolean are_graphics_running = true; // läuft der Gameloop für repaint
    private double framerate = GameController.tickrate;

    void Repaint() {
        window.repaint();
    }

    GUIController(GameController gameController) {
        game_ref = gameController;

        window = new JFrame("Volatile Memory");
        // Beim Schließen des fensters wird die Anwendung beendet
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(null);
        // window.setSize(new Dimension(1920, 1080));
        window.setExtendedState(Frame.MAXIMIZED_BOTH); // Fenster füllt den Bildschirm

        // obere Fensterleiste ja/nein
        window.setUndecorated(false);

        // Das Fenster wird sichtbar
        window.setVisible(true);

        // Objekte der Pannels werden angelegt
        main_menu = new MainMenu();
        game_panel = new Game_Panel();
        pause_menu = new PauseMenu();

        // Panels werden hinzugefügt und skaliert
        Container cp = window.getContentPane();
        cp.add(main_menu);
        main_menu.setBounds(0, 0, window.getWidth(), window.getHeight());
        cp.add(game_panel);
        game_panel.setBounds(0, 0, window.getWidth(), window.getHeight());
        cp.add(pause_menu, 0);
        pause_menu.setBounds(0, 0, window.getWidth(), window.getHeight());

        // Tastatureingaben
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE -> game_ref.FireEvent(GameController.Action.PAUSE_TOGGLE);
                    default -> {
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        window.setFocusable(true);
        window.setAutoRequestFocus(true);
    }

    /**
     * Veränderung des dargestellten Fensters z.B Hauptmenü -> Spiel
     * zum wechsel changeState() des Game_Controllers verwenden
     * 
     * @param state
     */
    public void ChangeWindowConfig(GameController.Gamestate state) {
        if (state != GameController.Gamestate.PAUSED) {
            for (Component element : window.getContentPane().getComponents()) {
                element.setVisible(false);
            }
        }

        switch (state) {
            case MENU -> main_menu.setVisible(true);
            case RUNNING -> game_panel.setVisible(true);
            case PAUSED -> pause_menu.setVisible(true);
            case GAMEOVER -> throw new UnsupportedOperationException("Unimplemented case: " + state);
            default -> throw new IllegalArgumentException("Unexpected value: " + state);
        }
        window.repaint();
    }

    /**
     * Gameloop für Grafik (noch nicht verwendet)
     */
    @Override
    public void run() {
        // GameLoop für Grafik
        long startTime, endTime, deltaTime;
        while (are_graphics_running) {
            startTime = System.currentTimeMillis();
            window.repaint();
            endTime = System.currentTimeMillis();
            deltaTime = endTime - startTime;
            if (framerate - deltaTime > 0) {
                try {
                    Thread.sleep((long) (framerate - deltaTime));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * das Fenster wird geschlossen
     */
    public void Quit() {
        are_graphics_running = false;
        window.dispose();
    }
}
