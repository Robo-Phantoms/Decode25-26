package org.firstinspires.ftc.teamcode.OpModes;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.bindings.Bindings.range;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.ExtraCommands.*;
import org.firstinspires.ftc.teamcode.util.Subsystems.*;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.driving.DifferentialTankDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;


@TeleOp(name="Teleop")
public class Teleop extends NextFTCOpMode {

    public Teleop(){
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Flywheels.INSTANCE, Intake.INSTANCE, Bumper.INSTANCE)
        );
    }

    private MotorEx leftFront = new MotorEx("leftFront");
    private MotorEx rightFront = new MotorEx("rightFront").reversed();
    private MotorEx leftBack = new MotorEx("leftBack");
    private MotorEx rightBack = new MotorEx("rightBack").reversed();
    private MotorGroup leftMotors = new MotorGroup(leftFront, leftBack);
    private MotorGroup rightMotors = new MotorGroup(rightFront, rightBack);

    @Override
    public void onStartButtonPressed() {
        gamepad1();
        gamepad2();
    }

    public void gamepad1(){
        Command driverControlled = new DifferentialTankDriverControlled(leftMotors, rightMotors, Gamepads.gamepad1().leftStickY(), Gamepads.gamepad1().rightStickY());
        driverControlled.schedule();

        button(() -> gamepad1.left_bumper)
                .whenTrue(() -> DriveCommands.strafeLeft(leftFront, rightFront, leftBack, rightBack))
                .whenBecomesFalse(() -> DriveCommands.stop(leftFront, rightFront, leftBack, rightBack));

        button(() -> gamepad1.right_bumper)
                .whenTrue(() -> DriveCommands.strafeRight(leftFront, rightFront, leftBack, rightBack))
                .whenBecomesFalse(() -> DriveCommands.stop(leftFront, rightFront, leftBack, rightBack));

        button(() -> gamepad1.y)
                .whenTrue(() -> DriveCommands.forward(leftFront, rightFront, leftBack, rightBack))
                .whenBecomesFalse(() -> DriveCommands.stop(leftFront, rightFront, leftBack, rightBack));

        button(()  -> gamepad1.a)
                .whenTrue(() -> DriveCommands.backward(leftFront, rightFront, leftBack, rightBack))
                .whenBecomesFalse(() -> DriveCommands.stop(leftFront, rightFront, leftBack, rightBack));
    }

    public void gamepad2(){
        range(() -> gamepad2.right_stick_y).inRange(-0.1, 0.1)
                .whenFalse(() -> Intake.INSTANCE.intakeArtifact(gamepad2.right_stick_y).schedule()  )
                .whenTrue(() -> Intake.INSTANCE.stopIntake().schedule());

        button(() -> gamepad2.b)
                .toggleOnBecomesTrue()
                .whenBecomesTrue(Flywheels.INSTANCE.shootArtifact)
                .whenBecomesFalse(Flywheels.INSTANCE.stopShooting);

        button(() -> gamepad2.x).whenBecomesTrue(Bumper.INSTANCE.moveBumper);



    }
}