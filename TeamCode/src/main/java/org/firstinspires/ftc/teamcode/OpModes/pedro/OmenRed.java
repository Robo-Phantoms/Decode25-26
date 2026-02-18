package org.firstinspires.ftc.teamcode.OpModes.pedro;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "OmenRed")
public class OmenRed extends NextFTCOpMode {
    public OmenRed(){
        addComponents(
                new SubsystemComponent(Intake.INSTANCE, Catapults.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE
        );
    }

    // -------- Poses -------- //
    private final Pose startPose = new Pose(23.5, 125, Math.toRadians(144)).mirror();
    private final  Pose scorePose = new Pose(36, 112, Math.toRadians(144)).mirror();
    private final Pose line2StartPose = new Pose(42, 58, Math.toRadians(180)).mirror();
    private final Pose line2EndPose = new Pose(27, 58, Math.toRadians(180)).mirror();
    private final Pose openGatePose = new Pose(22, 67, Math.toRadians(180)).mirror();
    private final Pose gateIntakePose = new Pose(13, 54, Math.toRadians(140)).mirror();
    private final Pose line1StartPose = new Pose(45, 84, Math.toRadians(180)).mirror();
    private final Pose line1EndPose = new Pose(25, 84, Math.toRadians(180)).mirror();
    private final Pose line3StartPose = new Pose(45, 35, Math.toRadians(180)).mirror();
    private final Pose line3EndPose = new Pose(23, 35, Math.toRadians(180)).mirror();
    private final Pose leavePose = new Pose(43, 123, Math.toRadians(170)).mirror();

    public static Pose endPose = new Pose();


    // -------- Control Points -------- //
    private final Pose cLine2 = new Pose(77, 56).mirror();
    private final Pose cLine1 = new Pose(62, 81).mirror();
    private final Pose cLine3 = new Pose(68, 30).mirror();
    private final Pose cScore2 = new Pose(70, 67).mirror();
    private final Pose cScore3 = new Pose(77, 66).mirror();
    private final Pose cScore4 = new Pose(50, 92).mirror();
    private final Pose cScore5 = new Pose(52, 110).mirror();
    private final Pose cOpenGate = new Pose(37, 62).mirror();

    private PathChain score1, line3, score2, line2, openGate1, score3, line1, score4, leave;

    public Command intake(PathChain p){
        return new SequentialGroup(
                new ParallelGroup(new FollowPath(p), Intake.INSTANCE.run),
                new Delay(0.5)
        );
    }

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

    @Override
    public void onInit(){
        score1 = follower().pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();

        line3 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine3, line3StartPose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), line3StartPose.getHeading())
                .addPath(new BezierLine(line3StartPose, line3EndPose))
                .setConstantHeadingInterpolation(line3EndPose.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(line3EndPose, cScore5, scorePose))
                .setLinearHeadingInterpolation(line3EndPose.getHeading(), scorePose.getHeading())
                .build();

        line2 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine2, line2StartPose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), line2StartPose.getHeading())
                .addPath(new BezierLine(line2StartPose, line2EndPose))
                .setConstantHeadingInterpolation(line2EndPose.getHeading())
                .build();

        openGate1 = follower().pathBuilder()
                .addPath(new BezierCurve(line2EndPose, cOpenGate, openGatePose))
                .setLinearHeadingInterpolation(line2EndPose.getHeading(), openGatePose.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(openGatePose, new Pose(68, 56), scorePose))
                .setLinearHeadingInterpolation(openGatePose.getHeading(), scorePose.getHeading())
                .build();

        line1 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePose, cLine1, line1StartPose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), line1StartPose.getHeading())
                .addPath(new BezierLine(line1StartPose, line1EndPose))
                .setConstantHeadingInterpolation(line1EndPose.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(line1EndPose, cScore4, scorePose))
                .setLinearHeadingInterpolation(line1EndPose.getHeading(), scorePose.getHeading())
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
                intake(line3),
                shoot(score2),
                intake(line2),
                new FollowPath(openGate1),
                shoot(score3),
                intake(line1),
                shoot(score4),
                new FollowPath(leave)
        ).schedule();
    }
}
