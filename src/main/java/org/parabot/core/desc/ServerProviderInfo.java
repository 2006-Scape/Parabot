package org.parabot.core.desc;

import org.json.simple.JSONObject;
import org.parabot.core.Configuration;
import org.parabot.environment.api.utils.WebUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.CRC32;

/**
 * Gets the information for the selected server provider
 *
 * @author Paradox, Everel
 */
public class ServerProviderInfo {

    public URL getClient() {
        try {
            return new URL(Configuration.clientJar);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public URL getExtendedHookFile() {
        try {
            return new URL(Configuration.hooksFile /*+ "&extended=true"*/);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return getHookFile();
        }
    }

    public URL getHookFile() {
        try {
            return new URL(Configuration.hooksFile);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClientClass() {
        return Configuration.clientClass;
    }

    public String getServerName() {
        return Configuration.serverName;
    }

    public long getCRC32() {
        return Long.parseLong(String.valueOf(getCRC32(Configuration.serverName, "provider")));
    }

    public long getClientCRC32() {
        return Long.parseLong(String.valueOf(getCRC32(Configuration.serverName, "client")));
    }

    /**
     * Gets the URL to download the Randoms JAR from.
     *
     * @return The provided URL in the server config JSON (denoted by 'randoms:') or, fallback to the default BDN URL.
     */
    public URL getRandoms() {
        try {
            String randomsUrl = Configuration.GET_RANDOMS;
            return new URL(randomsUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the current provider version
     *
     * @return provider version
     */
    public String getProviderVersion() {
        String providerType = WebUtil.getJsonValue(String.format(Configuration.GET_SERVER_PROVIDER_TYPE, Configuration.serverName), "type");
        if (providerType != null) {
            String providerInfo = String.format(Configuration.SERVER_PROVIDER_INFO, providerType);
            return WebUtil.getJsonValue(providerInfo, "version");
        }

        return null;
    }

    private long getCRC32(String name, String type) {
        CRC32 crc = new CRC32();
        name += "-" + type;
        crc.update(name.getBytes());
        return crc.getValue();
    }
}
