import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import me.friwi.jcefmaven.impl.progress.ConsoleProgressHandler;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        //Create a new CefAppBuilder instance
        CefAppBuilder builder = new CefAppBuilder();

//Configure the builder instance
        builder.setInstallDir(new File("jcef-bundle")); //Default
        builder.setProgressHandler(new ConsoleProgressHandler()); //Default
        builder.addJcefArgs("--disable-gpu"); //Just an example
        builder.getCefSettings().windowless_rendering_enabled = true; //Default - select OSR mode

//Set an app handler. Do not use CefApp.addAppHandler(...), it will break your code on MacOSX!
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {

        });

//Build a CefApp instance using the configuration above
        JFrame frame = new JFrame();
        try {
            CefApp app = builder.build();
            CefClient client = app.createClient();
            CefBrowser browser = client.createBrowser("https://google.com", true, true);
            frame.add(browser.getUIComponent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedPlatformException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (CefInitializationException e) {
            throw new RuntimeException(e);
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);


    }
}
