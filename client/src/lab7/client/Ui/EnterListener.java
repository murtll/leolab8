package lab7.client.Ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EnterListener implements KeyListener {

    private final Runnable onEnter;

    public EnterListener(Runnable onEnter) {
        this.onEnter = onEnter;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            onEnter.run();
        }
    }
}
