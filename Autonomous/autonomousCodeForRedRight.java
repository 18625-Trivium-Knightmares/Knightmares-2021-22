package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.methodForEncoders;


@Autonomous
public class autonomousCodeForRedRight extends LinearOpMode {

    // start encoders
    public void startEncoders() {
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // exit encoders
    public void exitEncoders() {
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // restart encoders
    public void resetEncoders() {
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    // move forward or backward
    public void goForward(int tIme) {
        FR.setPower(0.75);
        FL.setPower(0.75);
        BR.setPower(0.75);
        BL.setPower(0.75);

        if (tIme != 0) { // IF THIS IS SET TO ANY NUMBER OTHER THAN 0 IT WILL MAKE IT SO THAT IT GOES
            sleep(tIme); // FORWARD FOR HOWEVER LONG IT IS SET TO, IF IT'S 0 IT WILL JUST SET
                         // THE MOTORS TO THE POWER INDEFINITELY
            FR.setPower(0);
            FL.setPower(0);
            BR.setPower(0);
            BL.setPower(0);
        }
    }

    public void goBackward(int tIme) {
        FR.setPower(-0.75);
        FL.setPower(-0.75);
        BR.setPower(-0.75);
        BL.setPower(-0.75);

        if (tIme != 0) { // IF THIS IS SET TO ANY NUMBER OTHER THAN 0 IT WILL MAKE IT SO THAT IT GOES
            sleep(tIme); // BACKWARD FOR HOWEVER LONG IT IS SET TO, IF IT'S 0 IT WILL JUST SET
                         // THE MOTORS TO THE POWER INDEFINITELY
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

        if (tIme != 0) { // IF THIS IS SET TO ANY NUMBER OTHER THAN 0 IT WILL MAKE IT SO THAT IT
            sleep(tIme); // TURNS FOR HOWEVER LONG IT IS SET TO, IF IT'S 0 IT WILL JUST SET
                         // THE MOTORS TO THE POWER INDEFINITELY
            FR.setPower(0);
            FL.setPower(0);
            BR.setPower(0);
            BL.setPower(0);
        }
    }

    // some encoders
    public void encoders(int targetToPlace) {

        startEncoders();

        FL.setTargetPosition(targetToPlace);
        FR.setTargetPosition(targetToPlace);
        BL.setTargetPosition(targetToPlace);
        BR.setTargetPosition(targetToPlace);

        FL.setPower(0.5);
        FR.setPower(0.5);
        BL.setPower(0.5);
        BR.setPower(0.5);

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

    DcMotor FR, FL, BR, BL, DCM, AM;
    Servo CS;

    methodForEncoders encoders = new methodForEncoders();

    @Override
    public void runOpMode() throws InterruptedException {

        // All motors
        FL = hardwareMap.dcMotor.get("Front Right");
        FR = hardwareMap.dcMotor.get("Front Left");
        BL = hardwareMap.dcMotor.get("Back Right");
        BR = hardwareMap.dcMotor.get("Back Left");
        DCM = hardwareMap.dcMotor.get("Duck");
        CS = hardwareMap.servo.get("Claw");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);

        // THIS CLOSES THE CLAW
        CS.setPosition(1);

        resetEncoders();

        waitForStart();

        // SO THE ARM DOESN'T GET STUCK
        goForward(400);

        // THIS SWINGS THE ARM OUT
        AM.setPower(0.4);

        sleep(1500);

        AM.setPower(0);

        // GO FORWARD 7IN
        int targetToPlace = encoders.calculateToPlaceDistance(7);
        encoders(targetToPlace);

        exitEncoders();

        sleep(1000);

        // TURNS LEFT BUT IF IT DOESN'T WORK (I HAVE REASON TO BELIEVE IT WONT) I'LL JUST UNCOMMENT
        // THE STUFF BELLOW IT AND GET RID OF THE METHOD
        turn(1800, "left");

//        FR.setPower(1);
//        BR.setPower(1);
//        FL.setPower(-1);
//        FR.setPower(-1);
//
//        sleep(1800);
//
//        FR.setPower(0);
//        BR.setPower(0);
//        FL.setPower(0);
//        FR.setPower(0);

        sleep(100);

        // THIS DROPS THE PRE-LOADED BOX
        CS.setPosition(0.02);

        sleep(100);

        // ALL THE WAY DOWN TO CS.setPosition(0.5) JUST CLOSES THE CLAW TO A POINT WHERE ITS
        // NOT IN THE WAY OF THE ARM SWINGING DOWN
        CS.setPosition(1);

        sleep(500);

        CS.setPosition(0.5);

        // ARM GOES DOWN
        AM.setPower(0.4);

        sleep(1500);

        AM.setPower(0);

        // TURNS RIGHT BUT IF IT DOESN'T WORK (I HAVE REASON TO BELIEVE IT WONT) I'LL JUST UNCOMMENT
        // THE STUFF BELLOW IT AND GET RID OF THE METHOD
        turn(500, "right");

//        FR.setPower(-1);
//        BR.setPower(-1);
//        FL.setPower(1);
//        BL.setPower(1);
//
//        sleep(500);
//
//        FR.setPower(0);
//        BR.setPower(0);
//        FL.setPower(0);
//        FR.setPower(0);

        goBackward(2000); // REVERSE
    }
}
