package org.firstinspires.ftc.teamcode.OpModes;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.bindings.Bindings.range;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;

import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Config
@TeleOp(name="Teleop")
public class Teleop extends NextFTCOpMode {

    public Teleop() {
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Intake.INSTANCE, Drivetrain.INSTANCE, Catapults.INSTANCE)
        );
    }

    @Override
    public void onStartButtonPressed() {
        button(() -> Intake.INSTANCE.getCount() > 3)
                .whenTrue(new SequentialGroup(Intake.INSTANCE.reverse, new Delay(0.5), new InstantCommand(() -> Intake.INSTANCE.resetCount())).setInterruptible(false));

        button(() -> gamepad1.left_bumper).whenTrue(Drivetrain.INSTANCE.strafeLeft);
        button(()-> gamepad1.right_bumper).whenTrue(Drivetrain.INSTANCE.strafeRight);
        button(() -> gamepad1.y).whenTrue(Drivetrain.INSTANCE.forward);
        button(() -> gamepad1.a).whenTrue(Drivetrain.INSTANCE.backward);

        range(() -> gamepad2.right_stick_y).inRange(-0.1, 0.1)
                .whenFalse(() -> Intake.INSTANCE.run(-gamepad2.right_stick_y).schedule())
                .whenTrue(() -> Intake.INSTANCE.stop.schedule());

        button(() -> gamepad2.right_bumper).toggleOnBecomesTrue()
                .whenBecomesTrue(Catapults.INSTANCE.down)
                .whenBecomesFalse(Catapults.INSTANCE.voltageCompUp);

        button(() -> gamepad2.left_bumper).whenBecomesTrue(Catapults.INSTANCE.stabilize);
        button(() -> gamepad2.a).whenBecomesTrue(new InstantCommand(() -> Intake.INSTANCE.resetCount()));

    }
}