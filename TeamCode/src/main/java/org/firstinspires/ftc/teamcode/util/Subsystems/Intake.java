package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem {
    public static Intake INSTANCE = new Intake();
    private Intake() {}
    private MotorEx rightIntake = new MotorEx("Intake");

    public Command intakeArtifact(float power){
        return new SetPower(rightIntake, power);
    }
    public Command intakeArtifact(double power){
        return new SetPower(rightIntake, power);
    }
    public Command stopIntake(){
        return new SetPower(rightIntake, 0.0);
    }
}
