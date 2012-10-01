package br.com.eits.m4j.bean;

import br.com.eits.m4j.bean.ann.MkMapping;

/**
 * The bean that maps the output of the command
 * <code>/ip/hotspot/host/print</code>
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 16/07/2012
 */
public class HotspotHost {

    @MkMapping(from = "mac-address")
    private String macAddress;
    @MkMapping(from = "address")
    private String ipAddress;
    @MkMapping(from = "to-address")
    private String toAddress;
    @MkMapping(from = "server")
    private String server;
    @MkMapping(from = "uptime")
    private String uptime;
    @MkMapping(from = "found-by")
    private String foundBy;
    @MkMapping(from = "authorized")
    private boolean autorized;
    @MkMapping(from = "bypassed")
    private boolean bypassed;

    /**
     * Default constructor, do nothing...
     */
    public HotspotHost() {
        // do nothing
    }

    /**
     * @return the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return the toAddress
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * @param toAddress the toAddress to set
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the uptime
     */
    public String getUptime() {
        return uptime;
    }

    /**
     * @param uptime the uptime to set
     */
    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    /**
     * @return the foundBy
     */
    public String getFoundBy() {
        return foundBy;
    }

    /**
     * @param foundBy the foundBy to set
     */
    public void setFoundBy(String foundBy) {
        this.foundBy = foundBy;
    }

    /**
     * @return the autorized
     */
    public boolean isAutorized() {
        return autorized;
    }

    /**
     * @param autorized the autorized to set
     */
    public void setAutorized(boolean autorized) {
        this.autorized = autorized;
    }

    /**
     * @return the bypassed
     */
    public boolean isBypassed() {
        return bypassed;
    }

    /**
     * @param bypassed the bypassed to set
     */
    public void setBypassed(boolean bypassed) {
        this.bypassed = bypassed;
    }
}
