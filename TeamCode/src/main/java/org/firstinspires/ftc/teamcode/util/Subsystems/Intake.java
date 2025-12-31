package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.Controllable;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;

public class Intake implements Subsystem {
    public static Intake INSTANCE = new Intake();
    private Intake() {}
    private MotorEx rightIntake = new MotorEx("Intake");
    private Controllable intake = new VoltageCompensatingMotor(rightIntake);
    /*private NormalizedColorSensor colorSensor;
    private float[] hsv = new float[3];

    public enum Colors{
        GREEN,
        PURPLE,
        NONE
    } */

    public Command run(float power){
        return instant("run intake teleop", () -> intake.setPower(power));
    }
    public Command run = instant("run intake auto", () -> intake.setPower(-1.0));
    public Command stop = instant("stop intake", () -> intake.setPower(0));

    /*public Command runIntake(){
        return new LambdaCommand("run-intake")
                .setUpdate(() -> {
                    NormalizedRGBA colors = colorSensor.getNormalizedColors();
                    Color.RGBToHSV(
                            (int) (colors.red*255),
                            (int) (colors.green*255),
                            (int) (colors.blue*255),
                            hsv
                    );

                    Colors color = updateHSV();
                    if(color == Colors.GREEN || color == Colors.PURPLE) new SequentialGroup(intakeArtifactAuto(), new Delay(3.0), stop()).schedule();
                    else stop().schedule();

                })
                .requires(this);
    }
    public Colors updateHSV(){
        float hue = hsv[0];

        if (hue >=260 && hue<=300){
            return Colors.PURPLE;
        }
        else if (hue >= 90 && hue <= 150){
            return Colors.GREEN;
        }
        return Colors.NONE;
    }

    @Override
    public void initialize(){
        colorSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "sensor_color");
        colorSensor.setGain(7.0f);
    }*/
}