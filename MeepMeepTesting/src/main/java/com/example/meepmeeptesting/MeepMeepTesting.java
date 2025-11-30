package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        final Pose2d startPose = new Pose2d(-51, -48, Math.toRadians(230));
        final Pose2d scorePose = new Pose2d(-39.1, -33.5, Math.toRadians(233));
        final Pose2d firstLineStartPose = new Pose2d(-8, -22, Math.toRadians(270));
        final Pose2d secondLineStartPose = new Pose2d(16, -22, Math.toRadians(270));
        final Pose2d thirdLineStartPose = new Pose2d(41, -22, Math.toRadians(270));
        final Pose2d squareIntakePose = new Pose2d(61, -44, Math.toRadians(270));


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(startPose.position, startPose.heading))
                        .setTangent(230)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                        .setTangent(270)
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .lineToY(-44)
                //2nd cycle
                        .setTangent(230)
                .splineToLinearHeading(scorePose, scorePose.heading)
                        .setTangent(270)
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .lineToY(-44)
                //third cycle
                .setTangent(230)
                .splineToLinearHeading(scorePose, scorePose.heading)
                        .setTangent(270)
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToY(-44)
                //4th cycle
                .setTangent(230)
                .splineToLinearHeading(scorePose, scorePose.heading)
                        .setTangent(270)
                .splineToLinearHeading(squareIntakePose, squareIntakePose.heading)
                //5th cycle
                        .setTangent(230)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}