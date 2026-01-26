package org.firstinspires.ftc.teamcode.TestOpModes;

import static dev.nextftc.bindings.Bindings.button;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "break beam test")
public class BreakBeamTest extends NextFTCOpMode {
    public BreakBeamTest(){
        addComponents(
                new SubsystemComponent(Intake.INSTANCE),
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }

    @Override
    public void onStartButtonPressed(){
        Intake.INSTANCE.run.schedule();

        button(() -> Intake.INSTANCE.getCount() > 3)
                .whenBecomesTrue(Intake.INSTANCE.reverse);
    }

    @Override
    public void onStop(){
        Intake.INSTANCE.resetCount.schedule();
    }
}