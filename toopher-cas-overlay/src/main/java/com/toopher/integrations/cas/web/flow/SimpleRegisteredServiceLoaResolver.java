package com.toopher.integrations.cas.web.flow;

import com.toopher.integrations.cas.authentication.LevelOfAssurance;

import java.util.Map;

import org.jasig.cas.authentication.principal.Principal;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;

/**
 * Looks up the RegisteredService corresponding to the incoming Service, and returns
 * a LevelOfAssurance based on the RegisteredService name. An example of using 
 * existing Service resolution logic in the ServicesManager. 
 *
 * @author Dan Ellentuck
 */
public class SimpleRegisteredServiceLoaResolver implements PrincipalLoaResolver {

    private Map<String, LevelOfAssurance> serviceNameToLoa;
    private ServicesManager servicesManager;


    @Override
    public LevelOfAssurance getLoa(Principal principal) {
    	String destination = principal.getId();
    	Service service = new SimpleWebApplicationServiceImpl(destination);
        RegisteredService registeredService = servicesManager.findServiceBy(service);
        return (registeredService!=null)
          ? serviceNameToLoa.get(registeredService.getName())
          : null;
    }

    public Map<String, LevelOfAssurance> getServiceNameToLoa() {
        return this.serviceNameToLoa;
    }

    public ServicesManager getServicesManager() {
        return this.servicesManager;
    }

    public void setServiceNameToLoa(Map<String, LevelOfAssurance> serviceNameToLoa) {
        this.serviceNameToLoa = serviceNameToLoa;
    }

    public void setServicesManager(ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }

}