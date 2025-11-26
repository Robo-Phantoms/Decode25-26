package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

public class LM2BlueCatapultAuto extends NextFTCOpMode {
    public LM2BlueCatapultAuto(){
        addComponents(
                new SubsystemComponent(Catapults.INSTANCE, Intake.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }

    private final Pose2d startPose = new Pose2d(-51, -48, Math.toRadians(230));
    private final Pose2d scorePose = new Pose2d(-39.1, -33.5, Math.toRadians(233));
    private final Pose2d firstLineStartPose = new Pose2d(-8, -22, Math.toRadians(270));
    private final Pose2d secondLineStartPose = new Pose2d(16, -22, Math.toRadians(270));
    private final Pose2d thirdLineStartPose = new Pose2d(41, -22, Math.toRadians(270));
    private final Pose2d squareIntakePose = new Pose2d(61, -44, Math.toRadians(270));

    public Command Auto;
    public MecanumDrive drive;

    @Override
    public void onInit(){
        drive = new MecanumDrive(hardwareMap, startPose);
        Catapults.INSTANCE.catapultsDown.schedule();

        Auto = drive.commandBuilder()
                //1st cycle
                .strafeToLinearHeading(scorePose.position, scorePose.heading)
                .stopAndAdd(Catapults.INSTANCE.shootArtifact.and(Intake.INSTANCE.intakeArtifactAuto()))
                .splineToLinearHeading(firstLineStartPose, firstLineStartPose.heading)
                .lineToY(-44)
                .stopAndAdd(Intake.INSTANCE.stopIntake())
                //2nd cycle
                .splineToLinearHeading(scorePose, scorePose.heading)
                .stopAndAdd(new SequentialGroup(new ParallelGroup(Catapults.INSTANCE.steadyArtifacts, Intake.INSTANCE.intakeArtifactAuto()), Catapults.INSTANCE.shootArtifact))
                .splineToLinearHeading(secondLineStartPose, secondLineStartPose.heading)
                .lineToY(-44)
                .stopAndAdd(Intake.INSTANCE.stopIntake())
                //third cycle
                .splineToLinearHeading(scorePose, scorePose.heading)
                .stopAndAdd(new SequentialGroup(new ParallelGroup(Catapults.INSTANCE.steadyArtifacts, Intake.INSTANCE.intakeArtifactAuto()), Catapults.INSTANCE.shootArtifact))
                .splineToLinearHeading(thirdLineStartPose, thirdLineStartPose.heading)
                .lineToY(-44)
                .stopAndAdd(Intake.INSTANCE.stopIntake())
                //4th cycle
                .splineToLinearHeading(scorePose, scorePose.heading)
                .stopAndAdd(new SequentialGroup(new ParallelGroup(Catapults.INSTANCE.steadyArtifacts, Intake.INSTANCE.intakeArtifactAuto()), Catapults.INSTANCE.shootArtifact))
                .splineToLinearHeading(squareIntakePose, squareIntakePose.heading)
                .splineToLinearHeading(scorePose, scorePose.heading)
                .stopAndAdd(new SequentialGroup(new ParallelGroup(Catapults.INSTANCE.steadyArtifacts, Intake.INSTANCE.intakeArtifactAuto()), Catapults.INSTANCE.shootArtifact))
                .build();
    }
}
