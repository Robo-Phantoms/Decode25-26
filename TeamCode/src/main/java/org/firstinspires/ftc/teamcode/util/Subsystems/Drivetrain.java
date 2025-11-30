package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.util.localizers.MecanumDrive;
import org.jetbrains.annotations.NotNull;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.units.Angle;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.driving.DifferentialTankDriverControlled;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import dev.nextftc.hardware.impl.MotorEx;

public class Drivetrain implements Subsystem {
    public static final Drivetrain INSTANCE = new Drivetrain();
    private Drivetrain(){}

    private MotorEx leftFront = new MotorEx("leftFront");
    private MotorEx rightFront = new MotorEx("rightFront").reversed();
    private MotorEx leftBack = new MotorEx("leftBack");
    private MotorEx rightBack = new MotorEx("rightBack").reversed();
    private MotorGroup leftMotors = new MotorGroup(leftFront, leftBack);
    private MotorGroup rightMotors = new MotorGroup(rightFront, rightBack);
    public double POWER = 1.0;

    public DriverControlledCommand startDrive = new DifferentialTankDriverControlled(
            leftMotors, rightMotors, Gamepads.gamepad1().leftStickY(), Gamepads.gamepad1().rightStickY()
    );

    public Command strafeRight = new LambdaCommand("strafe-right")
            .setStart(() -> {
                setDtPowers(-POWER, POWER, POWER, -POWER);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0, 0, 0);
            }).requires(this);

    public Command strafeLeft = new LambdaCommand("strafe-left")
            .setStart(() -> {
                setDtPowers(POWER, -POWER, -POWER, POWER);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0 , 0, 0 );
            }).requires(this);

    public Command forward = new LambdaCommand("forward")
            .setStart(() -> {
                setDtPowers(POWER, POWER, POWER, POWER);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0, 0, 0);
            }).requires(this);

    public Command backward = new LambdaCommand("backward")
            .setStart(() -> {
                setDtPowers(-POWER, -POWER, -POWER, -POWER);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0, 0, 0);
            }).requires(this);

    public Command turnTo(MecanumDrive drive, double kp, double target){
        return new LambdaCommand("Turn-to")
                .setUpdate(() -> {
                    double currentHeading = drive.getPose().heading.toDouble();
                    double error = target - currentHeading;
                    error = Angle.fromRad(error).normalized().inRad;
                    drive.setDrivePowers(new PoseVelocity2d(
                            new Vector2d(0,0),
                            kp * error
                    ));
                })
                .setIsDone(() -> {
                    double currentHeading = drive.getPose().heading.toDouble();
                    double error = target - currentHeading;
                    error = Angle.fromRad(error).normalized().inRad;
                    return Math.abs(error) < Math.toRadians(1.0);
                }).requires(this);
    }

    public void setDtPowers(double lfPower, double rfPower, double lbPower, double rbPower){
        leftFront.setPower(lfPower);
        rightFront.setPower(rfPower);
        leftBack.setPower(lbPower);
        rightBack.setPower(rbPower);
    }

    @Override
    @NotNull
    public Command getDefaultCommand(){
        return startDrive;
    }
}
