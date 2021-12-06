package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class teleOpCode extends LinearOpMode {

    // pull claw up or down
    public void wind(String upDown, int tIme) {
        if (upDown == "up") {
            RPM.setPower(-0.75);
            LPM.setPower(-0.75);
        } else if (upDown == "down") {
            RPM.setPower(0.75);
            LPM.setPower(0.75);
        }

        if (tIme != 0) {
            sleep(tIme);
            RPM.setPower(0);
            LPM.setPower(0);
        }
    }

    // extend or pull claw
    public void extend() { servo.setPosition(1); }

    public void pull() { servo.setPosition(0); }

    // claw
    public void claw(String closeOpen) {
        if (closeOpen == "close") { CM.setPower(1); }
        else if (closeOpen == "open") { CM.setPower(-1); }

        sleep(500);

        CM.setPower(0);
    }

    // grab or drop object
    public void grab() {
        extend();
        claw("close");
        pull();
    }

    public void drop() {
        extend();
        claw("open");
        pull();
    }

    // duck carousel
    public void duck(int tIme) {
        DCM.setPower(1);

        sleep(tIme);

        DCM.setPower(0);
    }

    DcMotor FR, FL, BR, BL, RPM, LPM, CM, DCM;

    Servo servo, servo2;


    @Override
    public void runOpMode() throws InterruptedException {
        

        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        RPM = hardwareMap.dcMotor.get("Right Pulley");
        LPM = hardwareMap.dcMotor.get("Left Pulley");
        CM = hardwareMap.dcMotor.get("Claw");
        DCM = hardwareMap.dcMotor.get("Duck");
        servo = hardwareMap.servo.get("daServo");
        servo2 = hardwareMap.servo.get("daServo2");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
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
                    wind("up", 500); // 500 is temp
                }

                // bring claw down
                if (gamepad1.a) {
                    wind("down", 500); //500 is temp
                }

                if (gamepad1.b) {
                    duck(5000);
                }
            }
        }
    }
}
