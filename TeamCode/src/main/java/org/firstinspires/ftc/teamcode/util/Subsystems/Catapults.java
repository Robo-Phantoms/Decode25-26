package org.firstinspires.ftc.teamcode.util.Subsystems;

import androidx.annotation.NonNull;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.Controllable;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;
import dev.nextftc.hardware.powerable.SetPower;

public class Catapults implements Subsystem {
    public static Catapults INSTANCE = new Catapults();
    private Catapults(){}
    private Controllable catapults = new VoltageCompensatingMotor(
            new MotorGroup(
                    new MotorEx("launcher").brakeMode().zeroed(),
                    new MotorEx("launcher2").reversed().brakeMode().zeroed()
            )
    );

    public static PIDCoefficients coefficients = new PIDCoefficients(0,0,0);
    public static GravityFeedforwardParameters ff = new GravityFeedforwardParameters(0,0,0,0);
    double catapultsDownPos = 0;
    public ControlSystem controlSystem = ControlSystem.builder()
            .posPid(coefficients)
            .armFF(ff)
            .build();

    public Command catapultsUp = new SetPower(catapults, 1.0).requires(this).named("Catapult Up");
    public Command catapultsDown = new RunToPosition(controlSystem, catapultsDownPos).named("Catapult Down");
    public Command Stop = new SetPower(catapults, 0.0).requires(this).named("Catapult Stop");
    public Command shootArtifact = new SequentialGroup(catapultsUp, new Delay(0.2), catapultsDown);
    public Command steadyArtifacts = new SequentialGroup(catapultsUp, new Delay(0.02), catapultsDown);

    @Override
    public void periodic(){
        catapults.setPower(controlSystem.calculate(catapults.getState()));
    }


}