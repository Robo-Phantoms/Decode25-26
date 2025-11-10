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

@Autonomous(name = "blueAutoNicer")
public class blueAutoNicer extends NextFTCOpMode {
    public blueAutoNicer(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-40, -33,Math.toRadians(230));
    private final Pose2d scorePoseV2 = new Pose2d(-35, -25,Math.toRadians(220));
    private final Pose2d firstLineStartPose = new Pose2d(-5, -28, Math.toRadians(270));
    private final Pose2d secondLineStartPose = new Pose2d(20, -34, Math.toRadians(270));
    private final Pose2d thirdLineStartPose = new Pose2d(44, -34, Math.toRadians(270));
    private final Pose2d secondCycleStartPose = new Pose2d(-5, -42, Math.toRadians(270));
    private final Pose2d thirdCycleStartPose = new Pose2d(20, -42, Math.toRadians(270));
    private final Pose2d fourthCycleStartPose = new Pose2d(44, -44, Math.toRadians(270));

    MecanumDrive drive;
    Command firstCycle, firstLineStart, firstLineIntake, secondCycle, secondLineStart, secondLineIntake,thirdLineIntake,thirdCycle, thirdLineStart,fourthCycle;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        firstCycle = drive.commandBuilder(startPose)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .build();

        firstLineStart = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .build();

        firstLineIntake = drive.commandBuilder(firstLineStartPose).fresh()
                .lineToY(secondCycleStartPose.position.y)
                .build();

        secondCycle = drive.commandBuilder(secondCycleStartPose).fresh()
                .splineToLinearHeading(scorePoseV2, scorePoseV2.heading)
                .build();

        secondLineStart = drive.commandBuilder(scorePoseV2).fresh()
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .build();

        secondLineIntake = drive.commandBuilder(secondLineStartPose)
                .lineToY(thirdCycleStartPose.position.y)
                .build();

        thirdCycle = drive.commandBuilder(thirdCycleStartPose).fresh()
                .splineToLinearHeading(scorePoseV2, scorePoseV2.heading)
                .build();

        thirdLineStart = drive.commandBuilder(scorePoseV2).fresh()
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .build();

        thirdLineIntake = drive.commandBuilder(thirdLineStartPose).fresh()
                .lineToY(fourthCycleStartPose.position.y)
                .build();

        fourthCycle =drive.commandBuilder(fourthCycleStartPose).fresh()
                .splineToLinearHeading(scorePoseV2, scorePoseV2.heading)
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
                firstLineIntake,
                Intake.INSTANCE.stopIntake(),
                secondCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                secondLineStart,
                secondLineIntake,
                Intake.INSTANCE.stopIntake(),
                thirdCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                thirdLineStart,
                thirdLineIntake,
                Intake.INSTANCE.stopIntake(),
                fourthCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}
