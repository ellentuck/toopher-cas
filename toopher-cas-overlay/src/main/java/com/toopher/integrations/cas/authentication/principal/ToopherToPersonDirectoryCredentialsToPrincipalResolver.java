/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.toopher.integrations.cas.authentication.principal;

import org.apache.log4j.Logger;
import org.jasig.cas.authentication.principal.AbstractPersonDirectoryCredentialsToPrincipalResolver;
import org.jasig.cas.authentication.principal.Credentials;

/**
 * Implementation of CredentialsToPrincipalResolver for ToopherCredentials 
 * that resolves the Principal from a PersonDirectory repository.
 * <p>
 * Implementation extracts the username from the provided Credentials, for use
 * by the superclass in constructing a new SimplePrincipal with attributes 
 * retrieved from PersonDirectory.  
 * </p>
 * 
 * @author Scott Battaglia
 * @author Dan Ellentuck
 * @see org.jasig.cas.authentication.principal.SimplePrincipal
 */
public class ToopherToPersonDirectoryCredentialsToPrincipalResolver extends
    AbstractPersonDirectoryCredentialsToPrincipalResolver 
  {

    private static Logger logger = Logger.getLogger("edu.columbia.cas.authentication.principal");
    private static String DEBUG_CLASS = "ToopherToPersonDirectoryCredentialsToPrincipalResolver";

    protected String extractPrincipalId(final Credentials credentials) 
    {
        logger.debug(DEBUG_CLASS + ": extractPrincipalId");
        final ToopherCredentials toopherCredentials = (ToopherCredentials)credentials;
        String principalId = toopherCredentials.getUsername();
        logger.debug(DEBUG_CLASS + ": got PrincipalId from ToopherCredentials: " + principalId);
        return principalId;
    }

    /**
     * Returns true if Credentials are ToopherCredentials.
     */
    public boolean supports(Credentials credentials) 
    {
        return (credentials.getClass() == ToopherCredentials.class);
    }
}
