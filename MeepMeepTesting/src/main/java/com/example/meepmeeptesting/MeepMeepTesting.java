package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
        final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(233));
        final Pose2d firstLineStartPose = new Pose2d(-12, -25, Math.toRadians(270));
        final Pose2d secondLineStartPose = new Pose2d(12, -25, Math.toRadians(270));
        final Pose2d thirdLineStartPose = new Pose2d(36, -25, Math.toRadians(270));
        final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(230));
        final Pose2d openGatePose = new Pose2d(2,-55,Math.toRadians(270));
        final double START_TANGENT = 90;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(startPose.position, startPose.heading))
                .splineToLinearHeading(scorePose, scorePose.heading)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .lineToY(-49)
                .splineToLinearHeading(openGatePose, openGatePose.heading)
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .lineToY(-49)
                .setReversed(true)
                .splineToLinearHeading(scorePose,scorePose.heading)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToY(-49)
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