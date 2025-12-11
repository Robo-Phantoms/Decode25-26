package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Autonomous(name = "LM2RedAuto")
public class LM2RedCatapultAuto extends NextFTCOpMode {
    public LM2RedCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,51, Math.toRadians(127));
    private final Pose2d scorePose = new Pose2d(-38, 34.5, Math.toRadians(127));
    private final Pose2d firstLineStartPose = new Pose2d(-12, 20, Math.toRadians(90));
    private final Pose2d secondLineStartPose = new Pose2d(14, 20, Math.toRadians(90));
    private final Pose2d thirdLineStartPose = new Pose2d(36, 20, Math.toRadians(90))
            ;
    private final Pose2d leavePose = new Pose2d(2, 38, Math.toRadians(127));

    MecanumDrive drive;
    Command firstCycle, firstLineStart, firstLineIntake, secondCycle, secondLineStart, secondLineIntake,thirdLineIntake, thirdCycle, thirdLineStart,fourthCycle, leave;
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
                .lineToY(44)
                .build();

        secondCycle = drive.commandBuilder(new Pose2d(firstLineStartPose.position.x, 44, Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(scorePose,scorePose.heading)
                .build();
        secondLineStart = drive.commandBuilder(scorePose)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .build();
        secondLineIntake = drive.commandBuilder(secondLineStartPose)
                .lineToY(44)
                .build();
        thirdCycle = drive.commandBuilder(new Pose2d(secondLineStartPose.position.x, 44, Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-39.1, 33.5, Math.toRadians(133)), Math.toRadians(130))
                .build();
        thirdLineStart = drive.commandBuilder(scorePose)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .build();
        thirdLineIntake = drive.commandBuilder(thirdLineStartPose)
                .lineToY(46)
                .build();
        fourthCycle = drive.commandBuilder(new Pose2d(thirdLineStartPose.position.x, 46, Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-39.1, 33.5, Math.toRadians(133)), Math.toRadians(130))
                .build();

        leave = drive.commandBuilder(new Pose2d(-39.1, 33.5, Math.toRadians(133)))
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
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
                Catapults.INSTANCE.shootArtifact,
                leave
        ).schedule();

    }
}