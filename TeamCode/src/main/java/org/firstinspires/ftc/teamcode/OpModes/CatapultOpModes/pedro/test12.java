package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes.pedro;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.*;

import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name="test12 (9 classified)")
public class test12 extends NextFTCOpMode {
    public test12(){
        addComponents(
                new PedroComponent(Constants::createFollower),
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose startPose = new Pose(22,125, Math.toRadians(144));
    private final Pose scorePose = new Pose(36, 112.5, Math.toRadians(138));
    private final Pose firstLinePose = new Pose(18, 84, Math.toRadians(180));
    private final Pose secondLinePose = new Pose(18, 60, Math.toRadians(180));
    private final Pose thirdLinePose = new Pose(18, 36, Math.toRadians(180));

    private PathChain scorePreload, intake1, score2, intake2, score3, intake3, score4;

    @Override
    public void onInit(){
        follower().setStartingPose(startPose);

        scorePreload = follower().pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();

        intake1 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        scorePose,
                        new Pose(77, 74.7),
                        firstLinePose
                ))
                .setLinearHeadingInterpolation(scorePose.getHeading(), firstLinePose.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        firstLinePose,
                        new Pose(46.5, 96.5),
                        scorePose
                ))
                .setLinearHeadingInterpolation(firstLinePose.getHeading(), scorePose.getHeading())
                .build();

        intake2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        scorePose,
                        new Pose(77, 50.7),
                        secondLinePose
                ))
                .setLinearHeadingInterpolation(scorePose.getHeading(), secondLinePose.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        secondLinePose,
                        new Pose(40.5, 76.4),
                        scorePose
                ))
                .setLinearHeadingInterpolation(secondLinePose.getHeading(), scorePose.getHeading())
                .build();

        intake3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        scorePose,
                        new Pose(91.3, 27.6),
                        thirdLinePose
                ))
                .setLinearHeadingInterpolation(scorePose.getHeading(), thirdLinePose.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        thirdLinePose,
                        new Pose(46.6, 59.8),
                        scorePose
                ))
                .setLinearHeadingInterpolation(thirdLinePose.getHeading(), scorePose.getHeading())
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                new FollowPath(scorePreload),
                Catapults.INSTANCE.shootArtifact,
                new ParallelGroup(
                        new FollowPath(intake1),
                        Intake.INSTANCE.intakeArtifactAuto()
                ),
                new ParallelGroup(
                        new FollowPath(score2),
                        Intake.INSTANCE.stopIntake()
                ),
                Catapults.INSTANCE.shootArtifact,
                new ParallelGroup(
                        new FollowPath(intake2),
                        Intake.INSTANCE.intakeArtifactAuto()
                ),
                new ParallelGroup(
                        new FollowPath(score3),
                        Intake.INSTANCE.stopIntake()
                ),
                Catapults.INSTANCE.shootArtifact,
                new ParallelGroup(
                        new FollowPath(intake3),
                        Intake.INSTANCE.intakeArtifactAuto()
                ),
                new ParallelGroup(
                        new FollowPath(score4),
                        Intake.INSTANCE.stopIntake()
                ),
                Catapults.INSTANCE.shootArtifact
        ).schedule();
    }
}
