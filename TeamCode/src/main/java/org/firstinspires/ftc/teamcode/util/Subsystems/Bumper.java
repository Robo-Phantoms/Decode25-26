package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Bumper implements Subsystem {
    public static final Bumper INSTANCE = new Bumper();
    private Bumper(){}
    private ServoEx bumper = new ServoEx("bumper");

    public Command moveBumper = new SetPosition(bumper, 0.8);
    public Command reverseBumper = new SetPosition(bumper, 1.0);


}
