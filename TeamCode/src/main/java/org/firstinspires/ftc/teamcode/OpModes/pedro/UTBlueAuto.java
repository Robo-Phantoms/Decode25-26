package org.firstinspires.ftc.teamcode.OpModes.pedro;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "UT9Blue")

public class UTBlueAuto extends NextFTCOpMode {
    public UTBlueAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private final Pose start = new Pose(23, 126, Math.toRadians(142));
    private final Pose score = new Pose(37, 114, Math.toRadians(142));
    private final Pose line2Start = new Pose(48, 57, Math.toRadians(180));
    private final Pose line2End = new Pose(24, 57, Math.toRadians(180));
    private final Pose openGate = new Pose(27, 78, Math.toRadians(180));
    private final Pose line1Start = new Pose(48, 82, Math.toRadians(180));
    private final Pose line1End = new Pose(27, 82, Math.toRadians(180));
    private final Pose line3Start = new Pose(48, 33, Math.toRadians(180));
    private final Pose line3End = new Pose(24, 33, Math.toRadians(180));
    private final Pose leavePose = new Pose(43, 127, Math.toRadians(142));

    private PathChain score1, line1StartPath, line1EndPath, gate1, score2, line2StartPath, line2EndPath, gate2, score3, line3StartPath, line3EndPath, score4, leave;

    @Override
    public void onInit(){
        follower().setStartingPose(start);

        score1 = follower().pathBuilder()
                .addPath(new BezierLine(start, score))
                .setLinearHeadingInterpolation(start.getHeading(), score.getHeading())
                .build();

        line1StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(60, 82), line1Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line1Start.getHeading())
                .build();

        line1EndPath = follower().pathBuilder()
                .addPath(new BezierLine(line1Start, line1End))
                .setConstantHeadingInterpolation(line1End.getHeading())
                .build();

        gate1 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line1End, new Pose(35, 77), openGate
                ))
                .setLinearHeadingInterpolation(line1End.getHeading(), openGate.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        openGate, new Pose(42, 98), score
                ))
                .setLinearHeadingInterpolation(openGate.getHeading(), score.getHeading())
                .build();

        line2StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(64, 59), line2Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line2Start.getHeading())
                .build();

        line2EndPath = follower().pathBuilder()
                .addPath(new BezierLine(line2Start, line2End))
                .setConstantHeadingInterpolation(line2End.getHeading())
                .build();

        gate2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                    line2End, new Pose(40, 77), openGate
                ))
                .setLinearHeadingInterpolation(line2End.getHeading(), openGate.getHeading())
                .build();

        score3 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        openGate, new Pose(42, 98), score
                ))
                .setLinearHeadingInterpolation(line2End.getHeading(), score.getHeading())
                .build();

        line3StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(65, 33), line3Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line3Start.getHeading())
                .build();

        line3EndPath = follower().pathBuilder()
                .addPath(new BezierLine(line3Start, line3End))
                .setConstantHeadingInterpolation(line3End.getHeading())
                .build();

        score4 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line3End, new Pose(53, 67), score
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
        /*button(() -> Intake.INSTANCE.getCount() > 3)
                .whenTrue(new SequentialGroup(Intake.INSTANCE.reverse, new Delay(0.5), Intake.INSTANCE.resetCount).setInterruptible(false));*/

        new SequentialGroup(
                Catapults.INSTANCE.down,
                new FollowPath(score1),
                new Delay(0.25),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())), //3
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line1StartPath)),
                new FollowPath(line1EndPath),
                new FollowPath(gate1),
                new Delay(1.0),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score2)),
                Intake.INSTANCE.stop,
                new Delay(0.25),
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(1.0),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(line2StartPath)),
                new FollowPath(line2EndPath),
                new FollowPath(gate2),
                new Delay(1.0),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score3)),
                Intake.INSTANCE.stop,
                new Delay(0.25),
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(1.0),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),
                new ParallelGroup(new FollowPath(line3StartPath), Intake.INSTANCE.run),
                new FollowPath(line3EndPath),
                new ParallelGroup(Intake.INSTANCE.run, new FollowPath(score4)),
                Intake.INSTANCE.stop,
                new Delay(0.25),
                Catapults.INSTANCE.stabilize,
                new Delay(0.1),
                Catapults.INSTANCE.stabilize,
                new Delay(1.0),
                new ParallelGroup(Catapults.INSTANCE.shoot3, new InstantCommand(() -> Intake.INSTANCE.resetCount())),
                new FollowPath(leave)

                ).schedule();
    }
}
