package hms.cpaas.simple.bmi.calculator.api;

public class USSDIndicationObject {
    private String version;
    private String applicationId;
    private String message;
    private String requestId;
    private String sessionId;
    private String ussdOperation;
    private String sourceAddress;
    private String vlrAddress;
    private String encoding;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUssdOperation() {
        return ussdOperation;
    }

    public void setUssdOperation(String ussdOperation) {
        this.ussdOperation = ussdOperation;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getVlrAddress() {
        return vlrAddress;
    }

    public void setVlrAddress(String vlrAddress) {
        this.vlrAddress = vlrAddress;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("USSDIndicationObject{");
        sb.append("version='").append(version).append('\'');
        sb.append(", applicationId='").append(applicationId).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", sessionId='").append(sessionId).append('\'');
        sb.append(", ussdOperation='").append(ussdOperation).append('\'');
        sb.append(", sourceAddress='").append(sourceAddress).append('\'');
        sb.append(", vlrAddress='").append(vlrAddress).append('\'');
        sb.append(", encoding='").append(encoding).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
