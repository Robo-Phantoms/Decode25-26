package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "redAuto")
public class redAuto extends NextFTCOpMode {
    public redAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,48, Math.toRadians(129));
    private final Pose2d scorePose = new Pose2d(-40, 35,Math.toRadians(129));

    MecanumDrive drive;
    Command firstCycle;
    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        firstCycle = drive.commandBuilder(startPose)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.catapultsDown,
                firstCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}
