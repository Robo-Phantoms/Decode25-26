package org.firstinspires.ftc.teamcode.TestOpModes;


import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.impl.ServoEx;

public class ColorSensorTeleop extends NextFTCOpMode {
    private ServoEx servo = new ServoEx("bumper");
    private NormalizedColorSensor colorSensor;

    @Override
    public void onInit(){
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
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

        if (isPurple(redRatio, blueRatio, greenRatio)){
            servo.to(0.8);
        } else if (isGreen(redRatio, blueRatio, greenRatio, total)){
            servo.to(0);
        }
    }
    public boolean isPurple(double r, double b, double g){
        return r > 0.22 && b > 0.42 && g < 0.3;
    }
    public boolean isGreen(double r, double b, double g, double total){
        return g > 0.35 && g > r + 0.10 && g > b + 0.05 && total > 0.05;
    }

}
