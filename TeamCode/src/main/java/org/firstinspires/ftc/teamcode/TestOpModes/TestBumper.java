package org.firstinspires.ftc.teamcode.TestOpModes;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.impl.ServoEx;

@Config
@TeleOp
public class TestBumper extends NextFTCOpMode {

    private ServoEx bumper = new ServoEx("bumper");
    public static double mB =0;
    public static double rB= 1.0;
    @Override
    public void onStartButtonPressed() {
        Gamepads.gamepad1().a()
                .toggleOnBecomesTrue()
                .whenBecomesTrue(() -> bumper.to(mB))
                .whenBecomesFalse(() ->  bumper.to(rB));
    }
}
