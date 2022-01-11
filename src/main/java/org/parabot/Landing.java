package org.parabot;

import org.parabot.core.Context;
import org.parabot.core.Core;
import org.parabot.core.Directories;
import org.parabot.core.network.NetworkInterface;
import org.parabot.core.ui.BotUI;
import org.parabot.core.ui.utils.UILog;

import java.io.File;

import javax.swing.UIManager;

/**
 * @author Everel, JKetelaar, Matt, Dane
 * @version 2.8.1
 * @see <a href="https://www.parabot.org">Homepage</a>
 */
public final class Landing {

    public static void main(String... args) {
//        Thread.setDefaultUncaughtExceptionHandler(new FileExceptionHandler(ExceptionHandler.ExceptionType.CLIENT));

        if (Context.getJavaVersion() >= 9) {
            UILog.log("Parabot", "Parabot doesn't support Java 9+ currently. Please downgrade to Java 8 to ensure Parabot is working correctly.");
        }

        if (!System.getProperty("os.arch").contains("64")) {
            UILog.log("Parabot", "You are not running a 64-bit version of Java, this might cause the client to lag or crash unexpectedly.\r\n" +
                    "It is recommended to upgrade to a 64-bit version.");
        }

        parseArgs(args);

        Directories.validate();

        try {
            Core.verbose("Setting look and feel: "
                    + UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        Core.verbose("Starting " + Configuration.serverName + "...");
        new BotUI();

    }

    private static void parseArgs(String... args) {
        for (int i = 0; i < args.length; i++) {
            final String arg = args[i].toLowerCase();
            switch (arg.toLowerCase()) {
                case "-createdirs":
                    Directories.validate();
                    System.out.println("Directories created, you can now run " + Configuration.BOT_TITLE + ".");
                    System.exit(0);
                    break;
                case "-dump":
                case "-debug":
                    Core.setDump(true);
                    break;
                case "-v":
                case "-verbose":
                    Core.setVerbose(true);
                    break;
                case "-scriptsbin":
                    Directories.setScriptCompiledDirectory(new File(args[++i]));
                    break;
                case "-c":
                case "-clearcache":
                    Directories.clearCache();
                    break;
                case "-mac":
                    byte[] mac = new byte[6];
                    String str = args[++i];
                    if (str.equalsIgnoreCase("random")) {
                        new java.util.Random().nextBytes(mac);
                    } else {
                        i--;
                        for (int j = 0; j < 6; j++) {
                            mac[j] = (byte) Integer.parseInt(args[++i], 16); // parses a hex
                            // number
                        }
                    }
                    NetworkInterface.setMac(mac);
                    break;
                case "-no_sec":
                    Core.disableSec();
                    break;
                default:
                    System.err.printf("Unknown argument given: %s%n", arg.toLowerCase());
                    break;
            }
        }
    }
}
