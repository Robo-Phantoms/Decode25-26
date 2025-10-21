package org.firstinspires.ftc.teamcode.util.PID;

import android.util.Size;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.MotorEx;

@Config
@TeleOp(name = "AutoAim")
public class AutoAim extends NextFTCOpMode {
    public AutoAim() {
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private MotorEx leftFront = new MotorEx("leftFront");
    private MotorEx rightFront = new MotorEx("rightFront").reversed();
    private MotorEx leftBack = new MotorEx("leftBack");
    private MotorEx rightBack = new MotorEx("rightBack").reversed();
    private Position cameraPosition = new Position(DistanceUnit.INCH,
            0, 0, 0, 0);
    private YawPitchRollAngles cameraOrientation = new YawPitchRollAngles(AngleUnit.DEGREES,
            0, -90, 0, 0);
    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal vision;

    public static double hKp = 0.02, hKi = 0, hKd = 0.001;
    public static double sKp = 0.015, sKi = 0, sKd = 0.001;

    public static double yawTolerance = 1.0;
    public static double xTolerance = 0.5;

    public static double maxPower = 0.6;
    public static double minPower = 0.05;

    private ControlSystem headingPID;
    private ControlSystem strafePID;

    @Override
    public void onInit() {
        headingPID = ControlSystem.builder()
                .angular(AngleType.DEGREES, feedback -> feedback.posPid(hKp, hKi, hKd))
                .build();

        strafePID = ControlSystem.builder()
                .posPid(sKp, sKi, sKd)
                .build();

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setCameraPose(cameraPosition, cameraOrientation)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                //placeholders for camera intrinsics
                .setLensIntrinsics(0,0,0,0)
                .build();
        aprilTagProcessor.setDecimation(2);

        vision = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTagProcessor)
                .enableLiveView(true)
                .setCameraResolution(new Size(640, 480))
                .build();

        telemetry.addLine("atag initialized");
        telemetry.update();
    }

    @Override
    public void onUpdate() {
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();

        if (!detections.isEmpty()) {
            AprilTagDetection tag = detections.get(0);

            double yawError = tag.ftcPose.yaw;
            double xError = tag.ftcPose.x;

            boolean yawOnTarget = Math.abs(yawError) < yawTolerance;
            boolean xOnTarget = Math.abs(xError) < xTolerance;

            double turn = yawOnTarget ? 0 : headingPID.calculate(new KineticState(yawError));
            double strafe = xOnTarget ? 0 : strafePID.calculate(new KineticState(xError));
            double forward = 0;

            double lf = forward + strafe + turn;
            double rf = forward - strafe - turn;
            double lr = forward - strafe + turn;
            double rr = forward + strafe - turn;

            double maxMagnitude = Math.max(
                    Math.max(Math.abs(lf), Math.abs(rf)),
                    Math.max(Math.abs(lr), Math.abs(rr))
            );
            if (maxMagnitude > maxPower) {
                lf = lf / maxMagnitude * maxPower;
                rf = rf / maxMagnitude * maxPower;
                lr = lr / maxMagnitude * maxPower;
                rr = rr / maxMagnitude * maxPower;
            }

            lf = applyMinPower(lf);
            rf = applyMinPower(rf);
            lr = applyMinPower(lr);
            rr = applyMinPower(rr);

            setDtPowers(lf, rf, lr, rr);

            telemetry.addData("Tag ID", tag.id);
            telemetry.addData("Yaw Error", "%.2f", yawError);
            telemetry.addData("X Error", "%.2f", xError);
            telemetry.addData("Turn Power", "%.2f", turn);
            telemetry.addData("Strafe Power", "%.2f", strafe);

        } else {
            setDtPowers(0, 0, 0, 0);
            telemetry.addLine("No tags detected");
        }

        telemetry.update();
    }

    private double applyMinPower(double power) {
        if (Math.abs(power) < 0.001) return 0;
        if (power > 0) return Math.max(power, minPower);
        if (power < 0) return Math.min(power, -minPower);
        return 0;
    }

    private void setDtPowers(double lfPower, double rfPower, double lbPower, double rbPower) {
        leftFront.setPower(lfPower);
        rightFront.setPower(rfPower);
        leftBack.setPower(lbPower);
        rightBack.setPower(rbPower);
    }
}
