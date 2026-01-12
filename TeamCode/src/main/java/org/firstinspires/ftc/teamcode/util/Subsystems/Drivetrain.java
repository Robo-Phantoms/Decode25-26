package org.firstinspires.ftc.teamcode.util.Subsystems;


import org.jetbrains.annotations.NotNull;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.driving.DifferentialTankDriverControlled;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

public class Drivetrain implements Subsystem {
    public static final Drivetrain INSTANCE = new Drivetrain();

    private Drivetrain() {
    }

    private MotorEx leftFront = new MotorEx("leftFront").reversed();
    private MotorEx rightFront = new MotorEx("rightFront");
    private MotorEx leftBack = new MotorEx("leftBack").reversed();
    private MotorEx rightBack = new MotorEx("rightBack");

    public double POWER = -1.0;

    public Command drive = new MecanumDriverControlled(
            leftFront,
            rightFront,
            leftBack,
            rightBack,
            Gamepads.gamepad1().leftStickY().negate(),
            Gamepads.gamepad1().leftStickX(),
            Gamepads.gamepad1().rightStickX()
    ).requires(this);

    public Command strafeRight = new LambdaCommand("strafe right")
            .setStart(() -> setDtPowers(-POWER, POWER, POWER, -POWER))
            .setStop(interrupted -> stopMotors())
            .setInterruptible(true)
            .requires(this);

    public Command strafeLeft = new LambdaCommand("strafe left")
            .setStart(() -> setDtPowers(POWER, -POWER, -POWER, POWER))
            .setStop(interrupted -> stopMotors())
            .setInterruptible(true)
            .requires(this);

    public Command forward = new LambdaCommand("forward")
            .setStart(() -> setDtPowers(POWER, POWER, POWER, POWER))
            .setStop(interrupted -> stopMotors())
            .requires(this);

    public Command backward = new LambdaCommand("backward")
            .setStart(() -> setDtPowers(-POWER, -POWER, -POWER, -POWER))
            .setStop(interrupted -> stopMotors())
            .requires(this);

    /*public InstantCommand turnTo(MecanumDrive drive, Rotation2d angleToTurnTo){
        return new InstantCommand("Turn-to" + angleToTurnTo, () -> {
            Rotation2d heading = drive.getPose().heading;
            new Turn(
                    drive,
                    new TimeTurn(drive.getPose(), angleToTurnTo.minus(heading), drive.defaultTurnConstraints)
            ).requires(this).schedule();
        });
    }*/

    public void setDtPowers(double lfPower, double rfPower, double lbPower, double rbPower) {
        leftFront.setPower(lfPower);
        rightFront.setPower(rfPower);
        leftBack.setPower(lbPower);
        rightBack.setPower(rbPower);
    }

    public void stopMotors(){
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
    }

    @Override
    @NotNull
    public Command getDefaultCommand() {
        return drive;
    }
}
