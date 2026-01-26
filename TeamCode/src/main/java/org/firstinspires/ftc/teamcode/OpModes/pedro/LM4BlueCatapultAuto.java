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
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "LM4Blue")
public class LM4BlueCatapultAuto extends NextFTCOpMode {
    public LM4BlueCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }
    private final Pose start = new Pose(23, 126, Math.toRadians(142));
    private final  Pose score = new Pose(35, 116, Math.toRadians(142));
    private final Pose line2Start = new Pose(48, 58, Math.toRadians(180));
    private final Pose line2End = new Pose(20, 58, Math.toRadians(180));
    private final Pose openGate = new Pose(25, 64, Math.toRadians(180));
    private final Pose openGateIntake = new Pose(9, 56.5, Math.toRadians(125));
    private final Pose line1Start = new Pose(48, 82, Math.toRadians(180));
    private final Pose line1End = new Pose(23, 82, Math.toRadians(180));
    private final Pose line3Start = new Pose(48, 35, Math.toRadians(180));
    private final Pose line3End = new Pose(20, 35, Math.toRadians(180));
    private final Pose leavePose = new Pose(32, 72, Math.toRadians(270));

    private PathChain score1, line2StartPath, line2EndPath, score2, open, score3, line1StartPath, line1EndPath,  score4, line3StartPath, line3EndPath, score5, leave;

    @Override
    public void onInit(){
        follower().setStartingPose(start);
        buildPaths();
    }

    @Override
    public void onStartButtonPressed(){
        /*button(() -> Intake.INSTANCE.getCount() > 3)
                .whenTrue(new ParallelDeadlineGroup(new Delay(1.5), Intake.INSTANCE.reverse));*/

        new SequentialGroup(
                Catapults.INSTANCE.down,
                new FollowPath(score1),
                new ParallelGroup(Catapults.INSTANCE.shoot3, Intake.INSTANCE.resetCount), //3

                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line2StartPath)),
                new FollowPath(line2EndPath, true, 0.5),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score2)),
                new ParallelGroup(Catapults.INSTANCE.shoot3, Intake.INSTANCE.resetCount), //6

                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(open)),
                new Delay(1.5),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score3)),
                new ParallelGroup(Catapults.INSTANCE.shoot3, Intake.INSTANCE.resetCount), //9

                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line1StartPath)),
                new FollowPath(line1EndPath, true, 0.5),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score4)),
                new ParallelGroup(Catapults.INSTANCE.shoot3, Intake.INSTANCE.resetCount), //12

                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line3StartPath)),
                new FollowPath(line3EndPath, true, 0.5),
                new ParallelGroup(Intake.INSTANCE.stop, new FollowPath(score5)),
                new ParallelGroup(Catapults.INSTANCE.shoot3, Intake.INSTANCE.resetCount), //15

                new FollowPath(leave)
        ).schedule();
    }

    public void buildPaths(){
        score1 = follower().pathBuilder()
                .addPath(new BezierLine(start, score))
                .setConstantHeadingInterpolation(score.getHeading())
                .build();

        line2StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(84, 60), line2Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line2Start.getHeading())
                .build();

        line2EndPath = follower().pathBuilder()
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

        line1StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(79, 80), line1Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line1Start.getHeading())
                .build();

        line1EndPath = follower().pathBuilder()
                .addPath(new BezierLine(line1Start, line1End))
                .setConstantHeadingInterpolation(line1End.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line1End, new Pose(39, 93), score
                ))
                .setLinearHeadingInterpolation(line1End.getHeading(), score.getHeading())
                .build();

        line3StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(85, 31), line3Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line3Start.getHeading())
                .build();

        line3EndPath = follower().pathBuilder()
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
}