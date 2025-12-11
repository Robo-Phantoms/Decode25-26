package org.firstinspires.ftc.teamcode.TestOpModes;

import static dev.nextftc.bindings.Bindings.button;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@TeleOp(name = "AutoAimTest")
public class AutoAimTest extends NextFTCOpMode {
    public AutoAimTest(){
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Drivetrain.INSTANCE)
        );
    }
    MecanumDrive drive;
    private final Pose2d startPose = new Pose2d(0,0,Math.toRadians(270));
    private final Pose2d goalHeading = new Pose2d(0,0,Math.toRadians(230));


    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
    }

    @Override
    public void onStartButtonPressed(){
        //button(() -> gamepad1.y).whenBecomesTrue(Drivetrain.INSTANCE.turnTo(drive, goalHeading.heading));
    }
}
