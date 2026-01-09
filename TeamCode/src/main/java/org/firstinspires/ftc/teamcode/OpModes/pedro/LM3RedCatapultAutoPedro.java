package org.firstinspires.ftc.teamcode.OpModes.pedro;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;


@Autonomous(name = "LM3RedCatapultAutoPedro")
public class LM3RedCatapultAutoPedro extends NextFTCOpMode {
    public LM3RedCatapultAutoPedro(){
        addComponents(
                new PedroComponent(Constants::createFollower),
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose startPose = new Pose(22,125, Math.toRadians(144)).mirror();
    private final Pose scorePose = new Pose(36, 112.5, Math.toRadians(138)).mirror();
    private final Pose firstLineStartPose = new Pose(42, 84, Math.toRadians(180)).mirror();
    private final Pose firstLineEndPose = firstLineStartPose.withX(19).mirror();
    private final Pose secondLineStartPose = new Pose(42, 60, Math.toRadians(180)).mirror();
    private final Pose secondLineEndPose = secondLineStartPose.withX(19).mirror();
    private final Pose thirdLineStartPose = new Pose(42, 36, Math.toRadians(180)).mirror();
    private final Pose thirdLineEndPose = thirdLineStartPose.withX(19).mirror();
    private final Pose openGatePose = new Pose(14, 64, Math.toRadians(180)).mirror();
    private final Pose leavePose = new Pose(22, 96, Math.toRadians(230)).mirror();

    private PathChain scorePreload, secondLineStart, secondLineEnd, openGate, score2;
    private PathChain firstLineStart, firstLineEnd, score3, thirdLineStart, thirdLineEnd, score4, leave;

    @Override
    public void onInit() {
        follower().setStartingPose(startPose);

        scorePreload = follower().pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();

        secondLineStart = follower().pathBuilder()
                .addPath(new BezierCurve(
                        scorePose,
                        new Pose(60, 57),
                        secondLineStartPose
                ))
                .setLinearHeadingInterpolation(scorePose.getHeading(), secondLineStartPose.getHeading())
                .build();

        secondLineEnd = follower().pathBuilder()
                .addPath(new BezierLine(secondLineStartPose, secondLineEndPose))
                .setConstantHeadingInterpolation(secondLineEndPose.getHeading())
                .build();

        openGate = follower().pathBuilder()
                .addPath(new BezierCurve(
                        secondLineEndPose,
                        new Pose(40, 65),
                        openGatePose
                ))
                .setLinearHeadingInterpolation(secondLineEndPose.getHeading(), openGatePose.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        openGatePose,
                        new Pose(68, 52),
                        scorePose
                ))
                .setLinearHeadingInterpolation(openGatePose.getHeading(), scorePose.getHeading())
                .build();

        firstLineStart = follower().pathBuilder()
                .addPath(new BezierCurve(
                        scorePose,
                        new Pose(57, 80),
                        firstLineStartPose
                ))
                .setLinearHeadingInterpolation(scorePose.getHeading(), firstLineStartPose.getHeading())
                .build();

        firstLineEnd = follower().pathBuilder()
                .addPath(new BezierLine(firstLineStartPose, firstLineEndPose))
                .setConstantHeadingInterpolation(firstLineEndPose.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        firstLineEndPose,
                        new Pose(41, 92),
                        scorePose
                ))
                .setLinearHeadingInterpolation(firstLineEndPose.getHeading(), scorePose.getHeading())
                .build();

        thirdLineStart = follower().pathBuilder()
                .addPath(new BezierCurve(
                        scorePose,
                        new Pose(65, 33),
                        thirdLineStartPose
                ))
                .setLinearHeadingInterpolation(scorePose.getHeading(), thirdLineStartPose.getHeading())
                .build();

        thirdLineEnd = follower().pathBuilder()
                .addPath(new BezierLine(thirdLineStartPose, thirdLineEndPose))
                .setConstantHeadingInterpolation(thirdLineEndPose.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        thirdLineEndPose,
                        new Pose(44, 58),
                        scorePose
                ))
                .setLinearHeadingInterpolation(thirdLineEndPose.getHeading(), scorePose.getHeading())
                .build();

        leave = follower().pathBuilder()
                .addPath(new BezierLine(scorePose, leavePose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), leavePose.getHeading())
                .build();
    }
    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                Catapults.INSTANCE.down,
                new FollowPath(scorePreload),
                new Delay(1.0),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(
                        new FollowPath(secondLineStart),
                        Intake.INSTANCE.run
                ),
                new FollowPath(secondLineEnd),
                new Delay(1.0),
                new ParallelGroup(
                        new FollowPath(openGate),
                        Intake.INSTANCE.stop
                ),
                new FollowPath(score2),
                Catapults.INSTANCE.shoot3
        ).schedule();
    }
}
