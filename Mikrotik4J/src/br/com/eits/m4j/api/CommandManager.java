package br.com.eits.m4j.api;

import br.com.eits.m4j.utils.MkLogger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class contains all methods to manipulate the channel to send <br/>
 * commands to the router and receive the data produced by the command
 *
 * @author janisk at <a href="http://wiki.mikrotik.com/wiki/API_in_Java">
 * Mikrotik Wiki</a>, improvements by Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 16/07/2012
 */
public class CommandManager {

    /**
     * The object to log events to console
     */
    private static final MkLogger LOG = new MkLogger(CommandManager.class);
    private DataInputStream consoleIn;
    private DataOutputStream consoleOut;

    /**
     * Initialize the command manager to send and receive commands
     *
     * @param consoleIn the result console to capture command results
     * @param consoleOut the console to send commands
     */
    public CommandManager(DataInputStream consoleIn, DataOutputStream consoleOut) {
        this.consoleIn = consoleIn;
        this.consoleOut = consoleOut;
    }

    /**
     * Parse the command to a byte array
     *
     * @param command the command to parse
     *
     * @return the command in bytes
     */
    private byte[] writeLen(String command) {

        Integer length;

        String ret = "";

        if (command.length() < 0x80) {
            length = command.length();
        } else if (command.length() < 0x4000) {
            length = Integer.reverseBytes(command.length() | 0x8000);
        } else if (command.length() < 0x20000) {
            length = Integer.reverseBytes(command.length() | 0xC00000);
        } else if (command.length() < 10000000) {
            length = Integer.reverseBytes(command.length() | 0xE0000000);
        } else {
            length = Integer.reverseBytes(command.length());
        }

        String hexSring = Integer.toHexString(length);

        if (hexSring.length() < 2) {
            return new byte[]{length.byteValue()};
        } else {
            for (int j = 0; j < hexSring.length(); j += 2) {
                ret += (char) Integer.parseInt(hexSring.substring(j, j + 2), 16) != 0
                        ? (char) Integer.parseInt(hexSring.substring(j, j + 2), 16) : "";
            }
        }

        return ret.getBytes();
    }

    /**
     * Send one command to the router
     *
     * @return the execution result
     *
     * @throws IOException if any problem occur
     */
    protected String sendCommand(String command) throws IOException {

        // prevent error if the command is null
        if (command == null) {
            return "";
        }

        byte[] ret = new byte[0];

        if (!command.contains("\n")) {
            byte[] b = writeLen(command);

            ret = new byte[b.length + command.length() + 1];

            int i;

            for (i = 0; i < b.length; i++) {
                ret[i] = b[i];
            }

            for (byte c : command.getBytes("US-ASCII")) {
                ret[i++] = c;
            }
        } else {
            String[] str = command.split("\n");

            int[] iTmp = new int[str.length];

            for (int a = 0; a < str.length; a++) {
                iTmp[a] = writeLen(str[a]).length + str[a].length();
            }

            int i = 1;

            for (int b : iTmp) {
                i += b;
            }

            ret = new byte[i];

            int counter = 0;

            for (int a = 0; a < str.length; a++) {

                byte[] b = writeLen(str[a]);

                int j;

                for (j = 0; j < b.length; j++) {
                    ret[counter++] = b[j];
                }

                for (byte c : str[a].getBytes("US-ASCII")) {
                    ret[counter++] = c;
                }
            }
        }

        consoleOut.write(ret);

        LOG.info("Command [" + command + "] sent successfully");

        return readConsoleData();
    }

    /**
     * Read the console and take the data produced by command
     *
     * @return the data produced by command
     *
     * @throws IOException if any problem occur
     */
    private String readConsoleData() throws IOException {

        String data = "";

        int result;

        while (!data.contains("!done")) {

            while ((result = consoleIn.read()) != 0) {

                int sk = 0;

                if (result != 0 && result > 0) {
                    if (result < 0x80) {
                        sk = result;
                    } else {
                        if (result < 0xC0) {
                            result = result << 8;
                            result += consoleIn.read();
                            sk = result ^ 0x8000;
                        } else {
                            if (result < 0xE0) {
                                for (int i = 0; i < 2; i++) {
                                    result = result << 8;
                                    result += consoleIn.read();
                                }
                                sk = result ^ 0xC00000;
                            } else {
                                if (result < 0xF0) {
                                    for (int i = 0; i < 3; i++) {
                                        result = result << 8;
                                        result += consoleIn.read();
                                    }
                                    sk = result ^ 0xE0000000;
                                } else {
                                    if (result < 0xF8) {
                                        result = 0;
                                        for (int i = 0; i < 5; i++) {
                                            result = result << 8;
                                            result += consoleIn.read();
                                        }
                                    }
                                }
                            }
                        }
                    }

                    data += "\n";

                    byte[] bb = new byte[sk];

                    result = consoleIn.read(bb, 0, sk);

                    if (result > 0) {
                        data += new String(bb);
                    }
                }
            }
        }

        return data;
    }
}
