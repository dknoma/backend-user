package com.the.mild.project.server.controllers;

import static com.the.mild.project.ResourceConfig.CommonPaths.PATH_CREATE;
import static com.the.mild.project.ResourceConfig.PATH_USER_RESOURCE;
import static com.the.mild.project.ResourceConfig.PathParams.ID;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_UPDATE_BY_ID;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.the.mild.project.server.service.Users;

/**
 * Root resource
 */
@Singleton
@Path(PATH_USER_RESOURCE)
public class User {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client.
     */
    @GET
    @Path(PATH_PARAM_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam(ID) String id) {
        return Users.getUser(id);
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     */
    @POST
    @Path(PATH_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String userBody) {
        return Users.createUser(userBody);
    }

    /**
     *
     * @param id format = _id: ObjectId("5e4c9832489d4d3766a257f4")
     * @param userBody
     */
    @PUT
    @Path(PATH_PARAM_UPDATE_BY_ID)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam(ID) String id, String userBody) {
        return Users.updateUser(id, userBody);
    }
}
