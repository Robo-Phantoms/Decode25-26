package org.firstinspires.ftc.teamcode.TestOpModes;

import static dev.nextftc.bindings.Bindings.button;

import org.firstinspires.ftc.teamcode.util.ExtraCommands.DriveCommands;
import org.firstinspires.ftc.teamcode.util.Subsystems.Filtration;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.Subsystems.Outtake;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.driving.DifferentialTankDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

public class FilterTeleop extends NextFTCOpMode {
    public FilterTeleop(){
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Outtake.INSTANCE, Intake.INSTANCE, Filtration.INSTANCE)
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
        Gamepads.gamepad2().rightStickY().inRange(-0.1, 0.1)
                .whenFalse(() -> Intake.INSTANCE.intakeArtifact(gamepad2.right_stick_y).schedule()  )
                .whenTrue(() -> Intake.INSTANCE.stopIntake().schedule());

        button(() -> gamepad2.b)
                .toggleOnBecomesTrue()
                .whenTrue(Outtake.INSTANCE.shootArtifact)
                .whenFalse(Outtake.INSTANCE.stopShooting);

        button(() -> gamepad2.x)
                .toggleOnBecomesTrue()
                .whenTrue(Outtake.INSTANCE.moveBumper)
                .whenFalse(Outtake.INSTANCE.reverseBumper);


    }
}
