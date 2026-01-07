package org.firstinspires.ftc.teamcode.TestOpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

@Config
@TeleOp(name = "Launcher Test")
public class LauncherTest extends NextFTCOpMode {

    public LauncherTest(){
        addComponents(
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }
    private MotorEx launcher = new MotorEx("launcher");
    private MotorEx launcher2 = new MotorEx("launcher2").reversed();
    private MotorGroup launcherGroup = new MotorGroup(launcher, launcher2);
    MotorEx flywheelMotor;
    public static double targetPos = 50;
    public static PIDCoefficients coefficients = new PIDCoefficients(0,0,0);
    public static GravityFeedforwardParameters feedforward = new GravityFeedforwardParameters(0);
    public ControlSystem controller = ControlSystem.builder()
            .posPid(coefficients)
            .armFF(feedforward)
            .build();

    @Override
    public void onInit(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        launcher.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    @Override
    public void onUpdate(){
        KineticState currentState = new KineticState(launcherGroup.getLeader().getCurrentPosition(), launcherGroup.getLeader().getVelocity());
        controller.setGoal(new KineticState(targetPos,0,0));
        double power = controller.calculate(currentState);
        launcherGroup.getLeader().setPower(power);
        telemetry.addData("pos:", launcherGroup.getLeader().getCurrentPosition());
        telemetry.addData("target", targetPos);
        telemetry.update();


    }
}