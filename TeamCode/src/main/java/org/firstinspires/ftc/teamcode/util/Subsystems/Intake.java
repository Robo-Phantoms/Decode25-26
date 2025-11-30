package org.firstinspires.ftc.teamcode.util.Subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Intake implements Subsystem {
    public static Intake INSTANCE = new Intake();
    private Intake() {}
    private MotorEx rightIntake = new MotorEx("Intake");
    /*private NormalizedColorSensor colorSensor;
    private float[] hsv = new float[3];

    public enum Colors{
        GREEN,
        PURPLE,
        NONE
    } */

    public Command intakeArtifactTele(float power){
        return new SetPower(rightIntake, power);
    }
    public Command intakeArtifactAuto(){
        return new SetPower(rightIntake, -1.0).requires(this);
    }
    public Command stopIntake(){
        return new SetPower(rightIntake, 0.0).requires(this);
    }

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
                    if(color == Colors.GREEN || color == Colors.PURPLE) new SequentialGroup(intakeArtifactAuto(), new Delay(3.0), stopIntake()).schedule();
                    else stopIntake().schedule();

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