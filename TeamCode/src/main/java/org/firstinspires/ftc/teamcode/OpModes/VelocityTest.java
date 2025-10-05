package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;

@Config
@TeleOp(name = "VelocityTest")
public class VelocityTest extends NextFTCOpMode {

    public VelocityTest(){
        addComponents(
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }
    private MotorEx flywheelLeft = new MotorEx("flywheelLeft").reversed();
    private MotorEx flywheelRight = new MotorEx("flywheelRight");

    private MotorGroup flywheels = new MotorGroup(flywheelLeft, flywheelRight);
    public static double kp, ki, kd;
    public static double kv, ka, ks;
    public static double targetVel;
    public static ControlSystem controller = ControlSystem.builder()
            .velPid(kp, ki, kd)
            .basicFF(kv, ks, ka)
            .build();

    @Override
    public void onStartButtonPressed(){
        Gamepads.gamepad1().a().whenTrue(() -> controller.setGoal(new KineticState(0.0, targetVel, 0.0)));
    }

    @Override
    public void onUpdate(){
        KineticState currentState = new KineticState(flywheels.getCurrentPosition(), flywheels.getVelocity());
        double power = controller.calculate(currentState);
        flywheels.setPower(power);

        ActiveOpMode.telemetry().addData("currentPos", flywheels.getCurrentPosition());
        ActiveOpMode.telemetry().addData("currentVel", flywheels.getVelocity());
        ActiveOpMode.telemetry().addData("power", power);
        ActiveOpMode.telemetry().update();

    }
}
