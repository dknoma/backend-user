package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.TodoJson;
import com.the.mild.project.server.jackson.UserJson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JacksonUT {

    @Test
    public void unmarshal() throws JsonProcessingException {
        final String username = "user";
        final String message = "message";
        final boolean completed = false;
        final String original = String.format("{\"username\":\"%s\",\"message\":\"%s\",\"completed\":%b}",
                                              username, message, completed);

        TodoJson expected = new TodoJson(username, message, completed);
        TodoJson result = JacksonHandler.unmarshal(original, TodoJson.class);


        assertEquals(String.format("\"%s\" did not match expected \"%s\"", result, expected), expected, result);
    }

    @Test
    public void jacksonTest() {
        final String username = "username";
        final String token = "token";
        final String expected = String.format("{\"key\":\"%s\",\"value\":\"%s\"}", username, token);

        final UserJson test = new UserJson(username, token);
        final String result = JacksonHandler.stringify(test);

        assertEquals(String.format("\"%s\" did not match expected \"%s\"", result, expected), result, expected);
    }
}
