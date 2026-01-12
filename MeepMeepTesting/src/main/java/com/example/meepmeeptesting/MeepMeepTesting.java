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
        final Pose2d firstLineEndPose = new Pose2d(-12, -49, Math.toRadians(270));
        final Pose2d secondLineStartPose = new Pose2d(12, -22, Math.toRadians(270));
        final Pose2d secondLineEndPose = new Pose2d(12, -49, Math.toRadians(270));

        final Pose2d thirdLineStartPose = new Pose2d(36, -29, Math.toRadians(270));
        final Pose2d thirdLineEndPose = new Pose2d(36, -49, Math.toRadians(270));

        final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(0));
        final Pose2d openGateStartPose = new Pose2d(7,-35,Math.toRadians(230));
        final Pose2d openGateEndPose = new Pose2d(2.5, -55, Math.toRadians(230));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(startPose.position, startPose.heading))
                .splineToLinearHeading(scorePose, scorePose.heading)
                .splineToLinearHeading(secondLineStartPose, Math.toRadians(270))
                .lineToYSplineHeading(secondLineEndPose.position.y, Math.toRadians(270))
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .setReversed(true)
                .splineToLinearHeading(openGateStartPose, Math.toRadians(270))
                .lineToYSplineHeading(openGateEndPose.position.y, Math.toRadians(220))
                .splineToLinearHeading(new Pose2d(openGateEndPose.position.x, -25, Math.toRadians(220)), Math.toRadians(220))
                .splineToSplineHeading(scorePose, scorePose.heading)
                .waitSeconds(0.001)
                .splineToLinearHeading(firstLineStartPose, Math.toRadians(270))
                .lineToYSplineHeading(firstLineEndPose.position.y, Math.toRadians(270))
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToYSplineHeading(thirdLineEndPose.position.y, Math.toRadians(270))
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