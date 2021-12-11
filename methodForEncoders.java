package org.firstinspires.ftc.teamcode;

public class methodForEncoders {
    public int calculateToPlace(double distance) {
        double circumference = 3.14 * 4.72441;
        double rotationsNeeded = distance * circumference;
        int encoderTarget = (int) (rotationsNeeded * 1338);
        return encoderTarget;
    }
}
