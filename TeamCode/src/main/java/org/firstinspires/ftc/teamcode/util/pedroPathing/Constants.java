package org.firstinspires.ftc.teamcode.util.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.ThreeWheelIMUConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {

    static double forwardTicksToInches = 0.0019955405651916535, strafeTicksToInches = 0.0020081748738415782, turnTicksToInches = 0.0019108418700895717;
    static double forwardVelocity = 79.93967501533771, lateralVelocity = 62.16949737716364;
    static double forwardZPA = -32.7267447859355, lateralZPA = -74.48685911949929;

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(10.8862)
            .forwardZeroPowerAcceleration(forwardZPA)
            .lateralZeroPowerAcceleration(lateralZPA)
            // PID's
            .translationalPIDFCoefficients(new PIDFCoefficients(0.15,0,0.03,0.02))
            .headingPIDFCoefficients(new PIDFCoefficients(0.9,0,0.07,0.02)) //TODO: IMPROVE! + SECONDARY
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.01, 0, 0.08, 0.02))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.04, 0, 0.00001, 0.6, 0.01)) //TODO: IMPROVE + SECONDARY
            .useSecondaryHeadingPIDF(true)
            .centripetalScaling(0.0008);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .xVelocity(forwardVelocity)
            .yVelocity(lateralVelocity)
            // Motor names and directions
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftBack")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);

    public static ThreeWheelIMUConstants localizerConstants = new ThreeWheelIMUConstants()
            .forwardTicksToInches(forwardTicksToInches)
            .strafeTicksToInches(strafeTicksToInches)
            .turnTicksToInches(turnTicksToInches)
            .leftEncoder_HardwareMapName("leftBack")
            .rightEncoder_HardwareMapName("leftFront")
            .strafeEncoder_HardwareMapName("rightFront")
            .leftEncoderDirection(Encoder.FORWARD)
            .rightEncoderDirection(Encoder.FORWARD)
            .strafeEncoderDirection(Encoder.FORWARD)
            .IMU_HardwareMapName("imu")
            .leftPodY(2.935) //TODO: fix measurements
            .rightPodY(-2.74) //TODO: fix measurements
            .strafePodX(-5.885) //TODO: fix measurements
            .IMU_Orientation(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.FORWARD, RevHubOrientationOnRobot.UsbFacingDirection.UP));


    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 4, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .threeWheelIMULocalizer(localizerConstants)
                .build();
    }
}
