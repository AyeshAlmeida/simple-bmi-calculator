package hms.cpaas.simple.bmi.calculator.api;

public class SessionData {
    private String sessionId;
    private String sourceAddress;
    private float weight;
    private float height;
    private int level;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SessionData{");
        sb.append("sessionId='").append(sessionId).append('\'');
        sb.append(", sourceAddress='").append(sourceAddress).append('\'');
        sb.append(", weight=").append(weight);
        sb.append(", height=").append(height);
        sb.append(", level=").append(level);
        sb.append('}');
        return sb.toString();
    }
}
