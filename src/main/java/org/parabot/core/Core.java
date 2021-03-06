package org.parabot.core;

import org.parabot.core.ui.utils.UILog;

/**
 * The core of parabot
 *
 * @author Everel, JKetelaar
 */
@SuppressWarnings("Duplicates")
public class Core {
    private static boolean verbose;
    private static boolean dump;
    private static boolean secure = true;

    /**
     * Enables dump mode
     */
    public static void setDump(final boolean dump) {
        Core.dump = dump;
    }

    public static void disableSec() {
        UILog.log(
                "Security Warning",
                "Disabling the securty manager is ill advised.\n"
                        + " Only do so if the client fails to load, or functions incorrectly (freezes,crashes, etc.)\n"
                        + "The security manager protects you from malicous code within the client, without it you are exposed!\n"
                        + "\nPlease contact Parabot staff to resolve whatever problem you are having!");
        Core.secure = false;
    }

    public static boolean isSecure() {
        return secure;
    }

    /**
     * @return if the client is in verbose mode.
     */
    public static boolean inVerboseMode() {
        return verbose;
    }

    /**
     * @return if parabot should dump injected jar
     */
    public static boolean shouldDump() {
        return dump;
    }

    /**
     * Sets verbose mode
     *
     * @param verbose - enabled
     */
    public static void setVerbose(final boolean verbose) {
        Core.verbose = verbose;
    }

    public static void verbose(final String line) {
        if (verbose) {
            System.out.println(line);
        }
    }

    /**
     * Prints a debug line to the Logger and System PrintStream
     * Meant for the debug adapter within hooks
     */
    public static void debug(final String line) {
        System.out.println(line);
    }

    /**
     * Method that removes the cache contents after 3 days
     */
    private static void validateCache() {
        // Already handled by Directories initiating
        // Method will be used once BDN V3 has a functionality for this
    }
}