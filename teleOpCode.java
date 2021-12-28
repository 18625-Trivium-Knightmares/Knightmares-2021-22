package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class teleOpCode extends LinearOpMode {
    DcMotor FR, FL, BR, BL, DCM, EJ;
    Servo CS;

    @Override
    public void runOpMode() throws InterruptedException {

        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        DCM = hardwareMap.dcMotor.get("Duck");
        EJ = hardwareMap.dcMotor.get("Elbow Joint");
        CS = hardwareMap.servo.get("Claw");
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                // move robot
                FR.setPower(gamepad1.right_stick_y * .5);
                BR.setPower(gamepad1.right_stick_y * .5);
                FL.setPower(gamepad1.left_stick_y * .5);
                BL.setPower(gamepad1.left_stick_y * .5);

                if (gamepad1.left_stick_x > 0) {
                    FR.setPower(gamepad1.left_stick_x * .5);
                    BL.setPower(gamepad1.left_stick_x * .5);
                    FL.setPower(-gamepad1.left_stick_x * .5);
                    BR.setPower(-gamepad1.left_stick_x * .5);
                    
                    if (gamepad1.left_stick_x == 0) {
                        FR.setPower(0);
                        BR.setPower(0);
                        BL.setPower(0);
                        BR.setPower(0);
                    }
                } else if (gamepad1.left_stick_x < 0) {
                    FR.setPower(-gamepad1.left_stick_x * .5);
                    BL.setPower(-gamepad1.left_stick_x * .5);
                    FL.setPower(gamepad1.left_stick_x * .5);
                    BR.setPower(gamepad1.left_stick_x * .5);

                    if (gamepad1.left_stick_x == 0) {
                        FR.setPower(0);
                        BR.setPower(0);
                        BL.setPower(0);
                        BR.setPower(0);
                    }
                }

                if (gamepad1.b) {
                    DCM.setPower(-0.5);
                }

                if (gamepad1.right_bumper) {
                    DCM.setPower(0.5);
                }

                if (gamepad1.x) {
                    DCM.setPower(0);
                }

                EJ.setPower(gamepad1.right_stick_x);

                if (gamepad1.a) {
                    CS.setPosition(5);
                }
            }
        }
    }
}
