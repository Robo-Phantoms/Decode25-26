package org.firstinspires.ftc.teamcode.OpModes.pedro;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;
import static org.firstinspires.ftc.teamcode.util.pedroPathing.Poses.combinedPosesBlue.*;

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

    private PathChain score1, line1, score2;

    @Override
    public void onInit(){
        follower().setStartingPose(start);

        score1 = follower().pathBuilder()
                .addPath(new BezierLine(start, score))
                .setLinearHeadingInterpolation(start.getHeading(), score.getHeading())
                .build();

        line1 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        score, new Pose(67, 83), line1Start
                ))
                .setLinearHeadingInterpolation(score.getHeading(), line1Start.getHeading())
                .addPath(new BezierLine(line1Start, line1End))
                .setConstantHeadingInterpolation(line1End.getHeading())
                .build();

        score2 = follower().pathBuilder()
                .addPath(new BezierCurve(
                        line1End, new Pose(34, 84), score
                ))
                .setLinearHeadingInterpolation(line1End.getHeading(), score.getHeading())
                .build();
    }
}
