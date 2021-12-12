package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class teleOpCode extends LinearOpMode {
    DcMotor FR, FL, BR, BL, DCM;

    @Override
    public void runOpMode() throws InterruptedException {

        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        DCM = hardwareMap.dcMotor.get("Duck");
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // for speed of robot
                double speed = 0.7;

                if (gamepad1.dpad_up) {
                    speed += 0.1;
                }

                if (gamepad1.dpad_down) {
                    speed -= 0.1;
                }

                // move robot
                FR.setPower(gamepad1.right_stick_y * speed);
                BR.setPower(gamepad1.right_stick_y * speed);
                FL.setPower(gamepad1.left_stick_y * speed);
                BL.setPower(gamepad1.left_stick_y * speed);

                if (gamepad1.b) {
                    DCM.setPower(-0.5);
                }

                if (gamepad1.right_bumper) {
                    DCM.setPower(0.5);
                }

                if (gamepad1.x) {
                    DCM.setPower(0);
                }
            }
        }
    }
}
