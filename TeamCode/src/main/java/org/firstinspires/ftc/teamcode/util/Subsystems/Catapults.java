package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
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

    public Command up = instant("catapults up", () -> catapults.setPower(1.0));
    public Command down = instant("catapults down", () -> catapults.setPower(-1.0));
    public Command stop = instant("catapults stop", () -> catapults.setPower(0.0));
    public Command shoot = new SequentialGroup(up, new Delay(0.2), down);
    public Command voltageCompUp = new SequentialGroup(up, new Delay(0.5), stop);
}