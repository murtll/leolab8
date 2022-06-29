package lab7.client.Locale;

import lab7.client.Ui.LocalizationEventBus;

import javax.swing.*;
import java.util.Locale;

public class LocaleBox extends JComboBox<Locale> implements Localizable {

    public void init() {
        removeAllItems();

        for(Locale l: Localizer.getAvailableLocales()) {
            addItem(l);
        }

        setSelectedItem(Localizer.getLocale());
        LocalizationEventBus.subscribe(this);

        addActionListener(e -> {
            if (getSelectedItem() == null || getSelectedItem().equals(Localizer.getLocale())) {
                return;
            }

            Localizer.setLocale((Locale) getSelectedItem());
            LocalizationEventBus.createEvent(Localizable::localize);
        });
    }

    @Override
    public void setKey(String key) {
        throw new RuntimeException("Not implemented - no keys");
    }

    @Override
    public void localize() {
        if (getSelectedItem() != null && getSelectedItem().equals(Localizer.getLocale())) {
            return;
        }

        setSelectedItem(Localizer.getLocale());
    }
}