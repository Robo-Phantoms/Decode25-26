package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.conditionals.IfElseCommand;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Filtration implements Subsystem {
    public static Filtration INSTANCE = new Filtration();
    private Filtration(){}
    private NormalizedColorSensor colorSensor;
    private ServoEx door = new ServoEx("door");
    public static double redValues = 0;
    public static double blueValues = 0;
    public static double greenValues = 0;
    public static double red = 0;
    public static double blue = 0;
    public static double green = 0;

    public Command openDoorGreen = new SetPosition(door, 0.5).requires(this);
    public Command openDoorPurple = new SetPosition(door, 1.0).requires(this);
    Command filter = new ParallelGroup(
            new LambdaCommand("update-colors")
                    .setUpdate(() -> updateColors())
                    .setIsDone(() -> false),
            new IfElseCommand(
                    () -> isPurple(),
                    openDoorPurple,
                    openDoorGreen
            ));

    public void updateColors() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        redValues = colors.red;
        greenValues = colors.green;
        blueValues = colors.blue;
    }
    public boolean isPurple(){
        return red > redValues && blue > blueValues && green > greenValues;
    }
    public boolean isGreen(){
        return red < redValues && blue < blueValues && green > greenValues;
    }

    @Override
    public void initialize(){
        colorSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "colorSensor");
    }
    @Override
    public void periodic(){
        updateColors();
    }

}
