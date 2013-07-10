/*************************************************************************
 *                                                                       *
 *  CESeCore: CE Security Core                                           *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/
package org.cesecore.certificates.ocsp.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.cesecore.certificates.ocsp.extension.OCSPExtension;
import org.cesecore.config.OcspConfiguration;

/**
 * Enum based singleton to contain a Map of OCSP extensions.
 * 
 * Note that this class is currently not thread safe, and use of the reloadCache()-method should be extremely limited. 
 * 
 * @version $Id$
 * 
 */
public enum OcspExtensionsCache {
    INSTANCE;

    private static final Logger log = Logger.getLogger(OcspExtensionsCache.class);

    private final ReentrantLock lock = new ReentrantLock(true);
    
    private Map<String, OCSPExtension> extensionMap;
    
    private OcspExtensionsCache() {    
        reloadCache();
    }

    /**
     * 
     * @return a map containing all loaded extensions. 
     */
    public Map<String, OCSPExtension> getExtensions() {
        while(lock.isLocked()) {
            //Kill time if cache is being reloaded. Should be quick, so this method can be blocking. 
        }
        return extensionMap;
    }
    
    /**
     * Method to manually reload the cache. 
     */
    public void reloadCache() {
        extensionMap = new HashMap<String, OCSPExtension>();
        Iterator<String> extensionClasses = OcspConfiguration.getExtensionClasses().iterator();
        Iterator<String> extensionOids = OcspConfiguration.getExtensionOids().iterator();

        lock.lock();
        try {
            while (extensionClasses.hasNext()) {
                String clazz = extensionClasses.next();
                String oid = extensionOids.next();
                if (oid.startsWith("*")) {
                    oid = oid.substring(1, oid.length());
                }
                OCSPExtension ext = null;
                try {
                    ext = (OCSPExtension) Class.forName(clazz).newInstance();
                } catch (Exception e) {
                    log.error("Can not create extension with class " + clazz, e);
                    continue;
                }
                extensionMap.put(oid, ext);
            }
        } finally {
            lock.unlock();
        }
    }

}
