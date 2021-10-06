package org.parabot.environment.api.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;

/**
 * @author JKetelaar
 */
public class PBPreferences {

    private final int scriptID;
    private Properties properties;

    public PBPreferences(int scriptID) {
        this.scriptID = scriptID;
    }

}
