package org.parabot.api.io;

import org.json.simple.parser.JSONParser;
import org.parabot.api.output.Verboser;

import java.io.*;
import java.net.*;

/**
 * A WebUtil class fetches data from an URL
 *
 * @author Everel
 */
public class WebUtil {

    private static JSONParser jsonParser;

    private static String agent = "Mozilla/5.0 (Wind0ws NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    /**
     * Gets useragent
     *
     * @return useragent
     */
    public static String getUserAgent() {
        return agent;
    }

    /**
     * Agent to set at a URL connection
     *
     * @param userAgent
     */
    public static void setUserAgent(final String userAgent) {
        agent = userAgent;
    }

    /**
     * Fetches content of a page
     *
     * @param location
     * @return contents of page
     * @throws MalformedURLException
     */
    public static String getContents(final String location)
            throws MalformedURLException {
        return getContents(new URL(location));
    }

    public static String getContents(final String location, String parameters) throws MalformedURLException {
        return getContents(new URL(location), parameters);
    }

    /**
     * Get contents from URL
     *
     * @param url
     * @return page contents
     */
    public static String getContents(final URL url) {
        return getContents(getConnection(url));
    }

    public static String getContents(final URL url, final String parameters) {
        return getContents(getConnection(url), parameters);
    }

    /**
     * Gets contents from URLConnection
     *
     * @param urlConnection
     * @return page contents
     */
    public static String getContents(URLConnection urlConnection) {
        try {
            final BufferedReader in = getReader(urlConnection);
            final StringBuilder builder = new StringBuilder();
            String line;
            if (in != null) {
                while ((line = in.readLine()) != null) {
                    builder.append(line);
                }
                in.close();
            }
            return builder.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static String getContents(URLConnection urlConnection, String parameters) {
        try {
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(parameters);
            wr.flush();
            wr.close();

            final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            final StringBuilder builder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Gets buffered reader from string url
     *
     * @param url
     * @return bufferedreader
     */
    public static BufferedReader getReader(final String url) {
        try {
            return getReader(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets BufferedReader from URL
     *
     * @param url
     * @return BufferedReader from URL
     */
    public static BufferedReader getReader(final URL url) {
        return getReader(getConnection(url));
    }

    public static BufferedReader getReader(final URLConnection urlConnection) {
        try {
            return new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Gets inputstream from url
     *
     * @param url
     * @return inputstream from url
     */
    public static InputStream getInputStream(final URL url) {
        final URLConnection con = getConnection(url);
        try {
            return con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Opens a connection
     *
     * @param url
     * @return URLConnection to URL
     */
    public static URLConnection getConnection(final URL url) {
        try {
            final URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", agent);
            return con;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static URLConnection getConnection(final URL url, String parameters) {
        try {
            final URLConnection con = getConnection(url);
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(parameters);
            wr.flush();
            wr.close();

            return con;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static BufferedReader getReader(final URL url, String username, String password) {
        try {
            String data = URLEncoder.encode("username", "UTF-8") + "=" + username;
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + password;

            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static URLConnection getConnection(final URL url, String username, String password) {
        try {
            String data = URLEncoder.encode("username", "UTF-8") + "=" + username;
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + password;

            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            return connection;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Downloads a file on the internet
     *
     * @param url
     * @param destination
     */
    public static void downloadFile(final URL url, final File destination) {
        downloadFile(url, destination, null, null, null);
    }

    /**
     * Downloads a file on the internet
     *
     * @param url
     * @param destination
     * @param listener
     */
    public static void downloadFile(final URL url, final File destination,
                                    final ProgressListener listener) {
        downloadFile(url, destination, listener, null, null);
    }

    /**
     * Downloads a file on the internet
     *
     * @param url
     * @param destination
     * @param listener
     */
    public static void downloadFile(final URL url, final File destination,
                                    final ProgressListener listener, String username, String password) {
        try {
            final URLConnection connection;
            if (username == null || password == null) {
                connection = getConnection(url);
            } else {
                connection = getConnection(url, username, password);
            }
            final int size = connection.getContentLength();
            SizeInputStream sizeInputStream = new SizeInputStream(
                    connection.getInputStream(), size, listener);
            BufferedInputStream in = new BufferedInputStream(sizeInputStream);
            FileOutputStream fileOut = new FileOutputStream(destination);
            final double before = listener == null ? 0d : listener.getCurrentProgress();
            final long startTime = System.currentTimeMillis();
            int totalRead = 0;
            try {
                byte data[] = new byte[1024];
                int count;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    fileOut.write(data, 0, count);
                    totalRead += count;
                    if (listener != null) {
                        double progress = (((double)totalRead / (double)size) * 100d);
                        String rate = (totalRead / Math.max(1, ((System.currentTimeMillis()-startTime)/1000))) + "/s";
                        listener.updateMessageAndProgress(String.format("[%s] Downloading... %02f%% (%s of %s bytes) %s from %s to %s", WebUtil.class.getName(), progress, totalRead, size, rate, url.getPath(), destination.getAbsolutePath()),
                                progress);
                    }
                }
            } finally {
                in.close();
                fileOut.close();
            }
            if (listener != null) {
                listener.onProgressUpdate(before);
            }
            Verboser.verbose("[WebUtil] Downloaded " + totalRead + " bytes from " + url + " -> " + destination.getAbsolutePath());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Converts file format to url format
     *
     * @param file
     * @return url to file
     */
    public static URL toURL(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONParser getJsonParser() {
        if (jsonParser == null) {
            jsonParser = new JSONParser();
        }
        return jsonParser;
    }
}