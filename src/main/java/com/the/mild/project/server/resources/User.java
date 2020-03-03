package com.the.mild.project.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoDatabase;
import com.the.mild.project.MongoDatabaseType;
import com.the.mild.project.db.mongo.DocumentEntry;
import com.the.mild.project.db.mongo.MongoDatabaseFactory;
import com.the.mild.project.db.mongo.MongoDocumentHandler;
import com.the.mild.project.db.mongo.documents.UserCreateDocument;
import com.the.mild.project.db.mongo.exceptions.CollectionNotFoundException;
import com.the.mild.project.db.mongo.exceptions.DocumentSerializationException;
import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.UserJson;
import org.bson.Document;

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

import static com.the.mild.project.MongoCollections.USER;
import static com.the.mild.project.ResourceConfig.CommonPaths.PATH_CREATE;
import static com.the.mild.project.ResourceConfig.PATH_USER_RESOURCE;
import static com.the.mild.project.ResourceConfig.PathParams.ID;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_ID;
import static com.the.mild.project.ResourceConfig.PathParams.PATH_PARAM_UPDATE_BY_ID;
import static com.the.mild.project.server.Main.MONGO_DB_FACTORY;

/**
 * Root resource
 */
@Singleton
@Path(PATH_USER_RESOURCE)
public class User {
    private static final MongoDatabaseFactory MONGO_DATABASE_FACTORY;
    private static final MongoDocumentHandler MONGO_HANDLER_USERS;

    private static final String EMPTY_JSON = "{}";

    static {
        MONGO_DATABASE_FACTORY = MONGO_DB_FACTORY.orElse(null);

        assert MONGO_DATABASE_FACTORY != null;

        final MongoDatabase developTestDb = MONGO_DATABASE_FACTORY.getDatabase(MongoDatabaseType.USERS);
        MONGO_HANDLER_USERS = new MongoDocumentHandler(developTestDb);
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client.
     */
    @GET
    @Path(PATH_PARAM_ID)
    @Produces(MediaType.APPLICATION_JSON)
    public String getUser(@PathParam(ID) String id) {
        String resp = EMPTY_JSON;
        try {
            final Document document = MONGO_HANDLER_USERS.tryFindById(USER.collectionName(), id);
            resp = document.toJson();
        } catch(CollectionNotFoundException e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     */
    @POST
    @Path(PATH_CREATE)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String userBody) {
        try {
            UserJson user = JacksonHandler.unmarshal(userBody, UserJson.class);
            final UserCreateDocument document = new UserCreateDocument(user);

            final boolean exists = MONGO_HANDLER_USERS.insertIfAbsent(document, c -> c.addEntry("username",
                                                                                                user.getUsername()));
            final Response resp;
            if(exists) {
                resp = Response.status(409, "Username already exists").build();
            } else {
                resp = Response.noContent().build();
            }
            return resp;
        } catch(JsonProcessingException | DocumentSerializationException | CollectionNotFoundException e) {
            e.printStackTrace();
        }
        return Response.status(404).build();
    }

    /**
     *
     * @param id format = _id: ObjectId("5e4c9832489d4d3766a257f4")
     * @param userBody
     */
    @PUT
    @Path(PATH_PARAM_UPDATE_BY_ID)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam(ID) String id, String userBody) {
        try {
            System.out.println(userBody);
            Document updateDoc = JacksonHandler.stringToDocument(userBody);

            MONGO_HANDLER_USERS.tryUpdateOneById(USER.collectionName(), id, updateDoc);
        } catch(JsonProcessingException | CollectionNotFoundException e) {
            e.printStackTrace();
        }
    }
}
