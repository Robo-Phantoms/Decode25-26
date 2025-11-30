package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;

public class Limelight implements Subsystem {
    public static Limelight INSTANCE = new Limelight();
    private Limelight(){}
    private Limelight3A ll;
    public double llX, llY, robotYaw;
    public Pose2d llPose;
    private IMUEx imu = new IMUEx("imu", Direction.FORWARD, Direction.UP);





    @Override
    public void initialize(){
        ll = ActiveOpMode.hardwareMap().get(Limelight3A.class, "ll");
        ll.setPollRateHz(100);
        ll.pipelineSwitch(0);
        imu.zeroed();
        ll.start();
    }

    @Override
    public void periodic() {
        robotYaw = imu.get().inRad;
        ll.updateRobotOrientation(robotYaw);
        LLResult result = ll.getLatestResult();
        if (result != null && result.isValid()){
            Pose3D botPose_mt2 = result.getBotpose_MT2();
            if (botPose_mt2 != null){
                llX = botPose_mt2.getPosition().x;
                llY = botPose_mt2.getPosition().y;
                llPose = new Pose2d(llX, llY, robotYaw);
            }
        }
    }

    public Pose2d getLLPose(){
        if (llPose != null) return llPose;
        return null;
    }
}
