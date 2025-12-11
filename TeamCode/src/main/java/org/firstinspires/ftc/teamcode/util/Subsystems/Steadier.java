package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Steadier implements Subsystem {
    public static final Steadier INSTANCE = new Steadier();
    private Steadier() {}
    private ServoEx steadier = new ServoEx("steadier");

    public Command steadyArtifacts = new SequentialGroup(new SetPosition(steadier, 0.5), new SetPosition(steadier, 0))                                                                ;

}
