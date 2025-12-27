package org.firstinspires.ftc.teamcode.OpModes.CatapultOpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "testMech")
public class testmech extends NextFTCOpMode {

    public testmech(){
        addComponents(
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }
    private MotorEx leftFront = new MotorEx("leftFront").brakeMode().reversed();
    private MotorEx rightFront = new MotorEx("rightFront").brakeMode();
    private MotorEx leftBack = new MotorEx("leftBack").brakeMode().reversed();
    private MotorEx rightBack = new MotorEx("rightBack").brakeMode();

    @Override
    public void onStartButtonPressed() {
        Command startDrive = new MecanumDriverControlled(
                leftFront,
                rightFront,
                leftBack,
                rightBack,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );

        startDrive.schedule();
    }
}
