package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class teleOpCode extends LinearOpMode {
    // wind up
    public void wind(String upOrDown, int tIme) {
        if (upOrDown == "up") {
            RPM.setPower(-0.75);
            LPM.setPower(-0.75);
        } else if (upOrDown == "down") {
            RPM.setPower(0.75);
            LPM.setPower(0.75);
        }
    }

    // duck carousel
    public void duck(int tIme) {
        DCM.setPower(-0.5);

        sleep(tIme);

        DCM.setPower(0);
    }

    DcMotor FR, FL, BR, BL, RPM, LPM, IM, DCM;

    @Override
    public void runOpMode() throws InterruptedException {

        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        RPM = hardwareMap.dcMotor.get("Right Pulley");
        LPM = hardwareMap.dcMotor.get("Left Pulley");
        IM = hardwareMap.dcMotor.get("Intake");
        DCM = hardwareMap.dcMotor.get("Duck");
        FR.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);
        LPM.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // for speed of robot
                double speed = 0.5;

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

                // pull claw up
                if (gamepad1.y) {
                    wind("up", 500);
                }

                // bring claw down
                if (gamepad1.a) {
                    wind("down", 500);
                }

                if (gamepad1.b) {
                    DCM.setPower(-1);
                }

                if (gamepad1.x) {
                    DCM.setPower(0);
                }

                // pick up
                if (gamepad1.left_trigger > 0) {
                    IM.setPower(-0.25);
                }

                if (gamepad1.left_bumper) {
                    IM.setPower(0);
                }
                
                // drop
                if (gamepad1.right_trigger > 0) {
                    IM.setPower(0.25);
                }
                
                if (gamepad1.right_bumper) {
                    IM.setPower(0);
                }
            }
        }
    }
}
