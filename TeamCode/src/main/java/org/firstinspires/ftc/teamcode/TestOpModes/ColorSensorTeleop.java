package org.firstinspires.ftc.teamcode.TestOpModes;


import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.impl.FeedbackCRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
@TeleOp(name = "ColorSensorTeleop")
public class ColorSensorTeleop extends NextFTCOpMode {
    private ServoEx servo = new ServoEx("bumper");
    private NormalizedColorSensor colorSensor;
    private float[] hsv = new float[3];

    public enum Colors{
        GREEN,
        PURPLE,
        NONE
    }
    @Override
    public void onInit(){
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        colorSensor.setGain(7.0f);
    }
    public void onUpdate() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        Color.RGBToHSV(
                (int) (colors.red*255),
                (int) (colors.green*255),
                (int) (colors.blue*255),
                hsv
        );
        Colors color = updateHSV();
        if (color == Colors.PURPLE) {
            servo.to(0.5);
            telemetry.addLine("Purple detected");
        } else if (color == Colors.GREEN) {
            servo.to(1.0);
            telemetry.addLine("Green detected");
        } else {
            telemetry.addLine("No color detected");
        }

    }

    public Colors updateHSV(){
        float hue = hsv[0];

        if (hue >=260 && hue<=300){
            return Colors.PURPLE;
        }
        else if (hue >= 90 && hue <= 150){
            return Colors.GREEN;
        }
        return Colors.NONE;
    }

}
