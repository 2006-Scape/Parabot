package org.parabot.core.ui.components;

import org.parabot.core.desc.ServerDescription;
import org.parabot.environment.Environment;

import javax.swing.*;

/**
 * A neat looking server component
 *
 * @author Everel
 */
public class ServerComponent extends JPanel {
    private static final long serialVersionUID = 1L;
    private final String name;
    public ServerDescription desc;

    public ServerComponent(final ServerDescription desc) {
        this.desc = desc;
        setLayout(null);
        this.name = desc.getServerName().replaceAll(" ", "");
    }

    @Override
    public String getName() {
        return name;
    }


    public void load(final ServerDescription desc) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Environment.load(desc);

            }
        }).start();
    }
}
