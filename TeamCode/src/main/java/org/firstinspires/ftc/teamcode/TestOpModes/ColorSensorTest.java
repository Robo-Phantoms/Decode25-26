package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@TeleOp(name = "Color Sensor Test (Purple vs Green)", group = "Test")
public class ColorSensorTest extends OpMode {

    private NormalizedColorSensor colorSensor;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        // Slightly increase gain for better dark color detection
        colorSensor.setGain(7.0f); // a bit higher for darker surfaces

        telemetry.addLine("Initialized REV Color Sensor V3!");
        telemetry.addLine("Detecting Purple vs Dark Green...");
        telemetry.update();
    }

    @Override
    public void loop() {
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
    }

    private String detectPurpleOrGreen(double r, double g, double b, double total) {
        // Keep your working purple rule exactly the same
        if (r > 0.22 && b > 0.42 && g < 0.3) {
            return "Purple";
        }

        // Improved Dark Green rule:
        // - Green slightly dominant (g > r + 0.10, g > b + 0.05)
        // - Allow darker brightness (total > 0.05)
        // - Handles dark greens like your sample (r=0.16, g=0.46, b=0.38)
        if (g > 0.35 && g > r + 0.10 && g > b + 0.05 && total > 0.05) {
            return "Dark Green";
        }

        return "Unknown";
    }
}
