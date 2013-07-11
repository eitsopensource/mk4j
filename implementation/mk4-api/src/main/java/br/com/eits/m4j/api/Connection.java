/*
 * Mikrotik4J, Intregrate Java and Mikrotik RouterOS
 *
 * Copyright (c) 2012, Eits It Solutions and Arthur Gregório. 
 * or third-party contributors as indicated by the @author tags or express 
 * copyright attribution statements applied by the authors.  All third-party 
 * contributions are distributed under license by Eits It Solutions.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package br.com.eits.m4j.api;

import br.com.eits.m4j.utils.MkLogger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

/**
 * This class contains basically the connection methods and handling of <br/>
 * commands sent to the Mikrotik through the API
 *
 * @author janisk at <a href="http://wiki.mikrotik.com/wiki/API_in_Java">
 * Mikrotik Wiki</a>, improvements by Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 16/07/2012
 */
public class Connection extends Thread {

    /**
     * Object to log events on console
     */
    private static final MkLogger LOG = new MkLogger(Connection.class);
    
    private int port;
    private String address;
    
    /**
     * The connection tunnel
     */
    private Socket socket;
    
    /**
     * The connection flag
     */
    private boolean connected;
   
    /**
     * The command processor, this object handles the commands sent and the
     * results obtained
     */
    private CommandManager commandManager;

    /**
     * Creates a new connection based on a address and port provided by
     * programmer
     *
     * @param address the address of Mikrotik Router
     * @param port the port to connect on the Mikrotik Router
     */
    public Connection(String address, int port) {
        this.port = port;
        this.address = address;
    }

    /**
     * The state of connection
     *
     * @return if connection is established to router it
     * returns <code>true</code>
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Disconnect from Mikrotik Router
     *
     * @throws IOException if any problem occur
     */
    public void disconnect() throws IOException {
        socket.close();
    }

    /**
     * Method that performs login to Mikrotik allowing commands to be sent to
     * the Router
     *
     * @param name the login
     * @param password the password
     *
     * @return true if login successfully
     *
     * @throws IOException if any problem occur
     */
    public boolean login(String name, String password) throws IOException {

        String result = "a";

        result = sendCommand("/login");

        if (!result.contains("!trap") && result.length() > 4) {

            String[] tmp = result.trim().split("\n");

            if (tmp.length > 1) {

                tmp = tmp[1].split("=ret=");

                password = Hasher.hexStrToStr("00") + password + Hasher.hexStrToStr(tmp[tmp.length - 1]);

                try {
                    password = Hasher.hashMD5(password);
                } catch (NoSuchAlgorithmException ex) {
                    throw new IOException("Error when encrypt the user password", ex);
                }

                result = sendCommand("/login\n=name=" + name + "\n=response=00" + password);

                if (result.contains("!done")) {
                    if (!result.contains("!trap")) {
                        LOG.info("Connected successfully to: " + address);
                    }
                }
            }
        }

        return true;
    }

    /**
     * Send the commands to Mikrotik router and return his result as
     * <code>String</code>
     *
     * @param command the command to sent to router
     *
     * @return the data produced by command
     *
     * @throws IOException if any problem occur
     */
    public synchronized String sendCommand(String command) throws IOException {

        if (!connected) {
            try {
                wait();
            } catch (InterruptedException ex) {
                throw new IOException("Erro when wait to send command", ex);
            }
        }

        return commandManager.sendCommand(command);
    }

    /**
     * Indicates what state the connection is
     *
     * @param connected <code>true</code> if connected <code>false</code>
     * otherwise
     */
    private synchronized void setConnected(boolean connected) {
        this.connected = connected;

        if (connected) {
            notifyAll();
        }
    }

    /**
     * Run the process to connect to the router...
     */
    @Override
    public void run() {

        try {
            InetAddress host = Inet4Address.getByName(address);

            LOG.info("Trying to connect to: " + address);

            if (host.isReachable(5000)) {

                socket = new Socket(host, port);

                commandManager = new CommandManager(new DataInputStream(socket.getInputStream()),
                        new DataOutputStream(socket.getOutputStream()));

                setConnected(true);
            } else {
                throw new RuntimeException("The time to attempt to connect to host has exceeded");
            }
        } catch (IOException ex) {
            LOG.error("Error when attempting to establish a connection with the MikroTik", ex);
        }
    }
}