package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "testAuto")
public class blueAuto extends NextFTCOpMode {
    public blueAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-40, -35,Math.toRadians(230));
    private final Pose2d firstLinePose = new Pose2d(-10, -52, Math.toRadians(270));

    MecanumDrive drive;
    Command firstCycle, firstLine, secondCycle;
    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        firstCycle = drive.commandBuilder(startPose)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .build();

        firstLine = drive.commandBuilder(scorePose).fresh()
                .splineTo(firstLinePose.position, firstLinePose.heading)
                .build();

        secondCycle = drive.commandBuilder(firstLinePose).fresh()
                .splineTo(scorePose.position, scorePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.catapultsDown,
                firstCycle,
                Catapults.INSTANCE.shootArtifact,
                new ParallelGroup(
                        firstLine,
                        Intake.INSTANCE.intakeArtifact(1.0)
                ),
                secondCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}
