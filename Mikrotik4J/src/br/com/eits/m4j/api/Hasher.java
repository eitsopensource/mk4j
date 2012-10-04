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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The password encryptor used by the API
 *
 * @author janisk at <a href="http://wiki.mikrotik.com/wiki/API_in_Java">
 * Mikrotik Wiki</a>, improvements by Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 16/07/2012
 */
public class Hasher {

    /**
     * Encrypts the password passed in an MD5 hash
     *
     * @param password
     *
     * @return the string hash
     *
     * @throws NoSuchAlgorithmException if the algorithm is not supported by the
     * system
     */
    static public String hashMD5(String password) throws NoSuchAlgorithmException {

        MessageDigest algorithm = MessageDigest.getInstance("MD5");

        byte[] defaultBytes = new byte[password.length()];

        for (int i = 0; i < password.length(); i++) {
            defaultBytes[i] = (byte) (0xFF & password.charAt(i));
        }

        algorithm.reset();
        algorithm.update(defaultBytes);

        byte messageDigest[] = algorithm.digest();

        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < messageDigest.length; i++) {

            String hex = Integer.toHexString(0xFF & messageDigest[i]);

            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * Converts hex value string to normal string for use with RouterOS API
     *
     * @param hex hex string to convert to
     * @return converted string
     */
    static public String hexStrToStr(String hex) {

        String ret = "";

        for (int i = 0; i < hex.length(); i += 2) {
            ret += (char) Integer.parseInt(hex.substring(i, i + 2), 16);
        }

        return ret;
    }
}