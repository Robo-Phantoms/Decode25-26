package org.firstinspires.ftc.teamcode.OpModes.TurretOpModes;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.bindings.Bindings.range;

import org.firstinspires.ftc.teamcode.util.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Subsystems.Flywheels;
import org.firstinspires.ftc.teamcode.util.Subsystems.Turret;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

public class TurretTeleop extends NextFTCOpMode {
    public TurretTeleop() {
        addComponents(
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE,
                new SubsystemComponent(Drivetrain.INSTANCE, Turret.INSTANCE, Flywheels.INSTANCE)
        );
    }

    @Override
    public void onStartButtonPressed() {
        button(() -> gamepad1.left_bumper).whenTrue(Drivetrain.INSTANCE.strafeLeft);
        button(() -> gamepad1.right_bumper).whenTrue(Drivetrain.INSTANCE.strafeRight);
        button(() -> gamepad1.a).whenTrue(Drivetrain.INSTANCE.forward);
        button(() -> gamepad1.y).whenTrue(Drivetrain.INSTANCE.backward);

        button(() -> gamepad2.b).toggleOnBecomesTrue()
                .whenBecomesTrue(Flywheels.INSTANCE.startShooting)
                .whenBecomesFalse(Flywheels.INSTANCE.stopShooting);
    }
}
