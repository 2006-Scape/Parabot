package org.parabot.core.parsers;

import org.parabot.core.Core;
import org.parabot.core.desc.ServerDescription;
import org.parabot.environment.servers.executers.LocalPublicServerExecuter;
import org.parabot.environment.servers.executers.ServerExecuter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Abstract class for parsing server providers
 *
 * @author Everel
 */
public abstract class ServerParser {
    public static final Map<ServerDescription, ServerExecuter> SERVER_CACHE = new HashMap<ServerDescription, ServerExecuter>();

    public static final ServerDescription[] getDescriptions() {
        SERVER_CACHE.clear();
        ServerDescription desc = new ServerDescription();
        SERVER_CACHE.put(desc, new LocalPublicServerExecuter());

        Map<ServerDescription, ServerExecuter> SORTED_SERVER_CACHE = new TreeMap<ServerDescription, ServerExecuter>(SERVER_CACHE);

        return SORTED_SERVER_CACHE.keySet().toArray(new ServerDescription[SORTED_SERVER_CACHE.size()]);
    }

    public abstract void execute();

}