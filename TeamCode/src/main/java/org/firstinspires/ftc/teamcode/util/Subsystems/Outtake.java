package org.firstinspires.ftc.teamcode.util.Subsystems;



import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Outtake implements Subsystem {
    public static Outtake INSTANCE = new Outtake();
    private Outtake() {}

    private MotorEx flywheelLeft = new MotorEx("flywheelLeft").reversed();
    private MotorEx flywheelRight = new MotorEx("flywheelRight");
    private MotorGroup flyWheelOuttake = new MotorGroup(flywheelLeft, flywheelRight);
    public static double kp, ki, kd;
    public static double kv, ka, ks;
    public static double targetVel;
    private ControlSystem controller = ControlSystem.builder()
            .velPid(kp, ki, kd)
            .basicFF(kv, ka, ks)
            .build();

    //public Command shootArtifact = new RunToVelocity(controller, targetVel).requires(this);
    //public Command stopShooting = new RunToVelocity(controller, 0).requires(this);

    public Command shootArtifact = new SetPower(flyWheelOuttake, 0.7).requires(this);
    public Command stopShooting = new SetPower(flyWheelOuttake, 0).requires(this);

    @Override
    public void periodic(){
        flyWheelOuttake.setPower(controller.calculate(flyWheelOuttake.getState()));
    }

}
