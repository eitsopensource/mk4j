package br.com.eits.m4j.bean;

import br.com.eits.m4j.bean.ann.MkMapping;

/**
 * the bean that maps the output of the command
 * <code>/ip/hotspot/active/print</code>
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 16/07/2012
 */
public class HotspotActiveUser {

    @MkMapping(from = "id")
    private String id;
    @MkMapping(from = "server")
    private String server;
    @MkMapping(from = "user")
    private String user;
    @MkMapping(from = "address")
    private String ipAddress;
    @MkMapping(from = "mac-address")
    private String macAddress;
    @MkMapping(from = "login-by")
    private String loginBy;
    @MkMapping(from = "uptime")
    private String uptime;
    @MkMapping(from = "session-time-left")
    private String sessionTimeLeft;
    @MkMapping(from = "radius")
    private boolean radius;

    /**
     * Default constructor, do nothing...
     */
    public HotspotActiveUser() {
        // do nothing
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
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
     * @return the macAddres
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * @param macAddress the macAddres to set
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * @return the loginBy
     */
    public String getLoginBy() {
        return loginBy;
    }

    /**
     * @param loginBy the loginBy to set
     */
    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
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
     * @return the sessionTimeLeft
     */
    public String getSessionTimeLeft() {
        return sessionTimeLeft;
    }

    /**
     * @param sessionTimeLeft the sessionTimeLeft to set
     */
    public void setSessionTimeLeft(String sessionTimeLeft) {
        this.sessionTimeLeft = sessionTimeLeft;
    }

    /**
     * @return the radius
     */
    public boolean isRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(boolean radius) {
        this.radius = radius;
    }
}
