package mongo;

import com.the.mild.project.db.mongo.BaseDocument;
import com.the.mild.project.db.mongo.annotations.DocumentSerializable;
import com.the.mild.project.db.mongo.documents.UserCreateDocument;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DocumentUT {

    private static void checkInsertDocumentAnnotation(BaseDocument document) {
        final Class<?> dClass = document.getClass();

        assertTrue("The class %s is not annotated with DocumentSerializable.",
                dClass.isAnnotationPresent(DocumentSerializable.class));
    }

    @Test
    public void validUserCreateDocument() {
        final String username = "username";
        final String token = "token";
        final UserCreateDocument doc = new UserCreateDocument(username, token);

        checkInsertDocumentAnnotation(doc);
    }
}
