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
    /*private DigitalChannel breakBeam;
    private int count = 0;
    boolean lastDetected = false;*/

    public Command run(float power){
        return instant("run intake teleop", () -> intake.setPower(power)).requires(this);
    }
    public Command run = instant("run intake auto", () -> intake.setPower(-1.0)).requires(this);
    public Command stop = instant("stop intake", () -> intake.setPower(0)).requires(this);
    public Command reverse = instant("reverse intake", () -> intake.setPower(1.0)).requires(this);

    /*public Command detectArtifacts = new LambdaCommand()
            .setUpdate(() -> {
                if (count >= 4){
                    new ParallelGroup(
                            instant(() -> stop.schedule()),
                            instant(() -> count = 0),
                            instant(() -> ActiveOpMode.gamepad2().rumble(500))
                    );
                }
            })
            .setInterruptible(true)
            .requires(this);

    @Override
    public void initialize(){
        breakBeam = ActiveOpMode.hardwareMap().get(DigitalChannel.class, "breakBeam");
        breakBeam.setMode(DigitalChannel.Mode.INPUT);
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
    }*/
}