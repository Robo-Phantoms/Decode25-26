package org.firstinspires.ftc.teamcode.TestOpModes;


import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.teamcode.util.Subsystems.Intake;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.impl.FeedbackCRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
@TeleOp(name = "ColorSensorTeleop")
public class ColorSensorTeleop extends NextFTCOpMode {
    public ColorSensorTeleop(){
        addComponents(
                new SubsystemComponent(Intake.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
                );
    }

    @Override
    public void onStartButtonPressed(){
        //Intake.INSTANCE.runIntake().schedule();
    }
}
