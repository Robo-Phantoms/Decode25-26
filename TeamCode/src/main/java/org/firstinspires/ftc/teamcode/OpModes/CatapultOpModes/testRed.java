package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;


import dev.nextftc.core.commands.Command;

import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Autonomous(name = "testRed")
public class testRed extends NextFTCOpMode {
    public testRed() {
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,48, Math.toRadians(127));
    private final Pose2d scorePose = new Pose2d(-38, 34.5, Math.toRadians(127));
    private final Pose2d firstLineStartPose = new Pose2d(-8, 22, Math.toRadians(90));
    private final Pose2d secondLineStartPose = new Pose2d(18, 22, Math.toRadians(90));
    private final Pose2d thirdLineStartPose = new Pose2d(43, 22, Math.toRadians(90));
    private final Pose2d leavePose = new Pose2d(2, 38, Math.toRadians(127));
    double START_TANGENT = 90;

    MecanumDrive drive;
    Command Auto;

    @Override
    public void onInit() {
        drive = new MecanumDrive(hardwareMap, startPose);
        Auto = drive.commandBuilder(startPose)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .stopAndAdd(Catapults.INSTANCE.shootArtifact)

                .setTangent(START_TANGENT)
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .afterTime(0.5, Intake.INSTANCE.intakeArtifactAuto())

                .lineToY(44)

                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .afterTime(0.5, Intake.INSTANCE.stopIntake())

                .stopAndAdd(Catapults.INSTANCE.shootArtifact)

                .setTangent(START_TANGENT)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .afterTime(0.5, Intake.INSTANCE.intakeArtifactAuto())

                .lineToY(44)

                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .afterTime(0.5, Intake.INSTANCE.stopIntake())

                .stopAndAdd(Catapults.INSTANCE.shootArtifact)

                .setTangent(START_TANGENT)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .afterTime(0.5, Intake.INSTANCE.intakeArtifactAuto())

                .lineToY(46)

                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .afterTime(0.5, Intake.INSTANCE.stopIntake())

                .stopAndAdd(Catapults.INSTANCE.shootArtifact)
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed() {
        Auto.schedule();
    }
}
