package lab7.client.Locale;

import lab7.client.Locale.LocaleBox;
import lab7.client.Locale.Localizable;
import lab7.client.Locale.Localizer;
import lab7.client.Ui.LocalizationEventBus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class AbstractLocalizableFrame extends JFrame implements Localizable {
    private String key;

    protected AbstractLocalizableFrame(String key) {
        super(Localizer.getLocalizedText(key));
        this.key = key;

        LocalizationEventBus.subscribe(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            setIconImage(ImageIO.read(new File("resources/car.png")));
        } catch (IOException e) {
            System.err.println("Can't get icon, fallback to default");
        }
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
}
