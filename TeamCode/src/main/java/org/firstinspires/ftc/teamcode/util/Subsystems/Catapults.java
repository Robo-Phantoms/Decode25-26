package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.Controllable;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;

public class Catapults implements Subsystem {
    public static Catapults INSTANCE = new Catapults();
    private Catapults(){}
    private MotorEx catapultRight = new MotorEx("launcher").brakeMode();
    private MotorEx catapultLeft = new MotorEx("launcher2").brakeMode().reversed();
    private Controllable catapults = new VoltageCompensatingMotor(
            new MotorGroup(catapultRight, catapultLeft)
    );

    public Command up = instant(() -> catapults.setPower(1.0)).requires(this);
    public Command down = instant(() -> catapults.setPower(-1.0)).requires(this);
    public Command stop = instant(() -> catapults.setPower(0.0)).requires(this);
    public Command voltageCompUp = new SequentialGroup(up, new Delay(0.5), stop).requires(this);
    public Command shoot3 = new SequentialGroup(instant(() -> catapults.setPower(0.9)), new Delay(0.2), down).requires(this);
    public Command shoot2 = new SequentialGroup(instant(() -> catapults.setPower(0.87)), new Delay(0.2), down).requires(this);
    public Command shoot1 = new SequentialGroup(instant(() -> catapults.setPower(0.5)), new Delay(0.2), down).requires(this);
    public Command stabilize = new SequentialGroup(instant(() -> catapults.setPower(0.35)), new Delay(0.000005), down);
}