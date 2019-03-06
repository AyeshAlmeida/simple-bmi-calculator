package hms.cpaas.simple.bmi.calculator.api;

public class USSDReceiveResponseObject {
    private String statusCode;
    private String statusDetail;

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
        final StringBuilder sb = new StringBuilder("USSDReceiveResponseObject{");
        sb.append("statusCode='").append(statusCode).append('\'');
        sb.append(", statusDetail='").append(statusDetail).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
