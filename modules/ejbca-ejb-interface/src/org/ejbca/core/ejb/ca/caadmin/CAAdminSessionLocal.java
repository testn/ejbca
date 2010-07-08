/*************************************************************************
 *                                                                       *
 *  EJBCA: The OpenSource Certificate Authority                          *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/

package org.ejbca.core.ejb.ca.caadmin;

import javax.ejb.EJBException;
import javax.ejb.Local;

import org.ejbca.core.model.ca.catoken.ICAToken;
import org.ejbca.core.model.log.Admin;

@Local
public interface CAAdminSessionLocal extends CAAdminSession {

    /**
     * Removes the catoken keystore from the database and sets its status to
     * {@link ICAToken#STATUS_OFFLINE}. The signature algorithm, encryption
     * algorithm, key algorithm and other properties are not removed so that the
     * keystore can later by restored by using
     * {@link CAAdminSessionBean#restoreCAKeyStore(Admin, String, byte[], String, String, String, String)}
     * .
     * 
     * FIXME: The sister method of this in the remote interface does not throw
     * EJBException. Is this correct?
     * 
     * @param admin
     *            Administrator
     * @param caname
     *            Name (human readable) of CA for which the keystore should be
     *            removed
     * @throws EJBException
     *             in case if the catoken is not a soft catoken
     * @see CAAdminSessionBean#exportCAKeyStore(Admin, String, String, String,
     *      String, String)
     */
    public void removeCAKeyStore(Admin admin, String caname) throws javax.ejb.EJBException;

    /**
     * Restores the keys for the catoken from a keystore.
     * 
     * @param admin
     *            Administrator
     * @param caname
     *            Name (human readable) of the CA for which the keystore should
     *            be restored
     * @param p12file
     *            The keystore to read keys from
     * @param keystorepass
     *            Password for the keystore
     * @param privkeypass
     *            Password for the private key
     * @param privateSignatureKeyAlias
     *            Alias of the signature key in the keystore
     * @param privateEncryptionKeyAlias
     *            Alias of the encryption key in the keystore
     * @throws EJBException
     *             in case of the catoken is not a soft catoken or if the ca
     *             already has an active catoken or if any of the aliases can
     *             not be found or if the keystore does not contain the right
     *             private key
     */
    public void restoreCAKeyStore(Admin admin, String caname, byte[] p12file, String keystorepass, String privkeypass, String privateSignatureKeyAlias,
            String privateEncryptionKeyAlias) throws javax.ejb.EJBException;

    /**
     * Used by healthcheck. Validate that CAs are online and optionally performs
     * a signature test.
     * 
     * @return an error message or an empty String if all are ok.
     */
    public String healthCheck();
}
