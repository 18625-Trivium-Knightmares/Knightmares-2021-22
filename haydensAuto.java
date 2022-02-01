package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.methodForEncoders;


@Autonomous
public class haydensAuto extends LinearOpMode {

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

        /*
        Since the left wheels face in the opposite direction of the right ones if you set all of them
        to power 1 it will just turn so you would have to set the left to -1 and the right to 1
        unless you reverse them like above
         */

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

        //FORWARD SO THE ARM DOESN'T GET STUCK ON WALL
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        AM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);// Good point, I didn't think about this

        int power = (int) (0.5);
        FL.setPower(power);
        FR.setPower(power);
        BL.setPower(power);
        BR.setPower(power);

        sleep(10);

        int zero = (int) 0;
        FL.setPower(zero);
        FR.setPower(zero);
        BL.setPower(zero);
        BR.setPower(zero);

        //SWING ARM UP
        AM.setPower(-0.4);

        sleep(1500);

        AM.setPower(0);

        //WAKING UP ENCODERS
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // you had here RUN_TO_POSITION, i changed
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // that, bc that's what you use after you have
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // set the position you want it to go to
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //SETTING
        int distance = (int) (7*(90/3.14159));

        //FORWARD 7IN
        FL.setTargetPosition(distance);
        FR.setTargetPosition(distance);
        BL.setTargetPosition(distance);
        BR.setTargetPosition(distance);

        FL.setPower(0.5); // this sets the power that the motors will go
        FR.setPower(0.5);
        BL.setPower(0.5);
        BR.setPower(0.5);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION); // this actually starts the motors to the set position
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (FR.isBusy() || FL.isBusy() || BR.isBusy() || BL.isBusy()) {
            /*
            this while loop is what keeps it from reading the following code until it's where it's
            supposed to go, otherwise it would just move on to the code that sets the power to 0
             before it got to where it's supposed to
             */
        }

        FL.setPower(0); // this stops the motors, duh
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);

        //LEFT TURN
        int oneROTATION = (int) 537.69;
        FL.setTargetPosition(-oneROTATION);
        FR.setTargetPosition(oneROTATION);
        BL.setTargetPosition(-oneROTATION);
        BR.setTargetPosition(oneROTATION);

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

        //CLAW OPEN
        CS.setPosition(1); // right here you had 0.5, for some reason that doesn't work idk why,
        // in fact for the kill button in teleOp i actually had the dpad_up set it to position 0.5, lol

        //REVERSE
        FL.setTargetPosition(oneROTATION);
        FR.setTargetPosition(-oneROTATION);
        BL.setTargetPosition(oneROTATION);
        BR.setTargetPosition(-oneROTATION);

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

        FL.setTargetPosition(-distance);
        FR.setTargetPosition(-distance);
        BL.setTargetPosition(-distance);
        BR.setTargetPosition(-distance);

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

        //LEFT TURN
        FL.setTargetPosition(-oneROTATION / 4);
        FR.setTargetPosition(oneROTATION / 4);
        BL.setTargetPosition(-oneROTATION / 4);
        BR.setTargetPosition(oneROTATION / 4);

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

        //RAM INTO WAREHOUSE
        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        FR.setPower(-0.75);
        BR.setPower(-0.75);
        FL.setPower(-0.75);
        FR.setPower(-0.75);

        sleep(ramTIME);

        FR.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        FR.setPower(0);
    }
}
