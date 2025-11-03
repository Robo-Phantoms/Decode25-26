package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Catapults implements Subsystem {
    public static Catapults INSTANCE = new Catapults();
    private Catapults(){}

    private MotorEx catapult = new MotorEx("launcher").brakeMode();
    private MotorEx catapult_2 = new MotorEx("launcher2").reversed().brakeMode();
    private MotorGroup catapults = new MotorGroup(catapult, catapult_2);

    public Command catapultsUp = new SetPower(catapults, 1.0).requires(this);
    public Command catapultsDown = new SetPower(catapults, -0.5).requires(this);
    public Command Stop = new SetPower(catapults, 0.0).requires(this);



}