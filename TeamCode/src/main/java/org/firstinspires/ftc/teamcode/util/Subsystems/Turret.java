package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ServoController;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.AngularFeedback;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.FeedbackCRServoEx;

public class Turret implements Subsystem {
    public static final Turret INSTANCE = new Turret();
    private Turret() {
    }

    private AnalogInput analogInput = ActiveOpMode.hardwareMap().get(AnalogInput.class, "analogInput");
    private FeedbackCRServoEx turretServo = new FeedbackCRServoEx(0.1, () -> analogInput, () -> ActiveOpMode.hardwareMap().get(CRServo.class, "crServo"));
    public static PIDCoefficients coefficients = new PIDCoefficients(0,0,0);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(0,0,0);
    private ControlSystem controlSystem = ControlSystem.builder()
            .angular(AngleType.DEGREES,
                    feedback -> feedback.posPid(coefficients))
            .basicFF(ff)
            .build();

    @Override
    public void periodic(){
        turretServo.setPower(controlSystem.calculate(turretServo.getState()));
    }
}
