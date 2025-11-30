package org.firstinspires.ftc.teamcode.TestOpModes;

import static dev.nextftc.bindings.Bindings.button;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Config
@TeleOp(name = "AutoAimTest")
public class AutoAimTest extends NextFTCOpMode {
    public AutoAimTest(){
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Drivetrain.INSTANCE, Limelight.INSTANCE)
        );
    }
    MecanumDrive drive;
    private final Pose2d startPose = new Pose2d(0,0,Math.toRadians(270));
    public static double kp;
    public static double target;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void onStartButtonPressed(){
        button(() -> gamepad1.y).whenBecomesTrue(() -> {
            Pose2d llPose = Limelight.INSTANCE.getLLPose();
            if (llPose != null) Drivetrain.INSTANCE.turnTo(drive, kp, llPose.heading.toDouble()).schedule();
        });
    }

    @Override
    public void onUpdate(){
        telemetry.addData("X", drive.getPose().position.x);
        telemetry.addData("Y", drive.getPose().position.y);
        telemetry.addData("Heading", drive.getPose().heading);
        telemetry.addData("Target Heading", target);
        telemetry.update();
    }
}
