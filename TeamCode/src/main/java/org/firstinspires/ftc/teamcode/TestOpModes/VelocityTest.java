package org.firstinspires.ftc.teamcode.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforward;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
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

    private MotorGroup flywheels = new MotorGroup(flywheelRight, flywheelLeft);
    public static double kp=0.001, ki=0, kd=0;
    public static double kv=0.00035, ka=0, ks=0;
    public static double targetVel=1500;
    public static PIDCoefficients coefficients = new PIDCoefficients(kp, ki, kd);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(kv, ka, ks);

    public static ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .basicFF(ff)
            .build();

    @Override
    public void onUpdate(){
        KineticState currentState = new KineticState(flywheels.getCurrentPosition(), flywheels.getVelocity());
        double power = controller.calculate(currentState);
        flywheels.setPower(power);
        controller.setGoal(new KineticState(0,targetVel,0));


        ActiveOpMode.telemetry().addData("currentPos", flywheels.getCurrentPosition());
        ActiveOpMode.telemetry().addData("currentVel", flywheels.getVelocity());
        ActiveOpMode.telemetry().addData("power", power);
        ActiveOpMode.telemetry().update();

    }
}
