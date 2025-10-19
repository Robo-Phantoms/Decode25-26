package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.conditionals.IfElseCommand;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class Outtake extends SubsystemGroup {
    public static final Outtake INSTANCE = new Outtake();
    private Outtake(){
        super(Flywheels.INSTANCE, Intake.INSTANCE, Filtration.INSTANCE);
    }
    public Command shootGreen = new SequentialGroup(
            Filtration.INSTANCE.openGreenDoor,
            new Delay(0.5),
            Flywheels.INSTANCE.shootArtifact
    );
    public Command shootPurple = new SequentialGroup(
            Filtration.INSTANCE.openPurpleDoor,
            new Delay(0.5),
            Flywheels.INSTANCE.shootArtifact
    );

    @Override
    public void periodic(){
        Filtration.updateColors();
    }

}
