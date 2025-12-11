package org.firstinspires.ftc.teamcode.util.Subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
    private MotorEx catapultRight = new MotorEx("launcher");
    private MotorEx catapultLeft = new MotorEx("launcher2");
    private Controllable catapults = new VoltageCompensatingMotor(
            new MotorGroup(catapultRight, catapultLeft)
    );

    @Override
    public void initialize(){
        catapultRight.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapultLeft.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapultLeft.getMotor().setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Command catapultsUp = new SetPower(catapults, 1.0).requires(this).named("Catapult Up");
    public Command catapultsDown = new SetPower(catapults, -1.0).requires(this).named("Catapult Down");
    public Command Stop = new SetPower(catapults, 0.0).requires(this).named("Catapult Stop");
    public Command shootArtifact = new SequentialGroup(catapultsUp, new Delay(0.2), catapultsDown);
    public Command voltageCompensatingCatapultsUp = new SequentialGroup(catapultsUp, new Delay(0.5), Stop);
}