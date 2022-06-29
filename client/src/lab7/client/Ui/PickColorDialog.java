package lab7.client.Ui;

import javax.swing.*;
import javax.swing.colorchooser.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class PickColorDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JColorChooser colorChooser;

    private Consumer<Color> onPick;

    public PickColorDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        for (AbstractColorChooserPanel panel : colorChooser.getChooserPanels()) {
            if (panel.getDisplayName().equals("Swatches")) {
                continue;
            }
            colorChooser.removeChooserPanel(panel);
        }

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        onPick.accept(colorChooser.getColor());
        setVisible(false);
    }

    private void onCancel() {
        setVisible(false);
    }

    public void setOnPick(Consumer<Color> onPick) {
        this.onPick = onPick;
    }
}
