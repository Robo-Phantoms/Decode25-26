package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Flywheels implements Subsystem {
    public static Flywheels INSTANCE = new Flywheels();
    private Flywheels(){}

    private MotorEx flywheelLeft = new MotorEx("flywheelLeft").reversed();
    private MotorEx flywheelRight = new MotorEx("flywheelRight");
    private MotorGroup flyWheelOuttake = new MotorGroup(flywheelRight, flywheelLeft);

    private ControlSystem controller = ControlSystem.builder()
            .velPid(0.0009, 0 , 0)
            .basicFF(0.00031, 0, 0)
            .build();

    public Command shootArtifact = new RunToVelocity(controller, 1850).requires(this);
    public Command stopShooting = new RunToVelocity(controller, 0).requires(this);

    @Override
    public void periodic(){
        flyWheelOuttake.setPower(controller.calculate(flyWheelOuttake.getState()));
    }
}
