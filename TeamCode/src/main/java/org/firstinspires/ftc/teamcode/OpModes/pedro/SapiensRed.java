package org.firstinspires.ftc.teamcode.OpModes.pedro;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "SapiensRed", group = "Regional Championship Autos")
public class SapiensRed extends NextFTCOpMode {
    public SapiensRed(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    // -------- Poses -------- //
    private final Pose startPose = new Pose(23.5, 125, Math.toRadians(144)).mirror();
    private final  Pose scorePose = new Pose(36, 112, Math.toRadians(144)).mirror();
    private final Pose line2StartPose = new Pose(42, 58, Math.toRadians(180)).mirror();
    private final Pose line2EndPose = new Pose(27, 58, Math.toRadians(180)).mirror();
    private final Pose openGatePose = new Pose(22, 67, Math.toRadians(180)).mirror();
    private final Pose line1StartPose = new Pose(45, 84, Math.toRadians(180)).mirror();
    private final Pose line1EndPose = new Pose(25, 84, Math.toRadians(180)).mirror();
    private final Pose leavePose = new Pose(43, 123, Math.toRadians(170)).mirror();

    public static Pose endPose = new Pose();


    // -------- Control Points -------- //
    private final Pose cLine2 = new Pose(77, 56).mirror();
    private final Pose cLine1 = new Pose(62, 81).mirror();
    private final Pose cScore2 = new Pose(70, 67).mirror();
    private final Pose cScore4 = new Pose(50, 92).mirror();
    private final Pose cOpenGate = new Pose(70, 61).mirror();

    // -------- Path Chains -------- //
    private PathChain score1, line2, score2, openGate, score3, line1,leave;

    @Override
    public void onInit(){
        follower().setStartingPose(startPose);

        score1 = follower().pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();

        line2 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine2, line2StartPose))
                .setHeadingInterpolation(HeadingInterpolator.linear(scorePose.getHeading(), line2StartPose.getHeading()))
                .addPath(new BezierLine(line2StartPose, line2EndPose))
                .setConstantHeadingInterpolation(line2EndPose.getHeading())
                .build();

        openGate = follower().pathBuilder()
                .addPath(new BezierCurve(line2EndPose, cOpenGate, openGatePose))
                .setLinearHeadingInterpolation(line2EndPose.getHeading(), openGatePose.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(openGatePose, cScore2, scorePose))
                .setHeadingInterpolation(HeadingInterpolator.linear(openGatePose.getHeading(), scorePose.getHeading() ))
                .build();

        line1 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine1, line1StartPose))
                .setHeadingInterpolation(HeadingInterpolator.linear(scorePose.getHeading(), line1StartPose.getHeading()))
                .addPath(new BezierLine(line1StartPose, line1EndPose))
                .setConstantHeadingInterpolation(line1EndPose.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(line1EndPose, cScore4, scorePose))
                .setHeadingInterpolation(HeadingInterpolator.linear(line1EndPose.getHeading(), scorePose.getHeading()))
                .build();


        leave = follower().pathBuilder()
                .addPath(new BezierLine(scorePose, leavePose))
                .setConstantHeadingInterpolation(leavePose.getHeading())
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.down,
                new FollowPath(score1),
                new Delay(0.5),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(new FollowPath(line2), Intake.INSTANCE.run),
                new FollowPath(openGate),
                new Delay(0.5),
                new FollowPath(score2),
                Intake.INSTANCE.stop,
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(0.8),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),
                new ParallelGroup(new FollowPath(line1), Intake.INSTANCE.run),
                new FollowPath(score3),
                Intake.INSTANCE.stop,
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(0.8),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),
                new FollowPath(leave)
        ).schedule();
    }
}