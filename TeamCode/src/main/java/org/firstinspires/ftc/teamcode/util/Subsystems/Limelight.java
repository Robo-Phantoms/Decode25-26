package org.firstinspires.ftc.teamcode.util.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class Limelight implements Subsystem {
    public static Limelight INSTANCE = new Limelight();
    private Limelight(){}
    private Limelight3A ll;
    private enum IDState{
        FINDING,
        FOUND
    }

    IDState idState = IDState.FINDING;
    @Override
    public void initialize(){
        ll = ActiveOpMode.hardwareMap().get(Limelight3A.class, "ll");
        ll.setPollRateHz(100);
        ll.pipelineSwitch(0);
        ll.start();
    }

    @Override
    public void periodic(){
        if (idState == IDState.FINDING){
            LLResult result = ll.getLatestResult();
            if (result != null && result.isValid()){
                List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fiducial : fiducials){
                    int id = fiducial.getFiducialId();
                    ActiveOpMode.telemetry().addData("ID:", id);
                    idState = IDState.FOUND;
                    ActiveOpMode.telemetry().update();
                }
            }
        }
    }
}
