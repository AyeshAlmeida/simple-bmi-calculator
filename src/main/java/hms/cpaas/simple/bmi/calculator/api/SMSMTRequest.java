package hms.cpaas.simple.bmi.calculator.api;

import java.util.List;

public class SMSMTRequest {
    private String applicationId;
    private String password;
    private String message;
    private String version;
    private List<String> destinationAddresses;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SMSMTRequest{");
        sb.append("applicationId='").append(applicationId).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", destinationAddresses=").append(destinationAddresses);
        sb.append('}');
        return sb.toString();
    }
}
