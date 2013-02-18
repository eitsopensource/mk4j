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

import java.util.ArrayList;
import java.util.List;

/**
 * Class that encapsulates a command to be sent to the router, it<br/> is
 * possible to send a command with parameters more easily by using <br/> the
 * aiding methods
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 23/07/2012
 */
public class MkCommand {

    private String baseCommand;
    private List<Parameter> parameters;

    /**
     * The base command to send for the router
     *
     * For example:
     *
     * To retrieve the ip address list send:
     *
     * <code>/ip/address/print</code> as base command
     *
     * if you want to put any parameter in this command, use the
     * {@link MkCommand#appendParameter(String, String)} method
     *
     * @param baseCommand the base command to concatenates with the parameters
     */
    public MkCommand(String baseCommand) {
        this.baseCommand = baseCommand;
        this.parameters = new ArrayList<>();
    }

    /**
     * Append a new parameter in the command string
     *
     * @param parameter the parameter
     * @param value the parameter value
     */
    public void appendParameter(String parameter, String value) {
        parameters.add(new Parameter(parameter, value, ParameterType.COMMON));
    }

    /**
     * Add a where condition to the command string
     * 
     * @param parameter the parameter key
     * @param value the value for the given key
     */
    public void appendWhere(String parameter, String value) {
        parameters.add(new Parameter(parameter, value, ParameterType.WHERE));
    }
    
    /**
     * Concatenates the parameters of a command string to be sent to the router
     *
     * @return the command string
     */
    public String asCommandString() {

        StringBuilder command = new StringBuilder(baseCommand);

        for (int i = 0; i < parameters.size(); i++) {

            Parameter p = parameters.get(i);

            if (p.getType() == ParameterType.COMMON) {
                command.append("\n=");
                command.append(p.getParameter());
                command.append("=");
                command.append(p.getValue());
            } else {
                command.append("\n?");
                command.append(p.getParameter());
                command.append("=");
                command.append(p.getValue());
            }            
        }

        return command.toString();
    }

    /**
     * The encapsulation for parameters in the command
     *
     * @author Arthur Gregorio
     *
     * @since 1.0
     * @version 1.0, 23/07/2012
     */
    private class Parameter {

        private String parameter;
        private String value;
        private ParameterType type;        

        /**
         * Constructor thats recei one parameter and one value for<br/> this
         * parameter (key=value)
         *
         * @param parameter the parameter (key)
         * @param value the value for the parametert (value)
         */
        public Parameter(String parameter, String value, ParameterType type) {
            this.parameter = parameter;
            this.value = value;
            this.type = type;
        }

        /**
         * @return the value of value
         */
        public String getValue() {
            return value;
        }

        /**
         * @return the parameter
         */
        public String getParameter() {
            return parameter;
        }

        /**
         * @return the parameter type
         */
        public ParameterType getType() {
            return type;
        }
    }
    
    /**
     * Mark if the command is where or common command
     */
    private enum ParameterType {
        WHERE, COMMON;
    }
}
