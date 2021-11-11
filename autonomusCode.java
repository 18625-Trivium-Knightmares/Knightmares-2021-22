package org.firstinspires.ftc.teamcode;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

@Autonomous

public class autonomusCode extends LinearOpMode {

    /* voria and tensorFlow */
    public static final String TAG = "Vuforia Navigation Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;
    /* ... */

    public void goFoward(DcMotor FR, DcMotor FL, DcMotor BR, DcMotor BL, int tIme, int power){
        FR.setPower(power);
        FL.setPower(power);
        BR.setPower(power);
        BL.setPower(power);

        sleep(tIme);

        FR.setPower(0);
        FL.setPower(0);
        BR.setPower(0);
        BL.setPower(0);
    }
    public void turn(DcMotor FR, DcMotor FL, DcMotor BR, DcMotor BL, int tIme, String direction) {
        if (direction == "right") {
            FL.setPower(1);
            BL.setPower(1);
            FR.setPower(0);
            BR.setPower(0);
        } else if (direction == "left") {
            FR.setPower(1);
            BR.setPower(1);
            FL.setPower(0);
            BL.setPower(0);
        } else {
            System.out.println(direction + " is not an option");
        }

        sleep(tIme);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    public void wind(DcMotor RPM, DcMotor LPM, String upDown, int tIme) {
        if (upDown == "up") {
            RPM.setPower(1);
            LPM.setPower(1);
        } else if (upDown == "down") {
            RPM.setPower(-1);
            LPM.setPower(-1);
        }

        sleep(tIme);

        RPM.setPower(0);
        LPM.setPower(0);
    }

        DcMotor FR, FL, BR, BL, RPM, LPM;

    @Override

    public void runOpMode () throws InterruptedException {

        /* voforia and tensorFlow */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            /* vuforia key */
        parameters.vuforiaLicenseKey = "AYHN1aL/////AAABmYrrSlCefkltl6fJdzJbMmsrPxVWJT3oTh/1nwkBjsa2mqa3lzXGv8PSdvit2XJmvJSo4yQbLZuJ/8GGiLyOUkxC+MSR6Xpc7zCnnWH3uhT/+PyaxU2+nrn67S3mxjLSC1oGXvdcbLhkoSDDyJ53K3sF4X0YdwtP9Jlg+i1RpJczM0t4Z1J2mkhufIpYCUgf4kqM4ie3T2Q/9EYkLgh1qlrM1yzTv8553fyxGtvLUc2rHWdqzuDuc32sQ7rQ81ZZNjKSjuesFKL2W7Fx2Pk660M7cWr6obPOa0KmL2NylbtEnP3RP0hQqBZ+6ZqRrWl6bAHZd0wjlxfnk+bzaIatkK2l3u2O057pHNg9vFE5CcsV";

            /* decide camera direction */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        vuforia = ClassFactory.getInstance().createVuforia(parameters);


        FL = hardwareMap.dcMotor.get("Front Left");
        FR = hardwareMap.dcMotor.get("Front Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        RPM = hardwareMap.dcMotor.get("Right Pulley Motor");
        LPM = hardwareMap.dcMotor.get("Left Pulley Motor");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        LPM.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        sleep(1000);

        RPM.setPower(1);
        LPM.setPower(1);

        sleep(1000);

        RPM.setPower(0);
        LPM.setPower(0);

        sleep(1000);

        RPM.setPower(-1);
        LPM.setPower(-1);

        sleep(1000);

        RPM.setPower(0);
        LPM.setPower(0);

    }

}
