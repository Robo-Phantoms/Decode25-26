package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.sun.tools.javac.util.MandatoryWarningHandler;

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

@Autonomous(name = "new Blue")
public class newBlue extends NextFTCOpMode {
    public newBlue(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-39.1, -33.5, Math.toRadians(233));
    private final Pose2d firstLineStartPose = new Pose2d(-8, -22, Math.toRadians(270));
    private final Pose2d secondLineStartPose = new Pose2d(16, -22, Math.toRadians(270));
    private final Pose2d thirdLineStartPose = new Pose2d(41, -22, Math.toRadians(270));

    MecanumDrive drive;
    Command firstCycle, firstLineStart, firstLineIntake, secondCycle, secondLineStart, secondLineIntake,thirdLineIntake, thirdCycle, thirdLineStart,fourthCycle;
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
                .lineToY(-44)
                .build();

        secondCycle = drive.commandBuilder(new Pose2d(firstLineStartPose.position.x, -44, Math.toRadians(270)))
                .setReversed(true)
                .splineToLinearHeading(scorePose,scorePose.heading)
                .build();
        secondLineStart = drive.commandBuilder(scorePose)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .build();
        secondLineIntake = drive.commandBuilder(secondLineStartPose)
                .lineToY(-44)
                .build();
        thirdCycle = drive.commandBuilder(new Pose2d(secondLineStartPose.position.x, -44, Math.toRadians(270)))
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();
        thirdLineStart = drive.commandBuilder(scorePose)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .build();
        thirdLineIntake = drive.commandBuilder(thirdLineStartPose)
                .lineToY(-46)
                .build();
        fourthCycle = drive.commandBuilder(new Pose2d(thirdLineStartPose.position.x, -46, Math.toRadians(270)))
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.catapultsDown,
                firstCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                firstLineStart,
                Intake.INSTANCE.intakeArtifactAuto(),
                firstLineIntake,
                secondCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                secondLineStart,
                secondLineIntake,
                thirdCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                thirdLineStart,
                thirdLineIntake,
                Intake.INSTANCE.intakeArtifactAuto(),
                fourthCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}