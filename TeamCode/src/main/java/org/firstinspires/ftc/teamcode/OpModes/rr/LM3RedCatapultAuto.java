package org.firstinspires.ftc.teamcode.OpModes.rr;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


//@Autonomous(name = "LM3RedAuto")
public class LM3RedCatapultAuto extends NextFTCOpMode {
    public LM3RedCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    final Pose2d startPose = new Pose2d(-51,48, Math.toRadians(360 - 230));
    final Pose2d scorePose = new Pose2d(-36, 33, Math.toRadians(360 - 233));
    final Pose2d firstLineStartPose = new Pose2d(-8, 25, Math.toRadians(92));
    final Pose2d secondLineStartPose = new Pose2d(15.25, 22, Math.toRadians(93));
    final Pose2d thirdLineStartPose = new Pose2d(39, 18, Math.toRadians(90));
    final Pose2d leavePose = new Pose2d(2, 38, Math.toRadians(180));
    final Pose2d openGatePose = new Pose2d(1,48,Math.toRadians(90));

    public Command score1, intake1, openGate, score2, intake2, score3, intake3, score4, leave, openGateForward;
    MecanumDrive drive;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);

        score1 = drive.commandBuilder(startPose)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake1 = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .lineToY(54)
                .build();

        openGate = drive.commandBuilder(new Pose2d(firstLineStartPose.position.x, 48, Math.toRadians(90))).fresh()
                .splineToLinearHeading(openGatePose, openGatePose.heading)
                .build();

        openGateForward = drive.commandBuilder(openGatePose).fresh()
                .lineToY(62)
                .waitSeconds(1.0)
                .build();

        score2 = drive.commandBuilder(new Pose2d(openGatePose.position.x, 60, Math.toRadians(90))).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake2 = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .lineToY(54)
                .build();

        score3 = drive.commandBuilder(new Pose2d(secondLineStartPose.position.x, 48, Math.toRadians(90))).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake3 = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToY(54)
                .build();

        score4 = drive.commandBuilder(new Pose2d(thirdLineStartPose.position.x, 48, Math.toRadians(90))).fresh()
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(scorePose.position.x, 31, Math.toRadians(360-233)),scorePose.heading)
                .build();

        leave = drive.commandBuilder(new Pose2d(scorePose.position.x, 31, Math.toRadians(360-233))).fresh()
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();

    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.down,
                score1,
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(intake1, Intake.INSTANCE.run),
                new Delay(0.5),
                new ParallelGroup(Intake.INSTANCE.stop, openGate),
                openGateForward,
                new Delay(0.1),
                score2,
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(intake2, Intake.INSTANCE.run),
                new Delay(0.5),
                new ParallelGroup(score3, Intake.INSTANCE.stop),
                new Delay(0.1),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(intake3, Intake.INSTANCE.run),
                new Delay(0.1),
                new ParallelGroup(score4, Intake.INSTANCE.stop),
                new Delay(0.5),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(leave, Catapults.INSTANCE.stop)
        ).schedule();
    }
}