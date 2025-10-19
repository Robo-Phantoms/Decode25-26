package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.conditionals.IfElseCommand;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class ArtifactFilter extends SubsystemGroup {
    public static ArtifactFilter INSTANCE = new ArtifactFilter();
    private ArtifactFilter(){
        super(Flywheels.INSTANCE, Bumper.INSTANCE, Door.INSTANCE);
    }
    private NormalizedColorSensor colorSensor;


    public Command shootGreen = new SequentialGroup(
            new Delay(0.5),
            Flywheels.INSTANCE.shootArtifact
    ).named("shoot-green");
    public Command shootPurple = new SequentialGroup(
            new Delay(0.5),
            Bumper.INSTANCE.moveBumper,
            Flywheels.INSTANCE.shootArtifact
    ).named("shoot-purple");

    @Override
    public void initialize(){
        colorSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "colorSensor");
        colorSensor.setGain(7.0f);
    }
    public boolean isPurple() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        double red = colors.red;
        double green = colors.green;
        double blue = colors.blue;
        double total = red + green + blue;
        double redRatio = red / total;
        double greenRatio = green / total;
        double blueRatio = blue / total;
        return redRatio > 0.22 && blueRatio > 0.42 && greenRatio < 0.3;
    }
    public boolean isGreen(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        double red = colors.red;
        double green = colors.green;
        double blue = colors.blue;
        double total = red + green + blue;
        double redRatio = red / total;
        double greenRatio = green / total;
        double blueRatio = blue/total;
        return greenRatio > 0.35 && greenRatio > redRatio + 0.10 && greenRatio > blueRatio + 0.05 && total > 0.05;
    }

}
