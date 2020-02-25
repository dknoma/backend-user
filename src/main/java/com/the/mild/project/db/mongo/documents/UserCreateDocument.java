package com.the.mild.project.db.mongo.documents;

import com.the.mild.project.db.mongo.InsertDocument;
import com.the.mild.project.db.mongo.InsertDocumentEntry;
import com.the.mild.project.db.mongo.annotations.DocumentEntryKeys;
import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.server.jackson.UserJson;
import org.bson.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.the.mild.project.MongoCollections.USER_NAME;

@DocumentSerializable(collectionName = USER_NAME)
public class UserCreateDocument extends InsertDocument {

    public UserCreateDocument() {
        super();
    }

    public UserCreateDocument(UserJson json) {
        super();
        putData(getEntryClass(), json.getUsername(), json.getPassword());
    }

    public UserCreateDocument(String username, String password) {
        super();
        putData(getEntryClass(), username, password);
    }

    /**
     * Put all required data into the document.
     * @param username;
     * @param password;
     * @return TestDocument
     */
    public UserCreateDocument putAll(String username, String password) {
        putData(getEntryClass(), username, password);
        return this;
    }

    @Override
    public String toString() {
        return String.format("User_Create_Document=[%s]", super.document.toString());
    }

    /**
     * Returns the document.
     * @return document
     */
    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public Class<Entry> getEntryClass() {
        return Entry.class;
    }

    @DocumentEntryKeys
    public enum Entry implements InsertDocumentEntry {
        USERNAME("username"),
        PASSWORD("password");

        private static final Map<String, Entry> ENTRY_BY_NAME = new HashMap<>();

        static {
            Arrays.asList(Entry.values())
                  .forEach(e -> ENTRY_BY_NAME.put(e.key(), e));
        }

        final private String key;

        Entry(String key) {
            this.key = key;
        }

        @Override
        public String key() {
            return key;
        }

        public static Entry getByKeyName(String key) {
            return ENTRY_BY_NAME.getOrDefault(key, null);
        }
    }
}
