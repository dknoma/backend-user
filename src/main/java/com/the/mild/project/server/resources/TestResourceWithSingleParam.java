package com.the.mild.project.server.resources;

import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.ParamTest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;

@Singleton
//@Path(PATH_TEST_RESOURCE_WITH_PARAM)
public class TestResourceWithSingleParam {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getId(@PathParam(PATH_PARAM_ID) String id) {
        final ParamTest test = new ParamTest(id);

        return JacksonHandler.stringify(test);
    }
}
