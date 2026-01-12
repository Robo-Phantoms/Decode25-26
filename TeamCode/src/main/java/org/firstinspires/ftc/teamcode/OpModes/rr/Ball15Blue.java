package org.firstinspires.ftc.teamcode.OpModes.rr;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.roadrunner.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "15 blue (hopefully)")
public class Ball15Blue extends NextFTCOpMode {
    public Ball15Blue(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    //private DigitalChannel breakBeam;
    private int count = 0;
    boolean lastDetected = false;

    private final Pose2d startPose = new Pose2d(-51,-48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-36, -34.5, Math.toRadians(233));
    private final Pose2d firstLineStartPose = new Pose2d(-8, -22, Math.toRadians(270));
    private final Pose2d firstLineEndPose = new Pose2d(-8, -45, Math.toRadians(270));
    private final Pose2d secondLineStartPose = new Pose2d(18, -14, Math.toRadians(270));
    private final Pose2d secondLineEndPose = new Pose2d(18, -45, Math.toRadians(270));
    private final Pose2d thirdLineStartPose = new Pose2d(40, -14, Math.toRadians(270));
    private final Pose2d thirdLineEndPose = new Pose2d(40, -45, Math.toRadians(270));
    private final Pose2d leavePose = new Pose2d(2, -38, Math.toRadians(0));
    private final Pose2d openGateStartPose = new Pose2d(7,-45,Math.toRadians(220));
    private final Pose2d openGateEndPose = new Pose2d(2.5, -51, Math.toRadians(220));



    public Command shootBasedOnCount;
    MecanumDrive drive;
    public Command score1, intake1, score2, openGate, score3, intake3, score4, intake4, score5, leave;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);

       /* breakBeam = hardwareMap.get(DigitalChannel.class, "breakBeam");
        breakBeam.setMode(DigitalChannel.Mode.INPUT);
        lastDetected = breakBeam.getState();*/

        Catapults.INSTANCE.down.schedule();

        /*shootBasedOnCount = new LambdaCommand("change catapult powers")
                .setStart(() -> {
                    if (count == 2){
                        Catapults.INSTANCE.shoot2.schedule(); //slower
                    } else if (count == 1) {
                        Catapults.INSTANCE.shoot1.schedule(); // even slower
                    } else{
                        Catapults.INSTANCE.shoot3.schedule();
                    }
                }).setIsDone(() -> true);*/


        score1 = drive.commandBuilder(startPose)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake1 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(secondLineStartPose, Math.toRadians(270))
                .lineToYSplineHeading(secondLineEndPose.position.y, Math.toRadians(270))
                .build();

        score2 = drive.commandBuilder(secondLineEndPose).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        openGate = drive.commandBuilder(scorePose).fresh()
                .setReversed(true)
                .splineToLinearHeading(openGateStartPose, Math.toRadians(270))
                .lineToYSplineHeading(openGateEndPose.position.y, Math.toRadians(220))
                .build();

        score3 = drive.commandBuilder(openGateEndPose).fresh()
                .lineToYLinearHeading(-25, Math.toRadians(220))
                .splineToSplineHeading(scorePose, scorePose.heading)
                .build();

        intake3 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(firstLineStartPose, Math.toRadians(270))
                .lineToYSplineHeading(firstLineEndPose.position.y, Math.toRadians(270))
                .build();

        score4 = drive.commandBuilder(firstLineEndPose).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        intake4 = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToYSplineHeading(thirdLineEndPose.position.y, Math.toRadians(270))
                .build();

        score5 = drive.commandBuilder(thirdLineEndPose).fresh()
                .setReversed(true)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .build();

        leave = drive.commandBuilder(scorePose).fresh()
                .strafeToLinearHeading(leavePose.position, leavePose.heading)
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                score1,
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(intake1, Intake.INSTANCE.run),
                new Delay(0.5),
                new ParallelGroup(score2, Intake.INSTANCE.stop),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(openGate, Intake.INSTANCE.run),
                //new WaitUntil(() -> count >= 3).endAfter(2.0),
                new ParallelGroup(score3, Intake.INSTANCE.stop),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(intake3, Intake.INSTANCE.run),
                new Delay(0.5),
                new ParallelGroup(score4, Intake.INSTANCE.stop),
                Catapults.INSTANCE.shoot3,
                new ParallelGroup(intake4, Intake.INSTANCE.run),
                new Delay(0.5),
                new ParallelGroup(score5, Intake.INSTANCE.stop),
                Catapults.INSTANCE.shoot3,
                leave
        ).schedule();
    }

    /*@Override
    public void onUpdate(){
        boolean detected = breakBeam.getState();
        if (detected && !lastDetected) {
            count++;
        }

        lastDetected = detected;

        telemetry.addLine(detected ? "object detected" : "no object detected");
        telemetry.addData("count", count);
        telemetry.update();
    }*/
}