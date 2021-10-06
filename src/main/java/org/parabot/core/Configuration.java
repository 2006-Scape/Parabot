package org.parabot.core;

import org.parabot.environment.api.utils.Version;

/**
 * Holds some important constants
 *
 * @author Everel
 */
public class Configuration extends org.parabot.api.Configuration {

    public static final String WEBSITE_LINK = "https://2006Scape.org/";
    public static final String SDN_PAGE = "https://2006Scape.org/sdn/";
    public static final String GET_RANDOMS = "https://2006Scape.org/Downloads/bot/Randoms-0.26.1.jar";

    public static final String GET_SERVER_PROVIDER_TYPE = "http://v3.bdn.parabot.org/api/bot/server/type?server=%s";
    public static final String SERVER_PROVIDER_INFO = "http://v3.bdn.parabot.org/api/bot/list/%s?latest=true";
    public static final String GET_SERVER_SETTINGS = "http://bdn.parabot.org/api/get.php?action=get_settings";
    public static final String API_DOWNLOAD_BOT = "http://v3.bdn.parabot.org/api/bot/download/client";
    public static final String DOWNLOAD_BOT = "http://bdn.parabot.org/versions/";
    public static final String DATA_API = "http://bdn.parabot.org/api/v2/data/";
    public static final String ITEM_API = DATA_API + "items/";

    public static final Version BOT_VERSION = ProjectProperties.getProjectVersion();
}
