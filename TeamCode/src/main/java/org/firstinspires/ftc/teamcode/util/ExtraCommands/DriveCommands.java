package org.firstinspires.ftc.teamcode.util.ExtraCommands;

import dev.nextftc.hardware.controllable.Controllable;

/**
 * @Param Use this class for extra commands that can't be put in subsystems
 */

public class DriveCommands {
    public static double POWER = 0.55;

    public static void strafeLeft(Controllable lfMotor, Controllable rfMotor, Controllable lbMotor, Controllable rbMotor) {
        rfMotor.setPower(POWER);
        lfMotor.setPower(POWER);
        lbMotor.setPower(-POWER);
        rbMotor.setPower(-POWER);
    }

    public static void strafeRight(Controllable lfMotor, Controllable rfMotor, Controllable lbMotor, Controllable rbMotor) {
        rfMotor.setPower(-POWER);
        lfMotor.setPower(-POWER);
        lbMotor.setPower(POWER);
        rbMotor.setPower(POWER);
    }

    public static void stop(Controllable lf, Controllable rf, Controllable lb, Controllable rb) {
        rf.setPower(0);
        lf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
    }

    public static void forward(Controllable lf, Controllable rf, Controllable lb, Controllable rb) {
        rf.setPower(-POWER);
        lf.setPower(-POWER);
        lb.setPower(-POWER);
        rb.setPower(-POWER);
    }

    public static void backward(Controllable lf, Controllable rf, Controllable lb, Controllable rb) {
        rf.setPower(POWER);
        lf.setPower(POWER);
        lb.setPower(POWER);
        rb.setPower(POWER);
    }
}