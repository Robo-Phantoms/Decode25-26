package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.bindings.Bindings.range;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.util.Subsystems.*;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Config
@TeleOp(name="CatapultTeleop")
public class CatapultTeleop extends NextFTCOpMode {

    public CatapultTeleop() {
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Intake.INSTANCE, Drivetrain.INSTANCE, Catapults.INSTANCE)
        );
    }

    @Override
    public void onStartButtonPressed() {
        //--- Gamepad1 Commands ---
        button(() -> gamepad1.left_bumper).whenTrue(Drivetrain.INSTANCE.strafeLeft);
        button(()-> gamepad1.right_bumper).whenTrue(Drivetrain.INSTANCE.strafeRight);
        button(() -> gamepad1.a).whenTrue(Drivetrain.INSTANCE.forward);
        button(() -> gamepad1.y).whenTrue(Drivetrain.INSTANCE.backward);


        // --- Gamepad2 Commands ---
        range(() -> gamepad2.right_stick_y).inRange(-0.1, 0.1)
                .whenFalse(() -> Intake.INSTANCE.run(gamepad2.right_stick_y).schedule())
                .whenTrue(() -> Intake.INSTANCE.stop.schedule());

        button(() -> gamepad2.right_bumper).toggleOnBecomesTrue()
                .whenBecomesTrue(Catapults.INSTANCE.down)
                .whenBecomesFalse(Catapults.INSTANCE.voltageCompUp);
    }
}