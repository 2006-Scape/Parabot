package org.parabot.core.ui.components;

import org.parabot.Configuration;
import org.parabot.core.io.ProgressListener;
import org.parabot.core.ui.ServerSelector;
import org.parabot.core.ui.fonts.Fonts;
import org.parabot.core.ui.images.Images;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * An informative JPanel which tells the user what bot is doing
 *
 * @author Everel, EmmaStone
 */
public class VerboseLoader extends JPanel implements ProgressListener {
    private static final long serialVersionUID = 7412412644921803896L;
    private static VerboseLoader current;
    private final BufferedImage background;
    private final BufferedImage banner;
    private final ProgressBar progressBar;
    private FontMetrics fontMetrics;

    private VerboseLoader() {
        if (current != null) {
            throw new IllegalStateException("MainScreenComponent already made.");
        }
        current = this;
        this.background = Images.getResource("/storage/images/background.png");
        this.banner = Images.getResource("/storage/images/logo.png");
        this.progressBar = new ProgressBar(400, 20);
        setLayout(new GridBagLayout());
        setSize(775, 510);
        setPreferredSize(new Dimension(775, 510));
        setDoubleBuffered(true);
        setOpaque(false);
        ServerSelector.getInstance();
    }

    /**
     * Gets instance of this panel
     *
     * @return instance of this panel
     */
    public static VerboseLoader get() {
        return current == null ? new VerboseLoader() : current;
    }

    /**
     * Updates the status message and repaints the panel
     *
     * @param message
     */
    public static void setState(final String message) {
        current.repaint();
    }

    /**
     * Paints on this panel
     */
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.drawImage(background, 0, 0, null);
        float[] scales = { 1f, 1f, 1f, 0.9f };
        float[] offsets = new float[4];
        RescaleOp rop = new RescaleOp(scales, offsets, null);
        g.drawImage(banner, rop, 0, 0);

        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.WHITE);

        g.draw(new Line2D.Float(0, 1, this.getWidth(), 1)); //TOP
        g.draw(new Line2D.Float(0, 0, 0, 120)); //LEFT
        g.draw(new Line2D.Float(0, 120, this.getWidth(), 120)); //BOTTOM
        g.draw(new Line2D.Float(this.getWidth() - 6, 0, this.getWidth() - 6, 120)); //RIGHT

        g.setColor(Color.white);

        g.setFont(Fonts.getResource("leelawadee.ttf", 30));
        g.getFont().deriveFont(Font.BOLD);
        g.drawString(Configuration.BOT_TITLE, 20, 50);

        g.setFont(Fonts.getResource("leelawadee.ttf", 15));
        g.getFont().deriveFont(Font.ITALIC);
        g.drawString(Configuration.BOT_SLOGAN, 20, 85);

        if (fontMetrics == null) {
            fontMetrics = g.getFontMetrics();
        }

        g.setColor(Color.white);

        g.setFont(Fonts.getResource("leelawadee.ttf"));
        final String version = Configuration.BOT_VERSION.get();
        g.drawString(version,
                getWidth() - g.getFontMetrics().stringWidth(version) - 10,
                getHeight() - 12);
    }

    @Override
    public void onProgressUpdate(double value) {
        progressBar.setValue(value);
        this.repaint();
    }

    @Override
    public void updateDownloadSpeed(double mbPerSecond) {
        progressBar.setText(String.format("(%.2fMB/s)", mbPerSecond));
    }

    @Override
    public void updateMessage(String message) {
        VerboseLoader.setState(message);
    }

    @Override
    public void updateMessageAndProgress(String message, double progress) {
        VerboseLoader.setState(message);
        onProgressUpdate(progress);
    }

    @Override
    public double getCurrentProgress() {
        return progressBar.getValue();
    }
}