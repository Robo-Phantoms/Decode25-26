package org.firstinspires.ftc.teamcode.TestOpModes;

import static dev.nextftc.bindings.Bindings.button;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Config
@TeleOp(name = "AutoAimTestLL")
public class AutoAimTestLL extends NextFTCOpMode {
    public AutoAimTestLL(){
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Drivetrain.INSTANCE, Limelight.INSTANCE)
        );
    }
    MecanumDrive drive;
    //private final Pose2d startPose = LM2BlueCatapultAuto.autonEndPose;
    private final Pose2d goalHeading = new Pose2d(0,0,Math.toRadians(230));

    @Override
    public void onInit(){
        //drive = new MecanumDrive(hardwareMap, startPose);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void onStartButtonPressed() {
        button(() -> gamepad1.y).whenBecomesTrue(() -> {
            Pose2d llPose = Limelight.INSTANCE.getLLPose();
            if (llPose != null) {
                drive.localizer.setPose(llPose);
               // Drivetrain.INSTANCE.turnTo(drive, goalHeading.heading).schedule();
            }
        });
    }
}
