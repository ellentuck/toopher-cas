Toopher-CAS Overlay
===================

Version: 1.0

# Requirements:
 * CAS 3.5.2+
 * JDK 1.6+
 * Tomcat6+ or compatible servlet container

# Installation:

Build the WAR:

    mvn clean package  

After successful execution, deployable WAR file is located at `target/cas.war`

# Configuration

You MUST modify this overlay before deploying to production.  At a minimum:

## WEB-INF/deployerConfigContext.xml
Replace the default `SimpleTestUsernamePasswordAuthenticationHandler` bean with an AuthenticationHandler configured for your organization's primary Username/Password credentials

## WEB-INF/spring-configuration/levelOfAssurance.xml
Four distinct LevelOfAssurance beans are predefined:

* `usernamePasswordOnly` : Toopher authentication is bypassed.
* `toopherSimpleMFA` : Standard Toopher Authentication, which allows the user to authenticate using their smartphone and choose to automate matching authentication requests received when their smartphone is in the same location.
* `toopherNoAutomationMFA` : The user will always be required to authorize authentication requests on their Toopher device (automation is disabled).
* `toopherNoAutomationChallengeRequiredMFA` : In addition to disabling automation, the user will be required to correctly enter a PIN or pattern lock on their device before the authentication is granted.

Edit the `defaultServiceLevelOfAssurance` property of the `levelOfAssuranceManager` bean to set the default Assurance level required for all services not explicitly configured.

## WEB-INF/spring-configuration/servicesConfig.xml
Each CAS client service can have a separate "Level of Assurance" defined by the administrator, corresponding to whether or not that service requires Toopher Authentication for login.  Toopher-CAS includes the `SimplePrincipalLoaResolver` bean that lets administrators place this per-service configuration in an XML file in their WAR.

If you wish to define per-service MFA Assurance requirements elsewhere, you should implement the `com.toopher.integrations.cas.web.flow.PrincipalLoaResolver` interface to suit your needs, then set `levelOfAssuranceManager.serviceLoaResolver` to an instance of your implementation in levelOfAssurance.xml.

## WEB-INF/spring-configuration/toopherConfig.xml
CAS must have access to your Toopher Consumer Key and Secret in order to communicate with the Toopher API.  The provided configuration takes these values from environment variables.  If you are using Tomcat, you can set these values in /etc/sysconfig/tomcat6

    TOOPHER_CONSUMER_KEY=YOUR TOOPHER CONSUMER KEY
    TOOPHER_CONSUMER_SECRET=YOUR TOOPHER CONSUMER SECRET
    TOOPHER_BASE_URL=https://api.toopher.com/v1/

Your Toopher Consumer Secret should be kept in a file that can only be read by the user running the CAS server.

Additionally, if you wish to allow users to securely self-reset their account pairings, the Toopher-CAS overlay must be able to resolve a trusted email address for each user, in order to send them a self-service reset link.  The default configuration instantiates the included `SimpleUsernameToEmailMapper` bean, which is mainly useful for testing.  Production implementations will need to implement `com.toopher.integrations.cas.authentication.principal.UsernameToEmailMapper`, and set `toopherCAS.usernameToEmailMapper` to an instance of the new bean.

You can provision Toopher consumer credentials (key and secret) at the [Toopher Developer Portal](https://dev.toopher.com).

# Command-line tools
Toopher provides simple command-line tools to automate some common administrative tasks, available in the `tools` folder.  These scripts require python 2.6 or later, and depend on the `Toopher` package installed from [pipy](https://pypi.python.org/pypi/toopher).  Pip users can install the package and all dependencies by running `pip install toopher`

# Changelog
###1.1 - Mar 12, 2014

 * Update authentication webflow to better handle bad-path scenarios, such as when user denies a pairing or authentication on their mobile device
 * Add /tools directory to hold administrative command-line tools

###1.0 - Feb 28, 2014

 * initial release
