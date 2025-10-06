package org.firstinspires.ftc.teamcode.util.Subsystems;


import com.acmerobotics.dashboard.config.Config;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;


public class Outtake implements Subsystem {
    public static Outtake INSTANCE = new Outtake();
    private Outtake() {}

    private MotorEx flywheelLeft = new MotorEx("flywheelLeft").reversed();
    private MotorEx flywheelRight = new MotorEx("flywheelRight");
    private MotorGroup flyWheelOuttake = new MotorGroup(flywheelRight, flywheelLeft);
    private ServoEx bumper = new ServoEx("bumper");
    public static double kp = 0.001, ki = 0, kd = 0;
    public static double kv=0.00035, ka=0, ks=0;
    public static double targetVel=1500;
    public static PIDCoefficients coefficients = new PIDCoefficients(kp, ki, kd);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(kv, ka, ks);
    private ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .basicFF(ff)
            .build();

    public Command shootArtifact = new RunToVelocity(controller, targetVel).requires(flyWheelOuttake);
    public Command stopShooting = new RunToVelocity(controller, 0).requires(flyWheelOuttake);
    public Command moveBumper = new SetPosition(bumper, 0.5).requires(bumper);
    public Command reverseBumper = new SetPosition(bumper, 0).requires(bumper);

    @Override
    public void periodic(){
        flyWheelOuttake.setPower(controller.calculate(flyWheelOuttake.getState()));
        ActiveOpMode.telemetry().addData("currentVel", flyWheelOuttake.getVelocity());
        ActiveOpMode.telemetry().update();
    }

}
