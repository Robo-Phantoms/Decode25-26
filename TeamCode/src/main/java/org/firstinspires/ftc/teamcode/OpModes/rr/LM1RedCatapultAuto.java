package org.firstinspires.ftc.teamcode.OpModes.rr;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Autonomous(name = "LM1RedAuto")
public class LM1RedCatapultAuto extends NextFTCOpMode {
    public LM1RedCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,48, Math.toRadians(127));
    private final Pose2d scorePose = new Pose2d(-39.1, 33.5, Math.toRadians(127));
    private final Pose2d firstLineStartPose = new Pose2d(-9, 22, Math.toRadians(90));
    private final Pose2d secondLineStartPose = new Pose2d(15.5, 22, Math.toRadians(93));
    private final Pose2d thirdLineStartPose = new Pose2d(38.5, 22, Math.toRadians(95));

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
                .splineToLinearHeading(new Pose2d(-39.1, 33.5, Math.toRadians(133)), Math.toRadians(130))
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.down,
                firstCycle,
                Catapults.INSTANCE.shoot3,
                Intake.INSTANCE.run,
                firstLineStart,
                Intake.INSTANCE.run,
                firstLineIntake,
                secondCycle,
                Catapults.INSTANCE.shoot3,
                Intake.INSTANCE.run,
                secondLineStart,
                secondLineIntake,
                thirdCycle,
                Catapults.INSTANCE.shoot3,
                Intake.INSTANCE.run,
                thirdLineStart,
                thirdLineIntake,
                Intake.INSTANCE.run,
                fourthCycle,
                Catapults.INSTANCE.shoot3
        ).schedule();

    }
}