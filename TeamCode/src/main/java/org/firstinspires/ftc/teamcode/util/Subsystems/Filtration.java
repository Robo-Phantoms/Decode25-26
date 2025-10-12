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
    private int ballCount = 0;
    private boolean ballDetected = false;
    public Command greenArtifact = new ParallelGroup(
            new SetPosition(doorGreen, 1.0),
            new SetPosition(doorPurple, 0.0)
    ).requires(this);
    public Command purpleArtifact = new ParallelGroup(
            new SetPosition(doorGreen, 0.0),
            new SetPosition(doorPurple, 1.0)
    ).requires(this);
    public Command filterArtifacts = new LambdaCommand("update-colors")
                    .setUpdate(() -> {
                        NormalizedRGBA colors = colorSensor.getNormalizedColors();

                        double red = colors.red;
                        double green = colors.green;
                        double blue = colors.blue;

                        double total = red + green + blue;
                        double redRatio = red / total;
                        double greenRatio = green / total;
                        double blueRatio = blue / total;

                        if (isPurple(redRatio, greenRatio, blueRatio)){
                            purpleArtifact.schedule();
                        } else if (isGreen(redRatio, greenRatio, blueRatio, total)){
                            greenArtifact.schedule();
                        }
                    })
                    .setIsDone(() -> false);

    @Override
    public void initialize(){
        colorSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "colorSensor");
        colorSensor.setGain(7.0f);
    }
    @Override
    public void periodic(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        double red = colors.red;
        double green = colors.green;
        double blue = colors.blue;

        double total = red + green + blue;
        double redRatio = red / total;
        double greenRatio = green / total;
        double blueRatio = blue / total;

        boolean currentDetection = isPurple(redRatio, greenRatio, blueRatio) || isGreen(redRatio, greenRatio, blueRatio, total);
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
    public boolean isPurple(double r, double g, double b){
        return r > 0.22 && b > 0.42 && g < 0.3;
    }
    public boolean isGreen(double r, double g, double b, double total){
        return g > 0.35 && g > r + 0.10 && g > b + 0.05 && total > 0.05;
    }

    public int getBallCount(){
        return ballCount;
    }
}
