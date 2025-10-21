package org.firstinspires.ftc.teamcode.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

import dalvik.system.DelegateLastClassLoader;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

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
    private MotorEx launcher2 = new MotorEx("launcher2");
    private MotorGroup launcherGroup = new MotorGroup(launcher, launcher2);


    public Command move = new SetPower(launcherGroup, 1.0);
    public Command stop = new SetPower(launcherGroup, 0.0);

    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .toggleOnBecomesTrue()
                .whenBecomesTrue(
                        new SequentialGroup(
                                move,
                                new Delay(0.075),
                                stop
                        )
                );
    }
}
