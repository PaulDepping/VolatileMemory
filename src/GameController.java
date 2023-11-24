public class GameController implements Runnable {
    private static GUIController global_gui;
    private static GameController global_game;
    private Thread game_thread;
    private boolean is_running = true;
    public static final double tickrate = 1000.0 / 60.0; // Tickrate in 60 pro Sekunde

    /**
     * Gameloop für Spielelogik
     */
    @Override
    public void run() {
        long start_time, end_time, delta_time;
        while (is_running) {
            start_time = System.currentTimeMillis();
            // Game Logic
            if (state != Gamestate.PAUSED) {
                
            }
            //
            end_time = System.currentTimeMillis();
            delta_time = end_time - start_time;
            if (tickrate - delta_time > 0) {
                try {
                    Thread.sleep((long) (tickrate - delta_time));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        global_gui.Quit();
        /*
         * Code für Speichern etc.
         */
    }

    // in welchen Zuständen kann das Programm sein
    public enum Gamestate {
        MENU,
        RUNNING,
        PAUSED,
        GAMEOVER
    }

    private Gamestate state; // aktueller Zustand

    // welche Aktionen können auftreten
    public enum Action {
        QUIT,
        START,
        PAUSE_TOGGLE,
        MENU
    }

    GameController() {
        if (global_game != null) {
            throw new RuntimeException("Can not create multiple Game Controllers!");
        }
        global_game = this;
        global_gui = new GUIController(this);
        game_thread = new Thread(this);
        game_thread.start();

        // wechseln zum Menü
        ChangeState(Gamestate.MENU);
    }

    /**
     * der aktuelle Zustand des Spieles wird geändert
     * ruft changeWindowConfig() auf
     * 
     * @param newState
     */
    public void ChangeState(Gamestate newState) {
        state = newState;
        global_gui.ChangeWindowConfig(newState);
    }

    public static GUIController GetGUI() {
        return global_gui;
    }

    public static GameController GetGame() {
        return global_game;
    }

    /**
     * ein Event wird ausgelöst
     * 
     * @param a
     */
    public void FireEvent(Action a) {
        switch (a) {
            case START -> ChangeState(Gamestate.RUNNING);
            case QUIT -> is_running = false;
            case PAUSE_TOGGLE -> {
                if (state == Gamestate.PAUSED) {
                    ChangeState(Gamestate.RUNNING);
                } else if (state == Gamestate.RUNNING) {
                    ChangeState(Gamestate.PAUSED);
                }
            }
            case MENU -> ChangeState(Gamestate.MENU);
        }
    }

    private MemoryCard first = null;
    private MemoryCard second = null;

    private int player_score = 0;

    public void CardChosen(int x, int y, MemoryCard card) throws InterruptedException {
        if (first != null && second != null) {
            first.FlipDown();
            second.FlipDown();
            first = null;
            second = null;
        }
        card.FlipUp();
        GameController.GetGUI().Repaint();
        if (first == null) {
            first = card;
        } else if (second == null) {
            second = card;
            if (first.IsPair(card)) {
                player_score += 1;
                first.Remove();
                card.Remove();
            }
        } else {
            throw new RuntimeException("this should never happen!");
        }
    }
}
