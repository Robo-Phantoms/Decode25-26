package org.firstinspires.ftc.teamcode.OpModes.rr;

import static dev.nextftc.bindings.Bindings.button;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.ParallelRaceGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


public class gooberBlue extends NextFTCOpMode {
    public gooberBlue(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(230));
    private final Pose2d line2 = new Pose2d(12,-25, Math.toRadians(270));
    private final Pose2d line2End = new Pose2d(12, -50, Math.toRadians(270));
    private final Pose2d gateStart = new Pose2d(4, -30, Math.toRadians(270));
    private final Pose2d gateEnd = new Pose2d(4, -56, Math.toRadians(220));
    private final Pose2d gateIntakePose = new Pose2d(15, -56, Math.toRadians(230));
    private final Pose2d scoreFromGate = new Pose2d(15,-25,Math.toRadians(270));
    private final Pose2d line1 = new Pose2d(-12, -25, Math.toRadians(270));
    private final Pose2d line1End = new Pose2d(-12, -50, Math.toRadians(270));
    private final Pose2d line3 = new Pose2d(36, -25, Math.toRadians(270));
    private final Pose2d line3End = new Pose2d(36, -50, Math.toRadians(270));
    final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(0));



    public Command score1, intake1, openGate, score2, intake2, score3, intake3, score4, score5, leave, openGateForward, gateIntake;
    MecanumDrive drive;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);

        score1 = drive.commandBuilder(startPose)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake1 = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(line2, line2.heading)
                .lineToYSplineHeading(-50, line2.heading)
                .build();

        score2 = drive.commandBuilder(line2End).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        openGate = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(gateStart, gateStart.heading)
                .lineToYSplineHeading(gateEnd.position.y, gateEnd.heading)
                .build();


        gateIntake = drive.commandBuilder(gateEnd).fresh()
                .splineToLinearHeading(gateIntakePose, gateIntakePose.heading)
                .build();

        score3 = drive.commandBuilder(gateIntakePose).fresh()
                .setTangent(Math.toRadians(270))
                .lineToYSplineHeading(scoreFromGate.position.y, scoreFromGate.heading)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();


        intake2 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(line1, line1.heading)
                .lineToYSplineHeading(-50, Math.toRadians(270))
                .build();

        score4 = drive.commandBuilder(line1End).fresh()
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake3 = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(line3, line3.heading)
                .lineToYSplineHeading(-50, Math.toRadians(270))
                .build();

        score5 = drive.commandBuilder(line3End).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        leave = drive.commandBuilder(scorePose).fresh()
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();

    }

    @Override
    public void onStartButtonPressed(){
        /*button(() -> Intake.INSTANCE.getCount() > 3)
                .whenBecomesTrue(Intake.INSTANCE.reverse.endAfter(0.5));*/

        new SequentialGroup(
                Catapults.INSTANCE.down,
                score1,
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),

                new ParallelGroup(Intake.INSTANCE.run, intake1),
                score2,
                Intake.INSTANCE.stop,
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),
                openGate,
                new ParallelGroup(Intake.INSTANCE.run, gateIntake),
                new WaitUntil(() -> Intake.INSTANCE.getCount() == 3).endAfter(1.5),
                score3,
                Intake.INSTANCE.stop,
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),

                new ParallelGroup(Intake.INSTANCE.run, intake2),
                score4,
                Intake.INSTANCE.stop,
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),

                new ParallelGroup(Intake.INSTANCE.run, intake3),
                score5,
                Intake.INSTANCE.stop,
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),

                leave
        ).schedule();
    }
}