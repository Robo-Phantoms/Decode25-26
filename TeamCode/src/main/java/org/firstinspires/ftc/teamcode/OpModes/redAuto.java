package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "redAuto")
public class redAuto extends NextFTCOpMode {
    public redAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51,48, Math.toRadians(40));
    private final Pose2d scorePose = new Pose2d(-40, 33,Math.toRadians(40));
    private final Pose2d firstLineStartPose = new Pose2d(-5, 28, Math.toRadians(90));

    MecanumDrive drive;
    Command firstCycle, firstLineStart, firstLineIntake, secondCycle, secondLineStart, secondLineIntake,thirdLineIntake,thirdLineCycle, thirdLineStart,fourthCycle;
    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        firstCycle = drive.commandBuilder(startPose)
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .build();

        firstLineStart = drive.commandBuilder(scorePose).fresh()
                .splineToLinearHeading(new Pose2d(firstLineStartPose.position, firstLineStartPose.heading), firstLineStartPose.heading)
                .build();

        firstLineIntake = drive.commandBuilder(firstLineStartPose).fresh()
                .lineToY(42)
                .build();

        secondCycle = drive.commandBuilder(new Pose2d(-5, 45, Math.toRadians(90))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(-35,25), Math.toRadians(30)), Math.toRadians(30))
                .build();

        secondLineStart = drive.commandBuilder(new Pose2d(-46, 29, Math.toRadians(30))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(20,34), Math.toRadians(90)), Math.toRadians(90))
                .build();

        secondLineIntake = drive.commandBuilder(new Pose2d(20, 34, Math.toRadians(90)))
                .lineToY(42)
                .build();

        thirdLineIntake = drive.commandBuilder(new Pose2d(44, 34, Math.toRadians(90))).fresh()
                .lineToY(44)
                .build();
        thirdLineCycle = drive.commandBuilder(new Pose2d(-5, 45, Math.toRadians(90))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(-35,25), Math.toRadians(30)), Math.toRadians(30))
                .build();
        thirdLineStart = drive.commandBuilder(new Pose2d(-46, 29, Math.toRadians(30))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(44,34), Math.toRadians(90)), Math.toRadians(90))
                .build();
        fourthCycle =drive.commandBuilder(new Pose2d(44,34, Math.toRadians(90))).fresh()
                .splineToLinearHeading(new Pose2d(new Vector2d(-35,25), Math.toRadians(30)),Math.toRadians(30))
                .build();
    }

    @Override
    public void onStartButtonPressed(){
        new SequentialGroup(
                firstCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                firstLineStart,
                firstLineIntake,
                Intake.INSTANCE.stopIntake(),
                secondCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                secondLineStart,
                secondLineIntake,
                Intake.INSTANCE.stopIntake(),
                thirdLineCycle,
                Catapults.INSTANCE.shootArtifact,
                Intake.INSTANCE.intakeArtifactAuto(),
                thirdLineStart,
                thirdLineIntake,
                Intake.INSTANCE.stopIntake(),
                fourthCycle,
                Catapults.INSTANCE.shootArtifact
        ).schedule();

    }
}
