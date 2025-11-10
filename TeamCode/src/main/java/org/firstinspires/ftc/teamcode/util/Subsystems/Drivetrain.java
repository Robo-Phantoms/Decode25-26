package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
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

    public double power = 0.8;

    public DriverControlledCommand startDrive = new DifferentialTankDriverControlled(
            leftMotors, rightMotors, Gamepads.gamepad1().leftStickY(), Gamepads.gamepad1().rightStickY()
    );

    public Command strafeRight = new LambdaCommand("strafe-right")
            .setStart(() -> {
                setDtPowers(-power, power, power, -power);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0, 0, 0);
            }).requires(this);

    public Command strafeLeft = new LambdaCommand("strafe-left")
            .setStart(() -> {
                setDtPowers(power, -power, -power, power);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0 , 0, 0 );
            }).requires(this);

    public Command forward = new LambdaCommand("forward")
            .setStart(() -> {
                setDtPowers(power, power, power, power);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0, 0, 0);
            }).requires(this);

    public Command backward = new LambdaCommand("backward")
            .setStart(() -> {
                setDtPowers(-power, -power, -power, -power);
            })
            .setStop(interrupted -> {
                setDtPowers(0, 0, 0, 0);
            }).requires(this);

    public void setDtPowers(double lfPower, double rfPower, double lbPower, double rbPower){
        leftFront.setPower(lfPower);
        rightFront.setPower(rfPower);
        leftBack.setPower(lbPower);
        rightBack.setPower(rbPower);
    }
}
