package org.firstinspires.ftc.teamcode.OpModes.pedro;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

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
    private final  Pose score = new Pose(35, 116, Math.toRadians(142));
    private final Pose line2Start = new Pose(48, 58, Math.toRadians(180));
    private final Pose line2End = new Pose(20, 58, Math.toRadians(180));
    private final Pose openGate = new Pose(25, 64, Math.toRadians(180));
    private final Pose line1Start = new Pose(48, 82, Math.toRadians(180));
    private final Pose line1End = new Pose(23, 82, Math.toRadians(180));
    private final Pose line3Start = new Pose(48, 35, Math.toRadians(180));
    private final Pose line3End = new Pose(20, 35, Math.toRadians(180));
    private final Pose leavePose = new Pose(32, 72, Math.toRadians(270));

    private PathChain score1, line1StartPath, line1EndPath, gate1, score2;

    @Override
    public void onInit(){
        follower().setStartingPose(start);

        score1 = follower().pathBuilder()
                .addPath(new BezierLine(start, score))
                .setLinearHeadingInterpolation(start.getHeading(), score.getHeading())
                .build();

        line1StartPath = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(67, 83), score
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line1Start.getHeading())
                .build();

        line1EndPath = follower().pathBuilder()
                .addPath(new BezierLine(line1Start, line1End))
                .setConstantHeadingInterpolation(line1End.getHeading())
                .build();

        gate1 = follower().pathBuilder()

                .build();


    }
}
