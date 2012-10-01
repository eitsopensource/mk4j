package br.com.eits.m4j.api;

import br.com.eits.m4j.bean.HotspotActiveUser;
import br.com.eits.m4j.bean.HotspotHost;
import br.com.eits.m4j.bean.MkCommand;
import br.com.eits.m4j.bean.NetworkInterface;
import br.com.eits.m4j.utils.MkLogger;
import br.com.eits.m4j.utils.MkParser;
import java.io.IOException;
import java.util.List;

/**
 * The main class of the API, it should be handled by sending and receiving data
 * from the Router
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 17/07/2012
 */
public class Mikrotik {

    /**
     * Object to log events on console
     */
    private static final MkLogger LOG = new MkLogger(Mikrotik.class);
    /**
     * The connection object
     */
    private static Connection connection;

    /**
     * Creates a new connection and request the login to make API ready to send
     * <br/> commands to router
     *
     * <b>observation</b>: this method uses the default port 8728. If you need
     * to <br/> use another port, use another method of connection
     *
     * @param address the address to connect
     * @param login the login of admin user
     * @param password the password of user
     */
    public static void connect(String address, String login, String password) {
        connect(address, 8728, login, password);
    }

    /**
     * Creates a new connection and request the login to make API ready to send
     * <br/> commands to router
     *
     * @param address the address to connect
     * @param port the port to request connection
     * @param login the login of admin user
     * @param password the password of user
     */
    public static void connect(String address, int port, String login, String password) {
        LOG.info("Connecting to: " + address);

        connection = new Connection(address, port);
        connection.start();

        try {
            connection.login(login, password);
        } catch (IOException ex) {
            LOG.error("Cannot login on " + address, ex);
        }
    }

    /**
     * Request disconnection from server
     *
     * @throws IOException if any problem occur
     */
    public static void disconnect() throws IOException {
        LOG.info("Disconnecting...");
        connection.disconnect();
    }

    /**
     * Send one command to the router through the API
     *
     * @param command the command
     *
     * @return the result produced by command
     *
     * @throws IOException if any problem occur
     */
    public static String runCommand(String command) throws IOException {

        if (connection == null) {
            throw new RuntimeException("Connect firts!");
        }

        return connection.sendCommand(command);
    }

    /**
     * Send one command to the router through the API
     *
     * @param command object of {@link MkCommand} thats contain the base command
     * and his parameters
     *
     * @return the result produced by command
     *
     * @throws IOException if any problem occur
     */
    public static String runCommand(MkCommand command) throws IOException {

        if (connection == null) {
            throw new RuntimeException("Connect firts!");
        }

        return connection.sendCommand(command.asCommandString());
    }

    /**
     * Lists the active users in the hotspot
     *
     * @return {@link HotspotActiveUser} list containing data from the active
     * users in the hotspot
     *
     * @throws Exception if any problem occur
     */
    public static List<HotspotActiveUser> listHotspotActiveUsers() throws Exception {

        List<HotspotActiveUser> activeUsers = MkParser.asListOfObjects(
                HotspotActiveUser.class, runCommand("/ip/hotspot/active/print"));

        return activeUsers;
    }

    /**
     * Lists the active hosts in the hotspot
     *
     * @return {@link HotspotHost} list containing data from the hotspot hosts
     *
     * @throws Exception if any problem occur
     */
    public static List<HotspotHost> listHotspotHosts() throws Exception {

        List<HotspotHost> hotspotHosts = MkParser.asListOfObjects(
                HotspotHost.class, runCommand("/ip/hotspot/host/print"));

        return hotspotHosts;
    }

    /**
     * List the network interfaces in the router
     *
     * @return {@link NetworkInterface} list containing data from the router
     * interfaces
     *
     * @throws Exception if any problem occur
     */
    public static List<NetworkInterface> listNetworkInterfaces() throws Exception {

        List<NetworkInterface> networkInterfaces = MkParser.asListOfObjects(
                NetworkInterface.class, runCommand("/ip/address/print"));

        return networkInterfaces;
    }
}
