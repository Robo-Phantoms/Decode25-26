package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Door implements Subsystem {
    public static Door INSTANCE = new Door();
    private Door(){}
    private ServoEx doorGreen = new ServoEx("doorGreen");
    private ServoEx doorPurple = new ServoEx("doorPurple");

    public Command openGreenDoor = new ParallelGroup(
            new SetPosition(doorGreen, 1.0),
            new SetPosition(doorPurple, 0.0)
    ).requires(this);
    public Command openPurpleDoor = new ParallelGroup(
            new SetPosition(doorGreen, 0.0),
            new SetPosition(doorPurple, 1.0)
    ).requires(this);
}
