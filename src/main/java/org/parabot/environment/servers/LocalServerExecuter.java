package org.parabot.environment.servers;

import org.parabot.core.Core;
import org.parabot.core.classpath.ClassPath;
import org.parabot.environment.servers.executers.ServerExecuter;

/**
 * Loads locally stored server providers
 *
 * @author Everel
 */
@SuppressWarnings("Duplicates")
@Deprecated
public class LocalServerExecuter extends ServerExecuter {
    private final ServerProvider serverProvider;
    private final ClassPath classPath;
    private final String serverName;

    public LocalServerExecuter(ServerProvider serverProvider,
                               ClassPath classPath, final String serverName) {
        this.serverProvider = serverProvider;
        this.classPath = classPath;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        // add jar or directory to buildpath.
        if (this.classPath.isJar()) {
            Core.verbose("Adding server provider jar to buildpath: "
                    + this.classPath.lastParsed.toString());
            this.classPath.addToBuildPath();
        }
        // finalize
        super.finalize(this.serverProvider, this.serverName);
    }

}
