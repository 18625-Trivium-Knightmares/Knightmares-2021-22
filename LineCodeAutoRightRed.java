package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

//import org.firstinspires.ftc.teamcode.methodForEncoders;


@Autonomous
public class LineCodeAutoRightRed extends LinearOpMode {

    DcMotor FR, FL, BR, BL, DCM, AM;
    Servo CS;

    @Override
    public void runOpMode() throws InterruptedException {

        // All motors
        FL = hardwareMap.dcMotor.get("Front Right");
        FR = hardwareMap.dcMotor.get("Front Left");
        BL = hardwareMap.dcMotor.get("Back Right");
        BR = hardwareMap.dcMotor.get("Back Left");
        DCM = hardwareMap.dcMotor.get("Duck");
        CS = hardwareMap.servo.get("Claw");
        AM = hardwareMap.dcMotor.get("Arm Motor");

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);

        //HOLD ONTO BLOCK
        CS.setPosition(1);

        //RESET ENCODERS
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /////////////////////////////////////////////////////////////////////////////////////////////
        waitForStart();

        //RAM TIME
        int ramTIME = (int) 2500;
        int distance = (int) ((537.689/(3.77953*3.1415926535))*16);

        //FORWARD SO THE ARM DOESN'T GET STUCK ON WALL
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FL.setTargetPosition(distance);
        FR.setTargetPosition(distance);
        BL.setTargetPosition(distance);
        BR.setTargetPosition(distance);

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

        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //SWING ARM UP
        AM.setPower(-0.4);
        sleep(2000);
        AM.setPower(0);

        FR.setPower(0.5);
        FL.setPower(-0.5);
        BR.setPower(0.5);
        BL.setPower(-0.5);

        sleep(1200);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        CS.setPosition(0.02);
        sleep(500);
        CS.setPosition(1);
        sleep(500);
        CS.setPosition(0.5);

        FR.setPower(-0.75);
        FL.setPower(-0.75);
        BR.setPower(-0.75);
        BL.setPower(-0.75);

        sleep(1000);

        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        AM.setPower(0.4);
        sleep(2000);
        AM.setPower(0);
    }
}
