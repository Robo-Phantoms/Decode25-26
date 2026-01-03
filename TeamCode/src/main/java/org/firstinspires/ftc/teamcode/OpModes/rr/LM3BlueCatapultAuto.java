package org.firstinspires.ftc.teamcode.OpModes.rr;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Autonomous(name = "LM3BlueAuto")
public class LM3BlueCatapultAuto extends NextFTCOpMode {
    public LM3BlueCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(233));
    private final Pose2d firstLineStartPose = new Pose2d(-6, -20, Math.toRadians(270));
    private final Pose2d secondLineStartPose = new Pose2d(20, -20, Math.toRadians(270));
    private final Pose2d thirdLineStartPose = new Pose2d(45, -20, Math.toRadians(270));
    private final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(230));
    private final Pose2d openGatePose = new Pose2d(2,-55, Math.toRadians(270));

    public Command score1, intake1, openGate, score2, intake2, score3, intake3, score4, leave;
    MecanumDrive drive;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        Catapults.INSTANCE.down.schedule();

        score1 = drive.commandBuilder(startPose)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake1 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .lineToY(-49)
                .build();

        openGate = drive.commandBuilder(new Pose2d(firstLineStartPose.position.x, -49, Math.toRadians(270))).fresh()
                .strafeToLinearHeading(openGatePose.position, openGatePose.heading)
                .build();

        score2 = drive.commandBuilder(openGatePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake2 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .lineToY(-49)
                .build();

        score3 = drive.commandBuilder(new Pose2d(secondLineStartPose.position.x, -49, Math.toRadians(270))).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake3 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToY(-49)
                .build();

        score4 = drive.commandBuilder(new Pose2d(thirdLineStartPose.position.x, -49, Math.toRadians(270))).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        leave = drive.commandBuilder(scorePose).fresh()
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();

    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                score1,
                Catapults.INSTANCE.shoot,
                new ParallelGroup(intake1, Intake.INSTANCE.run),
                new ParallelGroup(Intake.INSTANCE.stop, openGate),
                score2,
                Catapults.INSTANCE.shoot,
                new ParallelGroup(intake2, Intake.INSTANCE.run),
                new ParallelGroup(score3, Intake.INSTANCE.stop),
                Catapults.INSTANCE.shoot,
                new ParallelGroup(intake3, Intake.INSTANCE.run),
                new ParallelGroup(score4, Intake.INSTANCE.stop),
                Catapults.INSTANCE.shoot,
                new ParallelGroup(leave, Catapults.INSTANCE.stop)
        ).schedule();
    }
}