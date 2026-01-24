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
        public static Pose start = new Pose(23, 126, Math.toRadians(142)).mirror();
        public static Pose score = new Pose(35, 116, Math.toRadians(140)).mirror();
        public static Pose line1Start = new Pose(40, 84, Math.toRadians(180)).mirror();
        public static Pose line1End = line1Start.withX(17).mirror();
    }
}
