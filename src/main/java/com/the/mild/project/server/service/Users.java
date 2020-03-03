package com.the.mild.project.server.service;

import static com.the.mild.project.MongoCollections.USER;
import static com.the.mild.project.server.Main.MONGO_DB_FACTORY;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CONFLICT;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoDatabase;
import com.the.mild.project.MongoDatabaseType;
import com.the.mild.project.db.mongo.MongoDatabaseFactory;
import com.the.mild.project.db.mongo.MongoDocumentHandler;
import com.the.mild.project.db.mongo.documents.UserCreateDocument;
import com.the.mild.project.db.mongo.exceptions.CollectionNotFoundException;
import com.the.mild.project.db.mongo.exceptions.DocumentSerializationException;
import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.UserJson;

@Singleton
public final class Users {
    private static final MongoDatabaseFactory MONGO_DATABASE_FACTORY;
    private static final MongoDocumentHandler MONGO_HANDLER_USERS;

    private static final String EMPTY_JSON = "{}";

    static {
        MONGO_DATABASE_FACTORY = MONGO_DB_FACTORY.orElse(null);

        assert MONGO_DATABASE_FACTORY != null;

        final MongoDatabase developTestDb = MONGO_DATABASE_FACTORY.getDatabase(MongoDatabaseType.USERS);
        MONGO_HANDLER_USERS = new MongoDocumentHandler(developTestDb);
    }

    public static Response createUser(String userBody) {
        try {
            UserJson user = JacksonHandler.unmarshal(userBody, UserJson.class);
            final UserCreateDocument document = new UserCreateDocument(user);

            final boolean exists = MONGO_HANDLER_USERS.insertIfAbsent(document, c -> c.addEntry("username",
                                                                                                user.getUsername()));
            final Response resp;
            if(exists) {
                resp = Response.status(CONFLICT.getStatusCode(), "Username already exists").build();
            } else {
                resp = Response.noContent().build();
            }
            return resp;
        } catch(JsonProcessingException | DocumentSerializationException | CollectionNotFoundException e) {
            e.printStackTrace();
        }
        return Response.status(BAD_REQUEST).build();
    }

    public static String getUser(String id) {
        String resp = EMPTY_JSON;
        try {
            final Document document = MONGO_HANDLER_USERS.tryFindById(USER.collectionName(), id);
            resp = document.toJson();
        } catch(CollectionNotFoundException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public static Response updateUser(String id, String userBody) {
        Response resp = Response.status(BAD_REQUEST).build();
        try {
            System.out.println(userBody);
            Document updateDoc = JacksonHandler.stringToDocument(userBody);

            MONGO_HANDLER_USERS.tryUpdateOneById(USER.collectionName(), id, updateDoc);
            resp = Response.noContent().build();
        } catch(JsonProcessingException | CollectionNotFoundException e) {
            e.printStackTrace();
        }
        return resp;
    }

    private Users() {
        // Utility
    }
}
