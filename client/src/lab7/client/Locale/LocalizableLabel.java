package lab7.client.Locale;

import lab7.client.Ui.LocalizationEventBus;

import javax.swing.JLabel;

public class LocalizableLabel extends JLabel implements Localizable {
    private String key;

    public LocalizableLabel() {
        super();
        LocalizationEventBus.subscribe(this);
    }

    @Override
    public void setKey(String key) {
        this.key = key;
        localize();
    }

    @Override
    public void localize() {
        this.setText(Localizer.getLocalizedText(key));
    }
}
