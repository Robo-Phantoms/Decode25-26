package org.firstinspires.ftc.teamcode.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name="AprilTag Test")
public class AtagTest extends OpMode {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal vision;


    @Override
    public void init() {
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        aprilTagProcessor.setDecimation(2);

        vision = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .enableLiveView(true)
                .build();

        telemetry.addLine("AprilTag OpMode initialized");
        telemetry.update();
    }

    @Override
    public void init_loop(){
        telemetry.addData("Vision Portal Status", vision.getCameraState());
    }
    @Override
    public void loop() {
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();

        if (!detections.isEmpty()) {
            for (AprilTagDetection detection : detections) {
                telemetry.addData("Tag ID", detection.id);
            }
        } else {
            telemetry.addLine("No tags detected");
        }

        telemetry.update();
    }


}
