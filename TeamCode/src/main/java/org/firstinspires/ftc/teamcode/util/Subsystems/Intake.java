package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.Controllable;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;

public class Intake implements Subsystem {
    public static Intake INSTANCE = new Intake();
    private Intake() {}
    private MotorEx rightIntake = new MotorEx("Intake");
    private Controllable intake = new VoltageCompensatingMotor(rightIntake);

    public Command run(float power){
        return instant("run intake teleop", () -> intake.setPower(power));
    }
    public Command run = instant("run intake auto", () -> intake.setPower(-1.0));
    public Command stop = instant("stop intake", () -> intake.setPower(0));

}