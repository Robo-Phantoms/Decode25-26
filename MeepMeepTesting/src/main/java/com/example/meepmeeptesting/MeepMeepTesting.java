package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(75, 75, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(62, 10, 0))
                .splineToLinearHeading(new Pose2d(new Vector2d(53, 15), Math.toRadians(-24)), Math.toRadians(-24))
                .waitSeconds(1.7)
                .turnTo(-140)
                .strafeTo(new Vector2d(56, 58))
                .strafeTo(new Vector2d(58, 58))
                .strafeTo(new Vector2d(56, 50))
                .strafeTo(new Vector2d(53, 15))
                .turnTo(Math.toRadians(-24))
                .waitSeconds(1.7)
                .turnTo(-140)
                .strafeTo(new Vector2d(60, 60))
                .strafeTo(new Vector2d(53, 15))
                .turnTo(Math.toRadians(-24))
                .waitSeconds(1.7)
                .turnTo(-140)
                .strafeTo(new Vector2d(58, 58))
                .strafeTo(new Vector2d(53, 15))
                .waitSeconds(1.7)
                .turnTo(Math.toRadians(-24))
                .waitSeconds(1.7)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_BLACK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}