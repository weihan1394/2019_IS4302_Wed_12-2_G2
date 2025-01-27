/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.rest.DistributorResource.class);
        resources.add(ws.rest.FarmerResource.class);
        resources.add(ws.rest.GenericResource.class);
        resources.add(ws.rest.ProducerResource.class);
        resources.add(ws.rest.RetailerResource.class);
    }
}
