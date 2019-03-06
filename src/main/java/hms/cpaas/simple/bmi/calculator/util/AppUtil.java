package hms.cpaas.simple.bmi.calculator.util;

public final class AppUtil {
    public static float calculateBmi(float weightInKgs, float heightInMeters) {
        return weightInKgs / (heightInMeters * heightInMeters);
    }
}
