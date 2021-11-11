package org.firstinspires.ftc.teamcode;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    }

        DcMotor FR;
        DcMotor FL;
        DcMotor BR;
        DcMotor BL;

    @Override

    public void runOpMode () throws InterruptedException {
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
