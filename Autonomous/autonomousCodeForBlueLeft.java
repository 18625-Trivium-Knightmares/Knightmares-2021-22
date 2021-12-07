package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous
public class autonomousCodeForBlueLeft extends LinearOpMode {

    // move foward or backward
    public void goFoward(int tIme) {
        FR.setPower(1);
        FL.setPower(1);
        BR.setPower(1);
        BL.setPower(1);

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
            FR.setPower(0);
            BR.setPower(0);
        } else if (direction == "left") {
            FR.setPower(0.5);
            BR.setPower(0.5);
            FL.setPower(0);
            BL.setPower(0);
        } else {
            System.out.println(direction + " is not an option");
        }

        if (tIme != 0) {
            sleep(tIme);
            FL.setPower(0);
            FR.setPower(0);
            BL.setPower(0);
            BR.setPower(0);
        }
    }

    // pull claw up or down
    public void wind(String upDown, int tIme) {
        if (upDown == "up") {
            RPM.setPower(1);
            LPM.setPower(1);
        } else if (upDown == "down") {
            RPM.setPower(-1);
            LPM.setPower(-1);
        }

        if (tIme != 0) {
            sleep(tIme);
            RPM.setPower(0);
            LPM.setPower(0);
        }
    }

    // some encoders
    public void encoders(int targetToPlace) {
        FL.setTargetPosition(targetToPlace);
        FR.setTargetPosition(targetToPlace);
        BL.setTargetPosition(targetToPlace);
        BR.setTargetPosition(targetToPlace);

        FL.setPower(1);
        FR.setPower(1);
        BL.setPower(1);
        BR.setPower(1);

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
        DCM.setPower(1);

        sleep(tIme);

        DCM.setPower(0);
    }

    // claw
    public void claw(String closeOpen) {
        if (closeOpen  == "close") {
            CM.setPower(1);
            sleep(500);
            CM.setPower(0);
        } else if (closeOpen == "open") {
            CM.setPower(-1);
            sleep(500);
            CM.setPower(0);
        }
    }

    // extend claw or pull it back in
//    public void extend() {
//        servo.setPosition(1);
//    }
//    public void pull() {
//        servo.setPosition(0);
//    }
//
//    // grab or drop objects
//    public void grab() {
//        extend();
//        claw("close");
//        pull();
//    }
//
//    public void drop() {
//        extend();
//        claw("open");
//        pull();
//    }

    DcMotor FR, FL, BR, BL, RPM, LPM, DCM, CM;

//    Servo servo, servo2;

    @Override
    public void runOpMode() throws InterruptedException {

        // All motors
        FL = hardwareMap.dcMotor.get("Front Left");
        FR = hardwareMap.dcMotor.get("Front Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        RPM = hardwareMap.dcMotor.get("Right Pulley");
        LPM = hardwareMap.dcMotor.get("Left Pulley");
        DCM = hardwareMap.dcMotor.get("Duck");
        CM = hardwareMap.dcMotor.get("Claw");
//        servo = hardwareMap.servo.get("daServo");
//        servo2 = hardwareMap.servo.get("daServo2");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        LPM.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        /** Strategy:
         * Before the robot moves locate where the Team shipping element on the barcode is in the frame.
         * Based off where the team shipping element is place the freight on the corresponding level.
         * Next deliver a duck from the carousel to the floor
         * Deliver as much freight as possible to any level of the shipping hub and try to keep it balanced.
         * Park completely in warehouse
         */

    }
}
