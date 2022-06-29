package lab7.client.Ui;

import com.apple.eawt.Application;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MainUI {
    public static void main(String[] args) {
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            try {
                Application.getApplication().setDockIconImage(ImageIO.read(new File("resources/car.png")));
            } catch (IOException e) {
                System.err.println("Can't get icon, fallback to default");
            }
        }

        ServerConfigForm serverConfigForm = new ServerConfigForm();
        serverConfigForm.setVisible(true);
    }
}
