package org.parabot;

import org.parabot.core.ProjectProperties;
import org.parabot.environment.api.utils.Version;

/**
 * Holds some important constants
 *
 * @author Everel
 */
public class Configuration {
    public static final String serverName = "2006Scape";
    public static final String clientJar = "https://2006scape.org/Downloads/client.jar";
    public static final String hooksFile = "https://2006scape.org/Downloads/bot/2006Scape_hooks.xml";
    public static final String providerJar = "https://2006scape.org/Downloads/bot/Provider.jar";
    public static final String clientClass = "Game";
    public static final Double clientVersion = 1.0;

    public static final String BOT_TITLE = serverName + " Bot";
    public static final String BOT_SLOGAN = "The best RuneScape private server bot";
    public static final Version BOT_VERSION = ProjectProperties.getProjectVersion();

    public static final String WEBSITE_LINK = "https://2006Scape.org/";
    public static final String SDN_PAGE = "https://2006Scape.org/sdn/";
    public static final String GET_RANDOMS = "https://2006Scape.org/Downloads/bot/Randoms-0.26.1.jar";

}
