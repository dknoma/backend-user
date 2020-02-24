package jersey;

import com.the.mild.project.server.Main;
import com.the.mild.project.server.jackson.JacksonHandler;
import com.the.mild.project.server.jackson.UserJson;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.the.mild.project.ResourceConfig.PATH_USER_RESOURCE;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static org.junit.Assert.assertEquals;

public class PathsIT {
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();

        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the user was created
     */
    @Test
    public void pathUserCreate() {
        final UserJson test = new UserJson("username", "token");

        String json = JacksonHandler.stringify(test);

        final Response post = target.path(PATH_USER_RESOURCE)
                                    .request(MediaType.APPLICATION_JSON_TYPE)
                                    .post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE));

        final int status = post.getStatus();
        System.out.printf("status=%s\n", status);
        assertEquals(status, HTTP_NO_CONTENT);
    }
}
