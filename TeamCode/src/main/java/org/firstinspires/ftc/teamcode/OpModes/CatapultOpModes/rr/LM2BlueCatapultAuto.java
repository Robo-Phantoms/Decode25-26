package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes.rr;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Autonomous(name = "LM2BlueAuto")
public class LM2BlueCatapultAuto extends NextFTCOpMode {
    public LM2BlueCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(233));
    private final Pose2d firstLineStartPose = new Pose2d(-6, -20, Math.toRadians(268));
    private final Pose2d secondLineStartPose = new Pose2d(19, -20, Math.toRadians(268));
    private final Pose2d thirdLineStartPose = new Pose2d(43, -20, Math.toRadians(269));
    private final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(230));

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
                .lineToY(-47)
                .build();

        secondCycle = drive.commandBuilder(new Pose2d(firstLineStartPose.position.x, -47, Math.toRadians(270)))
                .setReversed(true)
                .splineToLinearHeading(scorePose,scorePose.heading)
                .build();
        secondLineStart = drive.commandBuilder(scorePose)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .build();
        secondLineIntake = drive.commandBuilder(secondLineStartPose)
                .lineToY(-47)
                .build();
        thirdCycle = drive.commandBuilder(new Pose2d(secondLineStartPose.position.x, -47, Math.toRadians(270)))
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();
        thirdLineStart = drive.commandBuilder(scorePose)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .build();
        thirdLineIntake = drive.commandBuilder(thirdLineStartPose)
                .lineToY(-49)
                .build();
        fourthCycle = drive.commandBuilder(new Pose2d(thirdLineStartPose.position.x, -49, Math.toRadians(270)))
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        leave = drive.commandBuilder(scorePose)
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.down,
                firstCycle,
                Catapults.INSTANCE.shoot,
                Intake.INSTANCE.run,
                firstLineStart,
                Intake.INSTANCE.run,
                firstLineIntake,
                secondCycle,
                Intake.INSTANCE.stop,
                new Delay(0.5),
                Catapults.INSTANCE.shoot,
                Intake.INSTANCE.run,
                secondLineStart,
                secondLineIntake,
                thirdCycle,
                Intake.INSTANCE.stop,
                new Delay(0.5),
                Catapults.INSTANCE.shoot,
                Intake.INSTANCE.run,
                thirdLineStart,
                thirdLineIntake,
                Intake.INSTANCE.run,
                fourthCycle,
                Intake.INSTANCE.stop,
                new Delay(0.5),
                Catapults.INSTANCE.shoot,
                leave
        ).schedule();

    }
}