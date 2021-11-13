package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp

public class teleOpCode extends LinearOpMode {
    DcMotor FR, FL, BR, BL, RPM, LPM;

    @Override

    public void runOpMode() throws InterruptedException {
        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        RPM = hardwareMap.dcMotor.get("Right Pulley System");
        LPM = hardwareMap.dcMotor.get("Left Pulley System");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        LPM.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.left_stick_y != 0) {
                FR.setPower(gamepad1.left_stick_y);
                FL.setPower(gamepad1.left_stick_y);
                BR.setPower(gamepad1.left_stick_y);
                BL.setPower(gamepad1.left_stick_y);
            }
            if (gamepad1.left_stick_x != 0) {
                FR.setPower(-gamepad1.left_stick_x);
                BR.setPower(-gamepad1.left_stick_x);
                FL.setPower(gamepad1.left_stick_x);
                BL.setPower(gamepad1.left_stick_x);
            }
            if (gamepad1.b) {
                RPM.setPower(-1);
                LPM.setPower(-1);
            }
            if (gamepad1.a) {
                RPM.setPower(1);
                LPM.setPower(1);
            }
            
        }
    }
}
