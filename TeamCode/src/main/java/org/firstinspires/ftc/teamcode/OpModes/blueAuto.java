package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "blueAuto")
public class blueAuto extends NextFTCOpMode {
    public blueAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-40, -33,Math.toRadians(230));
    private final Pose2d firstLineStartPose = new Pose2d(-5, -28, Math.toRadians(270));

    MecanumDrive drive;
    Command firstCycle, firstLineStart, firstLineIntake, secondCycle;
    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        firstCycle = drive.commandBuilder(startPose)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .build();

        firstLineStart = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(new Pose2d(firstLineStartPose.position, firstLineStartPose.heading), firstLineStartPose.heading)
                .build();

        firstLineIntake = drive.commandBuilder(firstLineStartPose).fresh()
                .lineToY(-45)
                .build();

        secondCycle = drive.commandBuilder(new Pose2d(-5, -45, Math.toRadians(270))).fresh()
                .splineToLinearHeading(new Pose2d(scorePose.position, scorePose.heading), scorePose.heading)
                .build();

    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Intake.INSTANCE.intakeArtifactAuto(),
                Catapults.INSTANCE.catapultsDown,
                firstCycle,
                Catapults.INSTANCE.shootArtifact,
                firstLineStart,
                firstLineIntake,
                secondCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}
