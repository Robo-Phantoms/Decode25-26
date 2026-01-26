package org.firstinspires.ftc.teamcode.OpModes.pedro;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;
import static org.firstinspires.ftc.teamcode.util.pedroPathing.Poses.LM4RedPoses.*;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "LM4Red")
public class LM4RedCatapultAuto extends NextFTCOpMode {
    public LM4RedCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }

    private PathChain score1, line2, score2, open, score3, line1, score4, line3, score5, leave;

    @Override
    public void onInit(){
        follower().setStartingPose(start);
        buildPaths();
    }

    @Override
    public void onStartButtonPressed(){
        //button(() -> Intake.INSTANCE.getCount() > 3).whenBecomesTrue(Intake.INSTANCE.reverse);

        new SequentialGroup(
                Catapults.INSTANCE.down,
                new FollowPath(score1),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line2)),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score2)),
                new Delay(0.2),
                Catapults.INSTANCE.shoot3,
                new FollowPath(open),
                Intake.INSTANCE.run,
                new Delay(2.0),
                /*new ParallelDeadlineGroup(
                        new Delay(2.0),
                        new WaitUntil(() -> Intake.INSTANCE.getCount() == 3)
                ),*/
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score3)),
                new Delay(0.2),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line1)),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score4)),
                new Delay(0.2),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line3)),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score5)),
                new Delay(0.2),
                Catapults.INSTANCE.shoot3,
                new FollowPath(leave)
        ).schedule();
    }

    public void buildPaths(){
        score1 = follower().pathBuilder()
                .addPath(new BezierLine(start, score))
                .setConstantHeadingInterpolation(score.getHeading())
                .build();

        line2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(84, 60).mirror(), line2Start
                ))
                .setTangentHeadingInterpolation()
                .addPath(new BezierLine(line2Start, line2End))
                .setConstantHeadingInterpolation(line2End.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line2End, new Pose(65, 75).mirror(), score
                ))
                .setLinearHeadingInterpolation(line2End.getHeading(), score.getHeading())
                .build();

        open = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(65, 60).mirror(), openGate
                ))
                .setLinearHeadingInterpolation(score.getHeading(), openGate.getHeading())
                .addPath(new BezierCurve(
                        openGate, new Pose(26, 57).mirror(), openGateIntake
                ))
                .setLinearHeadingInterpolation(openGate.getHeading(), openGateIntake.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        openGateIntake, new Pose(72, 67).mirror(), score
                ))
                .setLinearHeadingInterpolation(openGateIntake.getHeading(), score.getHeading())
                .build();

        line1 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(79, 80).mirror(), line1Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line1Start.getHeading())
                .addPath(new BezierLine(line1Start, line1End))
                .setConstantHeadingInterpolation(line1End.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line1End, new Pose(39, 93).mirror(), score
                ))
                .setLinearHeadingInterpolation(line1End.getHeading(), score.getHeading())
                .build();

        line3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(85, 31).mirror(), line3Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line3Start.getHeading())
                .addPath(new BezierLine(line3Start, line3End))
                .setConstantHeadingInterpolation(line3End.getHeading())
                .build();

        score5 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line3End, new Pose(42, 76).mirror(), score
                ))
                .setLinearHeadingInterpolation(line3End.getHeading(), score.getHeading())
                .build();

        leave = follower().pathBuilder()
                .addPath(new BezierLine(score, leavePose))
                .setLinearHeadingInterpolation(score.getHeading(), leavePose.getHeading())
                .build();
    }
}