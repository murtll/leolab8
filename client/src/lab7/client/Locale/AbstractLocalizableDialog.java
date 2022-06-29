package lab7.client.Locale;

import lab7.client.Ui.LocalizationEventBus;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractLocalizableDialog extends JDialog implements Localizable {
    private String key;

    protected AbstractLocalizableDialog(String key) {
        super();
        this.key = key;
        setTitle(Localizer.getLocalizedText(key));

        LocalizationEventBus.subscribe(this);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    @Override
    public void localize() {
        setTitle(Localizer.getLocalizedText(key));
    }

    @Override
    public void setKey(String key) {
        this.key = key;
        setTitle(Localizer.getLocalizedText(key));
    }

    @Override
    public void dispose() {
        LocalizationEventBus.unsubscribe(this);

        for (Component c : getContentPane().getComponents()) {
            if (c instanceof Localizable) {
                LocalizationEventBus.unsubscribe((Localizable) c);
            }
        }

        super.dispose();
    }

    public abstract void setOkCallback(Runnable r);
    public abstract void setCancelCallback(Runnable r);
}
