package org.firstinspires.ftc.teamcode.util.Subsystems;

import org.jetbrains.annotations.NotNull;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

public class Drivetrain implements Subsystem {
    public static final Drivetrain INSTANCE = new Drivetrain();
    private Drivetrain(){}

    private MotorEx leftFront = new MotorEx("leftFront").brakeMode();
    private MotorEx rightFront = new MotorEx("rightFront").brakeMode().reversed();
    private MotorEx leftBack = new MotorEx("leftBack").brakeMode();
    private MotorEx rightBack = new MotorEx("rightBack").brakeMode().reversed();

    public DriverControlledCommand startDrive = new MecanumDriverControlled(
            leftFront,
            rightFront,
            leftBack,
            rightBack,
            Gamepads.gamepad1().leftStickY().negate(),
            Gamepads.gamepad1().leftStickX(),
            Gamepads.gamepad1().rightStickX()
    );

    /*public InstantCommand turnTo(MecanumDrive drive, Rotation2d angleToTurnTo){
        return new InstantCommand("Turn-to" + angleToTurnTo, () -> {
            Rotation2d heading = drive.getPose().heading;
            new Turn(
                    drive,
                    new TimeTurn(drive.getPose(), angleToTurnTo.minus(heading), drive.defaultTurnConstraints)
            ).requires(this).schedule();
        });
    }*/

    @Override
    @NotNull
    public Command getDefaultCommand(){
        return startDrive;
    }
}
