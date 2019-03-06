package hms.cpaas.simple.bmi.calculator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class BMIService {
    @Value("${bmi.base.sms.content}")
    private String bmiSMSBaseContent;
    @Value("${bmi.base.sms.health.tip.goodBmi}")
    private String healthyBmi;
    @Value("${bmi.base.sms.health.tip.lowBmi}")
    private String lowBmi;
    @Value("${bmi.base.sms.health.tip.highBmi}")
    private String highBmi;

    public String getBmiBasedSmsContent(float bmi) {
        if (bmi > 25) {
            return MessageFormat.format(bmiSMSBaseContent, bmi, highBmi);
        } else if (bmi >= 18.5) {
            return MessageFormat.format(bmiSMSBaseContent, bmi, healthyBmi);
        } else {
            return MessageFormat.format(bmiSMSBaseContent, bmi, lowBmi);
        }
    }
}
