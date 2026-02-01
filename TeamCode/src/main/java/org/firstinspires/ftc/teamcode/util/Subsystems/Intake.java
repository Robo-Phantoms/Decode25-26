package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.Controllable;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.VoltageCompensatingMotor;
import static dev.nextftc.ftc.ActiveOpMode.telemetry;
import static dev.nextftc.ftc.ActiveOpMode.hardwareMap;

public class Intake implements Subsystem {
    public static Intake INSTANCE = new Intake();
    private Intake() {}
    private MotorEx rightIntake = new MotorEx("Intake").reversed();
    private Controllable intake = new VoltageCompensatingMotor(rightIntake);
    private DigitalChannel breakBeam;
    private int count = 0;
    boolean lastDetected = false;

    public Command run(float power){
        return instant(() -> intake.setPower(power)).requires(this);
    }
    public Command run = instant(() -> intake.setPower(1.0)).requires(this);
    public Command stop = instant(() -> intake.setPower(0)).requires(this);
    public Command reverse = instant(() -> intake.setPower(-1.0)).requires(this);
    public void resetCount(){
        count = 0;
        reverse.cancel();
    }
    public Command setCount(int newCount){
        return instant(() -> count = newCount);
    }


    @Override
    public void initialize(){
        breakBeam = hardwareMap().get(DigitalChannel.class, "breakBeam");
        breakBeam.setMode(DigitalChannel.Mode.INPUT);

        resetCount();
    }

    @Override
    public void periodic(){
        boolean detected = breakBeam.getState();
        if (detected && !lastDetected) {
            count++;
        }

        lastDetected = detected;


        telemetry().addLine(detected ? "object detected" : "no object detected");
        telemetry().addData("count", count);
        telemetry().update();
    }

    public double getCount(){
        return count;
    }
}