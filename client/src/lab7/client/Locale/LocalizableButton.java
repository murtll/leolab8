package lab7.client.Locale;

import lab7.client.Ui.LocalizationEventBus;

import javax.swing.JButton;

public class LocalizableButton extends JButton implements Localizable {
    private String key;

    public LocalizableButton() {
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
