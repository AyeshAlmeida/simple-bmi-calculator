package hms.cpaas.simple.bmi.calculator.api;

public class USSDSendConfirmationObject {
    private String version;
    private String requestId;
    private String timeStamp;
    private String statusCode;
    private String statusDetail;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("USSDSendConfirmationObject{");
        sb.append("version='").append(version).append('\'');
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", timeStamp='").append(timeStamp).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append(", statusDetail='").append(statusDetail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
