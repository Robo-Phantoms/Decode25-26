package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "break beam test")
public class BreakBeamTest extends NextFTCOpMode {

    private DigitalChannel breakBeam;
    int count = 0;
    boolean lastDetected = false;

    @Override
    public void onInit(){
        breakBeam = hardwareMap.get(DigitalChannel.class, "breakBeam");
        breakBeam.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void onUpdate(){
        boolean detected = breakBeam.getState();
        if (detected && !lastDetected) {
            count++;
        }

        lastDetected = detected;

        telemetry.addLine(detected ? "object detected" : "no object detected");
        telemetry.addData("count", count);
        telemetry.update();
    }
}
