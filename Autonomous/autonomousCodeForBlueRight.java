package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.methodForEncoders;


@Autonomous
public class autonomousCodeForBlueRight extends LinearOpMode {

    // move forward or backward
    public void goForward(int tIme) {
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
            FL.setPower(1);
            BL.setPower(1);
            FR.setPower(-1);
            BR.setPower(-1);
        } else if (direction == "left") {
            FR.setPower(1);
            BR.setPower(1);
            FL.setPower(-1);
            BL.setPower(-1);
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

        sleep(500);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    }

    // duck carousel spinner
    public void spinDuck(int tIme) {
        DCM.setPower(-1);

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

    DcMotor FR, FL, BR, BL, RPM, LPM, DCM, CM;
    
    methodForEncoders encoders = new methodForEncoders();

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
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        LPM.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        /** Strategy:
         * Next deliver a duck from the carousel to the floor
         * Park completely in warehouse
         */

        int Target = encoders.calculateToPlace(1, 1338, 5);
        encoders(Target);

        turn(1500, "right");
        Target = encoders.calculateToPlace(1, 1338, 5);
        encoders(Target);

        turn(1500, "left");
        Target = encoders.calculateToPlace(1, 1338, 5);
        encoders(-Target);
        spinDuck(1000);

        Target = encoders.calculateToPlace(1, 1338, 5);
        encoders(Target);

        turn(1500, "left");
        Target = encoders.calculateToPlace(1, 1338, 5);
        encoders(Target);

        turn(1500, "left");
        Target = encoders.calculateToPlace(1, 1338, 5);
        encoders(Target);

        turn(1500, "right");
        Target = encoders.calculateToPlace(8, 1338, 5);
        encoders(Target);
    }
}
