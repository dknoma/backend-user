package com.the.mild.project.server.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserJson {
    @JsonProperty("username") private String username;
    @JsonProperty("token") private String token;

    public UserJson(String key, String value) {
        this.username = key;
        this.token = value;
    }

    public UserJson setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserJson setToken(String token) {
        this.token = token;
        return this;
    }
}
