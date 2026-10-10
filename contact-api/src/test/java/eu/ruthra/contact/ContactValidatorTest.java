package eu.ruthra.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ContactValidatorTest {

    private static ContactHandler.ContactRequest request(String name, String email, String message) {
        return new ContactHandler.ContactRequest(name, email, message);
    }

    @Test
    void acceptsValidRequest() {
        assertNull(ContactValidator.validate(request("La Roman", "la.roman@lala.fr", "Bonjour!")));
    }

    @Test
    void rejectsNullRequest() {
        assertNotNull(ContactValidator.validate(null));
    }

    @Test
    void rejectsBlankName() {
        assertEquals("Name is required", ContactValidator.validate(request("   ", "a@b.cz", "Hi")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab.c", "@a.b", "a@b.", "a@b@c.d", "a b@c.d", "a@.cz"})
    void rejectsInvalidEmails(String email) {
        assertEquals("Invalid email", ContactValidator.validate(request("Artur", email, "Hi")));
    }
}