package lab7.client.Ui;

import lab7.client.Locale.AbstractLocalizableFrame;
import lab7.client.Locale.Localizable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class LocalizationEventBus {
    private static final Set<Localizable> subscribers = Collections.synchronizedSet(new HashSet<>());

    public static void createEvent(Consumer<Localizable> event) {
        new Thread(() -> {
            for (Localizable c : subscribers) {
                event.accept(c);

                if (c instanceof AbstractLocalizableFrame) {
                    ((AbstractLocalizableFrame) c).pack();
                }
            }
        }).start();
    }

    public static void subscribe(Localizable c) {
        subscribers.add(c);
    }

    public static void unsubscribe(Localizable c) {
        subscribers.remove(c);
    }
}
