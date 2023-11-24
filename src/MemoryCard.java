import javax.swing.JButton;

public class MemoryCard extends JButton {
    private int x_offset;
    private int y_offset;
    private String actual_value;
    private boolean is_flipped = false;

    MemoryCard(int x, int y, String actual_content) {
        super("?");
        x_offset = x;
        y_offset = y;
        actual_value = actual_content;
        addActionListener(e -> {
            if (!is_flipped) {
                try {
                    GameController.GetGame().CardChosen(getXOffset(), getYOffset(), this);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void FlipUp() {
        setText(actual_value);
        is_flipped = true;
    }

    public void FlipDown() {
        setText("?");
        is_flipped = false;
    }

    public void Remove() {
        setVisible(false);
    }

    public int getXOffset() {
        return x_offset;
    }

    public int getYOffset() {
        return y_offset;
    }

    public String getValue() {
        return actual_value;
    }

    public boolean IsPair(MemoryCard card) {
        return this.actual_value.equals(card.actual_value)
                && ((this.getXOffset() != card.getXOffset()) || (this.getYOffset() != card.getYOffset()));
    }
}
