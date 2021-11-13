package org.firstinspires.ftc.teamcode;

public class methodForEncoders {
    public int calculateToPlace(double distance, double ticksPerRev, double diameter) {
        double circumference = 3.14 * diameter;
        double rotationsNeeded = distance * circumference;
        int encoderTarget = (int) (rotationsNeeded * ticksPerRev);
        return encoderTarget;
    }
}

