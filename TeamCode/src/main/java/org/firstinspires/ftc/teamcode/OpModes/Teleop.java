package org.firstinspires.ftc.teamcode.OpModes;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.bindings.Bindings.range;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.util.Subsystems.*;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name="Teleop")
public class Teleop extends NextFTCOpMode {

    public Teleop() {
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Intake.INSTANCE, Drivetrain.INSTANCE, Catapult.INSTANCE)
        );
    }

    @Override
    public void onStartButtonPressed() {
        //--- Gamepad1 Commands ---
        Drivetrain.INSTANCE.startDrive.schedule();
        button(() -> gamepad1.left_bumper).whenTrue(Drivetrain.INSTANCE.strafeLeft);
        button(()-> gamepad1.right_bumper).whenTrue(Drivetrain.INSTANCE.strafeRight);
        button(() -> gamepad1.a).whenTrue(Drivetrain.INSTANCE.forward);
        button(() -> gamepad1.y).whenTrue(Drivetrain.INSTANCE.backward);

        // --- Gamepad2 Commands ---
        range(() -> gamepad2.right_stick_y).inRange(-0.1, 0.1)
                .whenFalse(() -> Intake.INSTANCE.intakeArtifact(gamepad2.right_stick_y).schedule()  )
                .whenTrue(() -> Intake.INSTANCE.stopIntake().schedule());

        button(() -> gamepad2.b)
                .toggleOnBecomesTrue()
                .whenBecomesTrue(Catapult.INSTANCE.catapultsUp)
                .whenBecomesFalse(Catapult.INSTANCE.catapultsDown);
    }
}