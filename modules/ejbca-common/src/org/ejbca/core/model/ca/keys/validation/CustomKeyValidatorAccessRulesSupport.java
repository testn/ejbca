/*************************************************************************
 *                                                                       *
 *  EJBCA Community: The OpenSource Certificate Authority                *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/

package org.ejbca.core.model.ca.keys.validation;

import org.cesecore.authentication.tokens.AuthenticationToken;

/**
 * A key validator that implements this interface contains additional access rules.
 * 
 * @version $Id: CustomKeyValidatorAccessRulesSupport.java 22117 2017-03-01 12:12:00Z anjakobs $
 */
public interface CustomKeyValidatorAccessRulesSupport {

    /** @return true if administrator is authorized to view this key validator. */
    public boolean isAuthorizedToKeyValidator(AuthenticationToken authenticationToken);
}