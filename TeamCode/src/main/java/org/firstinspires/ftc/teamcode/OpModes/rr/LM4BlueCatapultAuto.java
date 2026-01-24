package org.firstinspires.ftc.teamcode.OpModes.rr;

import static dev.nextftc.bindings.Bindings.button;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "LM4BlueAuto")
public class LM4BlueCatapultAuto extends NextFTCOpMode {
    public LM4BlueCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(230));
    private final Pose2d line2Pose = new Pose2d(12,-25, Math.toRadians(270));
    private final Pose2d gateStartPose = new Pose2d(4, -30, Math.toRadians(270));
    private final Pose2d gateEndPose = new Pose2d(4, -56, Math.toRadians(220));
    private final Pose2d gateIntakePose = new Pose2d(15, -56, Math.toRadians(270));
    private final Pose2d scoreFromGatePose = new Pose2d(15,-25,Math.toRadians(270));
    private final Pose2d line1Pose = new Pose2d(-12, -25, Math.toRadians(270));
    private final Pose2d line3Pose = new Pose2d(36, -25, Math.toRadians(270));
    final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(0));

    private MecanumDrive drive;
    public Command score1, intakeLine2, score2, gate, gateIntake, score3;
    public Command intakeLine1, score4, intakeLine3, score5, leave;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);

        score1 = drive.commandBuilder(startPose)
                .lineToYLinearHeading(scorePose.position.y, scorePose.heading)
                .build();

        intakeLine2 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(line2Pose, line2Pose.heading)
                .lineToYSplineHeading(-50, line2Pose.heading)
                .build();

        score2 = drive.commandBuilder(new Pose2d(line2Pose.position.x, line2Pose.position.y, Math.toRadians(270))).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        gate = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(gateStartPose, gateStartPose.heading)
                .lineToYSplineHeading(gateEndPose.position.y, gateEndPose.heading)
                .build();

        gateIntake = drive.commandBuilder(gateEndPose).fresh()
                .setReversed(true)
                .splineToLinearHeading(gateIntakePose, gateIntakePose.heading)
                .build();

        score3 = drive.commandBuilder(gateIntakePose)
                .lineToYSplineHeading(scoreFromGatePose.position.y, scoreFromGatePose.heading)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intakeLine1 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(line1Pose, line1Pose.heading)
                .lineToYSplineHeading(-50, line1Pose.heading)
                .build();

        score4 = drive.commandBuilder(new Pose2d(line1Pose.position.x, -50, Math.toRadians(270)))
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intakeLine3 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(line3Pose, line3Pose.heading)
                .lineToYSplineHeading(-50, line3Pose.heading)
                .build();

        score5 = drive.commandBuilder(new Pose2d(line3Pose.position.x, -50, Math.toRadians(270))).fresh()
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        leave = drive.commandBuilder(scorePose).fresh()
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        button(() -> Intake.INSTANCE.getCount() >= 4).whenBecomesTrue(Intake.INSTANCE.reverse);

        new SequentialGroup(
                Catapults.INSTANCE.down,
                score1,
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, intakeLine2),
                new ParallelGroup(Intake.INSTANCE.stop, score2),
                Catapults.INSTANCE.shoot3,
                gate,
                new ParallelGroup(Intake.INSTANCE.run, gateIntake),
                new WaitUntil(() -> Intake.INSTANCE.getCount() == 3).endAfter(2.0),
                new ParallelGroup(Intake.INSTANCE.stop, score3),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, intakeLine1),
                new ParallelGroup(Intake.INSTANCE.stop, score4),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, intakeLine3),
                new ParallelGroup(Intake.INSTANCE.stop, score5),
                Catapults.INSTANCE.shoot3,
                leave
        ).schedule();
    }
}