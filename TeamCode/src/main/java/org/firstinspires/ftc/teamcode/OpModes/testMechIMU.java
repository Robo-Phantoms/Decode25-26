package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.roadrunner.RoadrunnerComponent;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "testMech + IMU")
public class testMechIMU extends NextFTCOpMode {

    public testMechIMU(){
        addComponents(
                BindingsComponent.INSTANCE,
                BulkReadComponent.INSTANCE
        );
    }
    private MotorEx leftFront = new MotorEx("leftFront").brakeMode().reversed();
    private MotorEx rightFront = new MotorEx("rightFront").brakeMode();
    private MotorEx leftBack = new MotorEx("leftBack").brakeMode().reversed();
    private MotorEx rightBack = new MotorEx("rightBack").brakeMode();
    private IMUEx imu = new IMUEx("imu", Direction. FORWARD, Direction. UP).zeroed();


    @Override
    public void onStartButtonPressed() {
        Command startDrive = new MecanumDriverControlled(
                leftFront,
                rightFront,
                leftBack,
                rightBack,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                new FieldCentric(imu)
        );

        startDrive.schedule();
    }
}
