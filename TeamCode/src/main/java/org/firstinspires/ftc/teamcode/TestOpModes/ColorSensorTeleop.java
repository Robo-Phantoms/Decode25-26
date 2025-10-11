package org.firstinspires.ftc.teamcode.TestOpModes;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.impl.ServoEx;
@TeleOp(name = "ColorSensorTeleop")
public class ColorSensorTeleop extends NextFTCOpMode {
    private ServoEx servo = new ServoEx("bumper");
    private NormalizedColorSensor colorSensor;

    @Override
    public void onInit(){
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        colorSensor.setGain(7.0f);
    }
    public void onUpdate(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        double red = colors.red;
        double green = colors.green;
        double blue = colors.blue;

        double total = red + green + blue;
        double redRatio = red / total;
        double greenRatio = green / total;
        double blueRatio = blue / total;

        String detectedColor = detectPurpleOrGreen(redRatio, greenRatio, blueRatio, total);

        telemetry.addData("Raw Red", "%.3f", red);
        telemetry.addData("Raw Green", "%.3f", green);
        telemetry.addData("Raw Blue", "%.3f", blue);
        telemetry.addLine("---------------------------");
        telemetry.addData("Red Ratio", "%.2f", redRatio);
        telemetry.addData("Green Ratio", "%.2f", greenRatio);
        telemetry.addData("Blue Ratio", "%.2f", blueRatio);
        telemetry.addData("Brightness", "%.2f", total);
        telemetry.addLine("---------------------------");
        telemetry.addData("Detected Color", detectedColor);
        telemetry.update();

        if (isPurple(redRatio, greenRatio, blueRatio)){
            servo.setPosition(0.8);
        } else if (isGreen(redRatio, greenRatio, blueRatio, total)){
            servo.setPosition(0);
        }
    }
    public boolean isPurple(double r, double g, double b){
        return r > 0.22 && b > 0.42 && g < 0.3;
    }
    public boolean isGreen(double r, double g, double b, double total){
        return g > 0.35 && g > r + 0.10 && g > b + 0.05 && total > 0.05;
    }
    private String detectPurpleOrGreen(double r, double g, double b, double total) {
        if (r > 0.22 && b > 0.42 && g < 0.3) {
            return "Purple";
        }

        if (g > 0.35 && g > r + 0.10 && g > b + 0.05 && total > 0.05) {
            return "Dark Green";
        }

        return "Unknown";
    }
}
