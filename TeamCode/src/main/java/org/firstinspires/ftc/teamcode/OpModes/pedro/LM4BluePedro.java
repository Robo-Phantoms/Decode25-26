package org.firstinspires.ftc.teamcode.OpModes.pedro;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

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
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "goofygoober")
public class LM4BluePedro extends NextFTCOpMode {
    public LM4BluePedro(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose start = new Pose(23, 126, Math.toRadians(142));
    private final Pose score = new Pose(35, 116, Math.toRadians(142));
    private final Pose line2Start = new Pose(36, 60, Math.toRadians(180));
    private final Pose line2End = new Pose(17, 60, Math.toRadians(180));
    private final Pose openGate = new Pose(16, 17, Math.toRadians(180));
    private final Pose openGateIntake = new Pose(12, 53, Math.toRadians(140));
    private final Pose line1Start = new Pose(36, 84, Math.toRadians(180));
    private final Pose line1End = new Pose(16, 84, Math.toRadians(180));
    private final Pose line3Start = new Pose(36, 36, Math.toRadians(180));
    private final Pose line3End = new Pose(16, 36, Math.toRadians(180));
    private final Pose leavePose = new Pose(32, 72, Math.toRadians(270));

    private PathChain score1, line2, score2, open, score3, line1, score4, line3, score5, leave;

    @Override
    public void onInit(){
        follower().setStartingPose(start);

        score1 = follower().pathBuilder()
                .addPath(new BezierLine(start, score))
                .setConstantHeadingInterpolation(score.getHeading())
                .build();

        line2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(84, 60), line2Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line2Start.getHeading())
                .addPath(new BezierLine(line2Start, line2End))
                .setConstantHeadingInterpolation(line2End.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line2End, new Pose(65, 75), score
                ))
                .setLinearHeadingInterpolation(line2End.getHeading(), score.getHeading())
                .build();

        open = follower().pathBuilder()
                .addPath(new BezierCurve(
                       score, new Pose(65, 60), openGate
                ))
                .setLinearHeadingInterpolation(score.getHeading(), openGate.getHeading())
                .addPath(new BezierCurve(
                        openGate, new Pose(26, 57), openGateIntake
                ))
                .setLinearHeadingInterpolation(openGate.getHeading(), openGateIntake.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        openGateIntake, new Pose(72, 67), score
                ))
                .setLinearHeadingInterpolation(openGateIntake.getHeading(), score.getHeading())
                .build();

        line1 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(79, 80), line1Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line1Start.getHeading())
                .addPath(new BezierLine(line1Start, line1End))
                .setConstantHeadingInterpolation(line1End.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line1End, new Pose(39, 93), score
                ))
                .setLinearHeadingInterpolation(line1End.getHeading(), score.getHeading())
                .build();

        line3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(85, 31), line3Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line3Start.getHeading())
                .addPath(new BezierLine(line3Start, line3End))
                .setConstantHeadingInterpolation(line3End.getHeading())
                .build();

        score5 = follower().pathBuilder()
                .addPath(new BezierCurve(
                       line3End, new Pose(42, 76), score
                ))
                .setLinearHeadingInterpolation(line3End.getHeading(), score.getHeading())
                .build();

        leave = follower().pathBuilder()
                .addPath(new BezierLine(score, leavePose))
                .setLinearHeadingInterpolation(score.getHeading(), leavePose.getHeading())
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        //button(() -> Intake.INSTANCE.getCount() >= 4).whenBecomesTrue(Intake.INSTANCE.overload);

        new SequentialGroup(
                Catapults.INSTANCE.down,
                new FollowPath(score1),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line2)),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score2)),
                Catapults.INSTANCE.shoot3,
                new FollowPath(open),
                Intake.INSTANCE.run,
                new Delay(1.0),
                //new WaitUntil(() -> Intake.INSTANCE.getCount() == 3)
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score3)),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line1)),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score4)),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line3)),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score5)),
                Catapults.INSTANCE.shoot3,
                new FollowPath(leave)
        ).schedule();

    }
}