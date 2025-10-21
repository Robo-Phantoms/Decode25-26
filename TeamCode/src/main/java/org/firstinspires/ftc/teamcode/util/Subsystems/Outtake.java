package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class Outtake extends SubsystemGroup {
    public static final Outtake INSTANCE = new Outtake();
    private Outtake() {
        super(Flywheels.INSTANCE, Bumper.INSTANCE);
    }

    public Command shootArtifact = new SequentialGroup(
            Flywheels.INSTANCE.startShooting,
            Bumper.INSTANCE.moveBumperShoot,
            new Delay(1.0),
            Flywheels.INSTANCE.stopShooting
    );
}
