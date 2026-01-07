package org.firstinspires.ftc.teamcode.util.roadrunner;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.components.Component;
import dev.nextftc.ftc.ActiveOpMode;

public class RoadrunnerComponent implements Component {

    private static MecanumDrive drive;
    private final Pose2d startPose;
    private Telemetry telemetry;

    public RoadrunnerComponent(Pose2d startPose){
        this.startPose = startPose;
    }
    @Override
    public void preInit(){
        drive = new MecanumDrive(ActiveOpMode.hardwareMap(), startPose);
        telemetry = ActiveOpMode.telemetry();
    }

    @Override
    public void preUpdate(){
        drive.updatePoseEstimate();
        telemetry.addData("X:", drive.getPose().position.x);
        telemetry.addData("Y:", drive.getPose().position.y);
        telemetry.addData("Heading:", drive.getPose().heading.toDouble());
    }

    public static MecanumDrive drive() {
        if (drive == null) {
            throw new IllegalStateException("Roadrunner not initialized!");
        }
        return drive;
    }
}
