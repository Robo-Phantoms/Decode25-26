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

    private DigitalChannel breakBeam;
    private int count = 0;
    boolean lastDetected = false;

    @Override
    public void onInit(){
        breakBeam = hardwareMap.get(DigitalChannel.class, "breakBeam");
        breakBeam.setMode(DigitalChannel.Mode.INPUT);
    }

    @Override
    public void onStartButtonPressed(){
        Intake.INSTANCE.run.schedule();

        button(() -> count>=4)
                .whenBecomesTrue(Intake.INSTANCE.stop);
    }
    @Override
    public void onUpdate(){
        boolean detected = breakBeam.getState();
        if (detected && !lastDetected) {
            count++;
        }

        lastDetected = detected;

        if (count >= 4) count = 0;

        telemetry.addLine(detected ? "object detected" : "no object detected");
        telemetry.addData("count", count);
        telemetry.update();
    }
}