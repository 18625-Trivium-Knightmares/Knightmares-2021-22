package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.methodForEncoders;

import java.lang.annotation.Target;


@Autonomous
public class autonomousCodeForBlueLeft extends LinearOpMode {

    // move forward or backward
    public void goForward(int tIme) {
        FR.setPower(0.75);
        FL.setPower(0.75);
        BR.setPower(0.75);
        BL.setPower(0.75);

        if (tIme != 0) {
            sleep(tIme);

            FR.setPower(0);
            FL.setPower(0);
            BR.setPower(0);
            BL.setPower(0);
        }
    }

    public void goBackward(int tIme) {
        FR.setPower(-1);
        FL.setPower(-1);
        BR.setPower(-1);
        BL.setPower(-1);

        if (tIme != 0) {
            sleep(tIme);

            FR.setPower(0);
            FL.setPower(0);
            BR.setPower(0);
            BL.setPower(0);
        }
    }

    // turn
    public void turn(int tIme, String direction) {
        if (direction == "right") {
            FL.setPower(0.5);
            BL.setPower(0.5);
            FR.setPower(-0.5);
            BR.setPower(-0.5);
        } else if (direction == "left") {
            FR.setPower(0.5);
            BR.setPower(0.5);
            FL.setPower(-0.5);
            BL.setPower(-0.5);
        }

        if (tIme != 0) {
            sleep(tIme);
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }

    // some encoders
    public void encoders(int targetToPlace) {
        FL.setTargetPosition(targetToPlace);
        FR.setTargetPosition(targetToPlace);
        BL.setTargetPosition(targetToPlace);
        BR.setTargetPosition(targetToPlace);

        FL.setPower(-0.5);
        FR.setPower(-0.5);
        BL.setPower(-0.5);
        BR.setPower(-0.5);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FR.isBusy() || FL.isBusy() || BR.isBusy() || BL.isBusy()) {
        }

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    // duck carousel spinner
    public void spinDuck(int tIme) {
        DCM.setPower(-0.5);

        sleep(tIme);

        DCM.setPower(0);
    }

    // arm
    public void arm(String direction) {
        if (direction == "foward") {
            AM.setPower(1);
        } else if (direction == "back") {
            AM.setPower(-1);
        }

        sleep(500);

        AM.setPower(0);
    }

    // hand
    public void hand(String openOrClose) {
        if (openOrClose == "open") {
            CS.setPosition(90);
        } else if (openOrClose == "close") {
            CS.setPosition(0);
        }
    }

    DcMotor FR, FL, BR, BL, DCM, AM;
    Servo CS;

    methodForEncoders encoders = new methodForEncoders();

    @Override
    public void runOpMode() throws InterruptedException {

        // All motors
        FL = hardwareMap.dcMotor.get("Front Left");
        FR = hardwareMap.dcMotor.get("Front Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        DCM = hardwareMap.dcMotor.get("Duck");
        AM = hardwareMap.dcMotor.get("Arm Motor");
        CS = hardwareMap.servo.get("Claw");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);

//        CS.setPosition(1);

        waitForStart();

        int targetToPlace = encoders.calculateToPlaceDistance(5);
        encoders(targetToPlace);

        FR.setPower(0.5);
        BR.setPower(0.5);
        FL.setPower(-0.5);
        FR.setPower(-0.5);

        sleep(1000);

        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        FR.setPower(0);

        targetToPlace = encoders.calculateToPlaceDistance(5);
        encoders(targetToPlace);



//        turn(1000, "right");
    }
}
