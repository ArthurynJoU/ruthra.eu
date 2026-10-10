package eu.ruthra.contact;

final class ContactValidator {

    private ContactValidator() {}

    static String validate(ContactHandler.ContactRequest req) {
        if (req == null) {
            return "Request body is required";
        }
        if (isBlank(req.name())) {
            return "Name is required";
        }
        if (isBlank(req.email())) {
            return "Email is required";
        }
        if (isBlank(req.message())) {
            return "Message is required";
        }

        String name = req.name().trim();
        String email = req.email().trim();
        String message = req.message().trim();

        if (name.length() > 100) {
            return "Name is too long (max 100 characters)";
        }
        if (name.contains("\n") || name.contains("\r")) {
            return "Name must be on one line";
        }
        if (email.length() > 200) {
            return "Email is too long (max 200 characters)";
        }
        if (message.length() > 5000) {
            return "Message is too long (max 5000 characters)";
        }

        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        if (at <= 0
                || at != email.lastIndexOf('@')
                || dot < at + 2
                || dot == email.length() - 1
                || email.contains(" ")) {
            return "Invalid email";
        }

        return null;
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}