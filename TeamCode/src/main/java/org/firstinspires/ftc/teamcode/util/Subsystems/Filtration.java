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
    private ServoEx doorGreen = new ServoEx("doorGreen");
    private ServoEx doorPurple = new ServoEx("doorPurple");
    public static double redValues = 0;
    public static double blueValues = 0;
    public static double greenValues = 0;
    public static double red = 0;
    public static double blue = 0;
    public static double green = 0;
    private int ballCount = 0;
    private boolean ballDetected = false;

    public Command openDoorGreen = new SetPosition(doorGreen, 0.5).requires(doorGreen);
    public Command openDoorPurple = new SetPosition(doorPurple, 1.0).requires(doorPurple);
    public Command filter = new ParallelGroup(
            new LambdaCommand("update-colors")
                    .setUpdate(this::updateColors)
                    .setIsDone(() -> false),
            new IfElseCommand(
                    this::isPurple,
                    openDoorPurple,
                    openDoorGreen
            )).requires(this);


    @Override
    public void initialize(){
        colorSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "colorSensor");
    }
    @Override
    public void periodic(){
        updateColors();

        boolean currentDetection = isPurple() || isGreen();
        if(currentDetection && !ballDetected){
            ballCount++;
            ballDetected = true;
        }

        if (ballCount >= 3){
            ballCount = 0;
        }
        else if (!currentDetection) {
            ballDetected = false;
        }

        ActiveOpMode.telemetry().addData("Ball Count", getBallCount());
        ActiveOpMode.telemetry().update();

    }
    public void updateColors() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        redValues = colors.red;
        greenValues = colors.green;
        blueValues = colors.blue;
    }
    public boolean isPurple() {
        return redValues > red && blueValues > blue && greenValues < green;
    }

    public boolean isGreen() {
        return greenValues > green && greenValues > redValues && greenValues > blueValues;
    }

    public int getBallCount(){
        return ballCount;
    }
}
