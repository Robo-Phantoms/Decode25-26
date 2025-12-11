package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        final Pose2d startPose = new Pose2d(-51,48, Math.toRadians(127));
        final Pose2d scorePose = new Pose2d(-38, 34.5, Math.toRadians(127));
        final Pose2d firstLineStartPose = new Pose2d(-8, 22, Math.toRadians(90));
        final Pose2d secondLineStartPose = new Pose2d(18, 22, Math.toRadians(90));
        final Pose2d thirdLineStartPose = new Pose2d(43, 22, Math.toRadians(90));
        final Pose2d leavePose = new Pose2d(2, 38, Math.toRadians(127));
        final double START_TANGENT = 90;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(startPose.position, startPose.heading))
                .strafeToLinearHeading(scorePose.position, scorePose.heading)

                        .setTangent(START_TANGENT)
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)

                .lineToY(44)

                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)


                .setTangent(START_TANGENT)
                .splineToSplineHeading(secondLineStartPose, secondLineStartPose.heading)

                .lineToY(44)

                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)


                .setTangent(START_TANGENT)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)

                .lineToY(46)

                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)

                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}