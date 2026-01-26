package org.firstinspires.ftc.teamcode.util.pedroPathing;

import com.pedropathing.geometry.Pose;

public class Poses {
    public static class combinedPosesBlue {
        public static Pose start = new Pose(23, 126, Math.toRadians(142));
        public static Pose score = new Pose(35, 116, Math.toRadians(140));
        public static Pose line1Start = new Pose(40, 84, Math.toRadians(180));
        public static Pose line1End = line1Start.withX(17).mirror();
    }

    public static class combinedPosesRed {
        public static final Pose start = new Pose(23, 126, Math.toRadians(142)).mirror();
        public static final Pose score = new Pose(35, 116, Math.toRadians(140)).mirror();
        public static final Pose line1Start = new Pose(40, 84, Math.toRadians(180)).mirror();
        public static final Pose line1End = line1Start.withX(17).mirror();
    }

    public static class LM4BluePoses{
        public static final Pose start = new Pose(23, 126, Math.toRadians(142));
        public static final  Pose score = new Pose(35, 116, Math.toRadians(142));
        public static final Pose line2Start = new Pose(48, 58, Math.toRadians(180));
        public static final Pose line2End = new Pose(20, 58, Math.toRadians(180));
        public static final Pose openGate = new Pose(25, 64, Math.toRadians(180));
        public static final Pose openGateIntake = new Pose(9, 56.5, Math.toRadians(125));
        public static final Pose line1Start = new Pose(48, 82, Math.toRadians(180));
        public static final Pose line1End = new Pose(23, 82, Math.toRadians(180));
        public static final Pose line3Start = new Pose(48, 35, Math.toRadians(180));
        public static final Pose line3End = new Pose(20, 35, Math.toRadians(180));
        public static final Pose leavePose = new Pose(32, 72, Math.toRadians(270));

    }

    public static class LM4RedPoses{
        public static final Pose start = new Pose(23, 126, Math.toRadians(142)).mirror();
        public static final  Pose score = new Pose(35, 116, Math.toRadians(142)).mirror();
        public static final Pose line2Start = new Pose(36, 55, Math.toRadians(180)).mirror();
        public static final Pose line2End = new Pose(20, 55, Math.toRadians(180)).mirror();
        public static final Pose openGate = new Pose(27, 64, Math.toRadians(180)).mirror();
        public static final Pose openGateIntake = new Pose(8, 58, Math.toRadians(130)).mirror();
        public static final Pose line1Start = new Pose(36, 79, Math.toRadians(180)).mirror();
        public static final Pose line1End = new Pose(20, 79, Math.toRadians(180)).mirror();
        public static final Pose line3Start = new Pose(36, 33, Math.toRadians(180)).mirror();
        public static final Pose line3End = new Pose(20, 33, Math.toRadians(180)).mirror();
        public static final Pose leavePose = new Pose(32, 72, Math.toRadians(270)).mirror();
    }
}
