package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class teleOpCode extends LinearOpMode {

    // some encoders
    public void encoders(int rotations) {
        AM.setTargetPosition(rotations);
        AM.setPower(0.4);
        AM.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (AM.isBusy()) {
        }
        AM.setPower(0);
    }

    DcMotor FR, FL, BR, BL, DCM, AM;
    Servo CS;

    methodForEncoders encoders = new methodForEncoders();

    @Override
    public void runOpMode() throws InterruptedException {

        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        DCM = hardwareMap.dcMotor.get("Duck");
        AM = hardwareMap.dcMotor.get("Arm Motor");
        CS = hardwareMap.servo.get("Claw");
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {

                // move robot
                FR.setPower(gamepad1.right_stick_y * 0.5);
                BR.setPower(gamepad1.right_stick_y * 0.5);
                FL.setPower(gamepad1.left_stick_y * 0.5);
                BL.setPower(gamepad1.left_stick_y * 0.5);

                if (gamepad1.left_bumper) {
                    DCM.setPower(-1);
                }

                if (gamepad1.right_bumper) {
                    DCM.setPower(1);
                }

                if (gamepad1.a) {
                    DCM.setPower(0);
                }

                if (gamepad1.right_trigger != 0) {
                    int rotations = encoders.calculateToPlaceRotations(0.1);
                    encoders(rotations);
                }

                if (gamepad1.left_trigger != 0) {
                    int rotations = encoders.calculateToPlaceRotations(-0.1);
                    encoders(rotations);
                }

                if (gamepad1.y) {
                    AM.setPower(0);
                }

                if (gamepad1.x) {
                    CS.setPosition(1);
                }

                if (gamepad1.b) {
                    CS.setPosition(0.02);
                }
            }
        }
    }
}
