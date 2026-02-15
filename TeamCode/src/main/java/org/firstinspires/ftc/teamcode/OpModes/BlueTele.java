package org.firstinspires.ftc.teamcode.OpModes;

import static dev.nextftc.bindings.Bindings.button;
import static dev.nextftc.bindings.Bindings.range;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.pedro.SoloRedAuto;
import org.firstinspires.ftc.teamcode.util.Subsystems.Catapults;
import org.firstinspires.ftc.teamcode.util.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.util.pedroPathing.Constants;

import java.util.function.Supplier;

import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Config
@TeleOp(name="BlueTeleop")
public class BlueTele extends NextFTCOpMode {

    public BlueTele() {
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower),
                new SubsystemComponent(Intake.INSTANCE, Drivetrain.INSTANCE, Catapults.INSTANCE)
        );
    }

    @Override
    public void onStartButtonPressed() {
        //button(() -> Intake.INSTANCE.getCount() > 3).whenTrue(Intake.INSTANCE.overload);

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