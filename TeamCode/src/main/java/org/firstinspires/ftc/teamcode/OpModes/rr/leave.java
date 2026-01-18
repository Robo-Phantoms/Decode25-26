package org.firstinspires.ftc.teamcode.OpModes.rr;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Autonomous(name = "leave (DO NOT USE UNLESS NECESSARY!!!)")
public class leave extends NextFTCOpMode {
    public leave(){
        addComponents(
                BulkReadComponent.INSTANCE
        );
    }

    private Pose2d startPose = new Pose2d(58, -8, Math.toRadians(270));

    public Command leave;
    MecanumDrive drive;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        leave = drive.commandBuilder(startPose)
                .lineToY(-34)
                .build();
    }
}
