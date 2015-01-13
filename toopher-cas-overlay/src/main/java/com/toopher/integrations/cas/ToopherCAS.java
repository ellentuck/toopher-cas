// Generated by delombok at Mon Mar 10 08:01:35 CST 2014
package com.toopher.integrations.cas;

import com.toopher.integrations.cas.authentication.LevelOfAssurance;
import org.apache.log4j.Logger;
import com.toopher.api.ToopherIframe;
import com.toopher.integrations.cas.authentication.principal.UsernameToEmailMapper;

public class ToopherCAS {

    private Logger logger = Logger.getLogger(ToopherCAS.class);
    private ToopherConfig toopherConfig;
    private UsernameToEmailMapper usernameToEmailMapper;


    public String pairIframeUrl(String username) {
        return ToopherIframe.pairIframeUrl(username, getEmailForUsername(username), toopherConfig.getiframeTtl(), toopherConfig.getApiUrl(), toopherConfig.getConsumerKey(), toopherConfig.getConsumerSecret());
    }

    public String authIframeUrl(String username, Long levelOfAssuranceValue, String loginTicketId) {
        logger.debug("ToopherCAS: authIframeUrl(" + username + ", " + String.valueOf(levelOfAssuranceValue) + ")");
        LevelOfAssurance loa = new LevelOfAssurance(levelOfAssuranceValue);
        boolean isAutomationAllowed = !loa.isDisallowAutomationRequired();
        return ToopherIframe.authIframeUrl(username, getEmailForUsername(username), "Log In", isAutomationAllowed, loa.isChallengeRequired(), loginTicketId, "metadata", toopherConfig.getiframeTtl(), toopherConfig.getApiUrl(), toopherConfig.getConsumerKey(), toopherConfig.getConsumerSecret());
    }

    private String getEmailForUsername(String username) {
        if (usernameToEmailMapper != null) {
            return usernameToEmailMapper.getEmailForUsername(username);
        } else {
            // default - use username for reset email field.  Obviously this will only work
            // if users login using their email addresses
            return username;
        }
    }

    @java.lang.SuppressWarnings("all")
    public ToopherConfig getToopherConfig() {
        return this.toopherConfig;
    }

    @java.lang.SuppressWarnings("all")
    public void setToopherConfig(final ToopherConfig toopherConfig) {
        this.toopherConfig = toopherConfig;
    }

    @java.lang.SuppressWarnings("all")
    public UsernameToEmailMapper getUsernameToEmailMapper() {
        return this.usernameToEmailMapper;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsernameToEmailMapper(final UsernameToEmailMapper usernameToEmailMapper) {
        this.usernameToEmailMapper = usernameToEmailMapper;
    }

}