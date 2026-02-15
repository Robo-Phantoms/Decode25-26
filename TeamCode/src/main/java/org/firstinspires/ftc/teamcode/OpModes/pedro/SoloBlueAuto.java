package org.firstinspires.ftc.teamcode.OpModes.pedro;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.conditionals.IfElseCommand;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "SoloBlue", group = "Regional Championship Autos")
public class SoloBlueAuto extends NextFTCOpMode {
    public SoloBlueAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    // -------- Poses -------- //
    private final Pose startPose = new Pose(23.5, 125, Math.toRadians(144));
    private final  Pose scorePose = new Pose(36, 114, Math.toRadians(144));
    private final Pose line2StartPose = new Pose(42, 60, Math.toRadians(180));
    private final Pose line2EndPose = new Pose(24, 60, Math.toRadians(180));
    private final Pose openGatePose = new Pose(24, 63, Math.toRadians(180));
    private final Pose gateIntakePose = new Pose(13, 54, Math.toRadians(140));
    private final Pose line1StartPose = new Pose(45, 84, Math.toRadians(180));
    private final Pose line1EndPose = new Pose(24, 84, Math.toRadians(180));
    private final Pose line3StartPose = new Pose(45, 36, Math.toRadians(180));
    private final Pose line3EndPose = new Pose(23, 36, Math.toRadians(180));
    private final Pose leavePose = new Pose(43, 123, Math.toRadians(170));

    public static Pose endPose = new Pose();


    // -------- Control Points -------- //
    private final Pose cLine2 = new Pose(77, 56);
    private final Pose cLine1 = new Pose(62, 81);
    private final Pose cLine3 = new Pose(68, 30);
    private final Pose cScore2 = new Pose(70, 67);
    private final Pose cScore3 = new Pose(77, 66);
    private final Pose cScore4 = new Pose(50, 92);
    private final Pose cScore5 = new Pose(52, 110);
    private final Pose cOpenGate = new Pose(70, 61);
    private final Pose cGateIntake = new Pose(26, 57);

    // -------- Path Chains -------- //
    private PathChain score1, line2, score2, openGate, score3, line1, score4, line3, score5, leave;

    public Command shoot(PathChain path){
        return new SequentialGroup(
                new ParallelGroup(new FollowPath(path), Intake.INSTANCE.reverse),
                Intake.INSTANCE.stop,
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(0.8),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount()))
        );
    }

    public Command gateIntakeScore(PathChain path){
        return new SequentialGroup(
                new ParallelGroup(new FollowPath(path), Intake.INSTANCE.reverse),
                Intake.INSTANCE.stop,
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(0.75),
                new ParallelGroup(Catapults.INSTANCE.shoot2, new InstantCommand(() -> Intake.INSTANCE.resetCount()))
        );
    }

    public Command shootFirst(){
        return new SequentialGroup(
                new ParallelGroup(new FollowPath(score1), Intake.INSTANCE.stop),
                new Delay(0.25),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount()))

        );
    }

    public Command intake(PathChain path){
        return new SequentialGroup(
                new ParallelGroup(new FollowPath(path), Intake.INSTANCE.run),
                new Delay(0.4)
        );
    }

    public Command openGate(){
        return new SequentialGroup(
                new ParallelGroup(new FollowPath(openGate), Intake.INSTANCE.run),
                new Delay(0.5)
        );
    }

    @Override
    public void onInit(){
        follower().setStartingPose(startPose);

        score1 = follower().pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();

        line2 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine2, line2StartPose))
                .setHeadingInterpolation(HeadingInterpolator.linear(scorePose.getHeading(), line3StartPose.getHeading()))
                .addPath(new BezierLine(line2StartPose, line2EndPose))
                .setConstantHeadingInterpolation(line2EndPose.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(line2EndPose, cScore2, scorePose))
                .setHeadingInterpolation(HeadingInterpolator.linear(line2EndPose.getHeading(), scorePose.getHeading() ))
                .build();

        openGate = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cOpenGate, openGatePose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), openGatePose.getHeading())
                .addPath(new BezierCurve(openGatePose, cGateIntake, gateIntakePose))
                .setLinearHeadingInterpolation(openGatePose.getHeading(), gateIntakePose.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(gateIntakePose, cScore3, scorePose))
                .setHeadingInterpolation(HeadingInterpolator.linear(line3EndPose.getHeading(), scorePose.getHeading()))
                .build();

        line1 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine1, line1StartPose))
                .setHeadingInterpolation(HeadingInterpolator.linear(scorePose.getHeading(), line1StartPose.getHeading()))
                .addPath(new BezierLine(line1StartPose, line1EndPose))
                .setConstantHeadingInterpolation(line1EndPose.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(line1EndPose, cScore4, scorePose))
                .setHeadingInterpolation(HeadingInterpolator.linear(line1EndPose.getHeading(), scorePose.getHeading()))
                .build();

        line3 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine3, line3StartPose))
                .setHeadingInterpolation(HeadingInterpolator.linear(scorePose.getHeading(), line3StartPose.getHeading()))
                .addPath(new BezierLine(line3StartPose, line3EndPose))
                .setConstantHeadingInterpolation(line3EndPose.getHeading())
                .build();

        score5 = follower().pathBuilder()
                .addPath(new BezierCurve(line3EndPose, cScore5, scorePose))
                .setHeadingInterpolation(HeadingInterpolator.linear(line3EndPose.getHeading(), scorePose.getHeading()))
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
                shootFirst(),
                intake(line2),
                shoot(score2),
                openGate(),
                Intake.INSTANCE.over3(0.1).endAfter(1.0),
                gateIntakeScore(score3),
                intake(line1),
                shoot(score4),
                intake(line3),
                shoot(leave)
        ).schedule();
    }

    @Override
    public void onStop(){
        endPose = follower().getPose();
    }
}