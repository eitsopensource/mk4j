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
package br.com.eits.m4j.bean;

import br.com.eits.m4j.bean.ann.MkMapping;

/**
 * The bean that maps the output of the command
 * <code>/ip/address/print</code>
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 19/07/2012
 */
public class NetworkInterface {

    @MkMapping(from = "id")
    private String id;
    @MkMapping(from = "address")
    private String address;
    @MkMapping(from = "network")
    private String network;
    @MkMapping(from = "interface")
    private String interfaceName;
    @MkMapping(from = "actual-interface")
    private String actualInterface;
    @MkMapping(from = "invalid")
    private boolean invalid;
    @MkMapping(from = "dynamic")
    private boolean dynamic;
    @MkMapping(from = "disabled")
    private boolean disabled;
    @MkMapping(from = "comment")
    private String comment;

    /**
     * Default constructor, do nothing...
     */
    public NetworkInterface() {
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * @return the interfaceName
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * @param interfaceName the interfaceName to set
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * @return the actualInterface
     */
    public String getActualInterface() {
        return actualInterface;
    }

    /**
     * @param actualInterface the actualInterface to set
     */
    public void setActualInterface(String actualInterface) {
        this.actualInterface = actualInterface;
    }

    /**
     * @return the invalid
     */
    public boolean isInvalid() {
        return invalid;
    }

    /**
     * @param invalid the invalid to set
     */
    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    /**
     * @return the dynamic
     */
    public boolean isDynamic() {
        return dynamic;
    }

    /**
     * @param dynamic the dynamic to set
     */
    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
