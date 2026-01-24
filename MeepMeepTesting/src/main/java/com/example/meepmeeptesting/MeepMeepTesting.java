package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
        final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(230));
        final Pose2d line2 = new Pose2d(12,-25, Math.toRadians(270));
        final Pose2d gateStart = new Pose2d(4, -30, Math.toRadians(270));
        final Pose2d gateEnd = new Pose2d(4, -56, Math.toRadians(220));
        final Pose2d gateIntake = new Pose2d(15, -56, Math.toRadians(270));
        final Pose2d scoreFromGate = new Pose2d(15,-25,Math.toRadians(270));
        final Pose2d line1 = new Pose2d(-12, -25, Math.toRadians(270));
        final Pose2d line3 = new Pose2d(36, -25, Math.toRadians(270));

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(80, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(startPose.position, startPose.heading))
                .splineToLinearHeading(scorePose, scorePose.heading)
                        .setReversed(true)
                .splineToLinearHeading(line2, line2.heading)
                .lineToYSplineHeading(-50, line2.heading)
                .waitSeconds(0.01)
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                        .setReversed(true)
                .splineToLinearHeading(gateStart, gateStart.heading)
                .lineToYSplineHeading(gateEnd.position.y, gateEnd.heading)
                .waitSeconds(0.01)
                .setReversed(true)
                .splineToLinearHeading(gateIntake, gateIntake.heading)
                        .waitSeconds(0.01)
                .lineToYSplineHeading(scoreFromGate.position.y, scoreFromGate.heading)
                        .splineToLinearHeading(scorePose, scorePose.heading)
                        .splineToLinearHeading(line1, line1.heading)
                        .lineToYSplineHeading(-50, Math.toRadians(270))
                        .waitSeconds(0.01)
                        .splineToLinearHeading(scorePose, scorePose.heading)
                .splineToLinearHeading(line3, line3.heading)
                        .lineToYSplineHeading(-50, Math.toRadians(270))
                        .waitSeconds(0.01)
                        .splineToLinearHeading(scorePose, scorePose.heading)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}