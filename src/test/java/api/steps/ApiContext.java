package api.steps;

public class ApiContext {
    private static String requestBody;
    private static String lastEmail;

    public static void setRequestBody(String body) { requestBody = body; }
    public static String getRequestBody() { return requestBody; }

    public static void setLastEmail(String email) { lastEmail = email; }
    public static String getLastEmail() { return lastEmail; }

    public static void reset() {
        requestBody = null;
        lastEmail = null;
    }
}