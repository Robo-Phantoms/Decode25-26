package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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

@Autonomous(name = "redAuto")
public class redAuto extends NextFTCOpMode {
    public redAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51, 48, Math.toRadians(135));
    private final Pose2d scorePose = new Pose2d(-40, 33, Math.toRadians(135));
    private final Pose2d firstLineStartPose = new Pose2d(-4, 28, Math.toRadians(90));

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
                .lineToY(46)
                .build();

        secondCycle = drive.commandBuilder(new Pose2d(-5, 44, Math.toRadians(90))).fresh()
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(new Vector2d(-30, 27), Math.toRadians(145)), Math.toRadians(145))
                .build();

        secondLineStart = drive.commandBuilder(new Pose2d(-30, 27, Math.toRadians(145))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(20, 34), Math.toRadians(90)), Math.toRadians(90))
                .build();

        secondLineIntake = drive.commandBuilder(new Pose2d(20, 34, Math.toRadians(90)))
                .lineToY(50)
                .build();

        thirdCycle = drive.commandBuilder(new Pose2d(20, 42, Math.toRadians(270))).fresh()
                .splineToLinearHeading(new Pose2d(-30, 27, Math.toRadians(220)), Math.toRadians(220))
                .build();

        thirdLineStart = drive.commandBuilder(new Pose2d(-30, 27, Math.toRadians(220))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(44, 34), Math.toRadians(270)), Math.toRadians(270))
                .build();

        thirdLineIntake = drive.commandBuilder(new Pose2d(44, 34, Math.toRadians(270))).fresh()
                .lineToY(44)
                .build();

        fourthCycle = drive.commandBuilder(new Pose2d(44, 44, Math.toRadians(270))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(-30, 27), Math.toRadians(220)), Math.toRadians(220))
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
                Intake.INSTANCE.intakeArtifactAuto(),
                new Delay(0.2),
                Intake.INSTANCE.stopIntake(),
                secondCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                secondLineStart,
                secondLineIntake,
                new Delay(0.5),
                Intake.INSTANCE.stopIntake(),
                thirdCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                thirdLineStart,
                thirdLineIntake,
                new Delay(0.5),
                Intake.INSTANCE.stopIntake(),
                fourthCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}
