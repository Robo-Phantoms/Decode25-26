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

    private MotorEx flywheelLeft = new MotorEx("flywheelLeft");
    private MotorEx flywheelRight = new MotorEx("flywheelRight").reversed();
    private MotorGroup flyWheelOuttake = new MotorGroup(flywheelRight, flywheelLeft);

    private ControlSystem controller = ControlSystem.builder()
            .velPid(0.000001, 0 , 0.0001)
            .basicFF(0.000359, 0, 0)
            .build();

    public Command startShooting = new RunToVelocity(controller, 1400).requires(this);
    public Command stopShooting = new RunToVelocity(controller, 0).requires(this);

    @Override
    public void periodic(){
        flyWheelOuttake.setPower(controller.calculate(flyWheelOuttake.getState()));
    }
}
