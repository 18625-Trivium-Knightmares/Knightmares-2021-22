package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XZY;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@TeleOp

public class teleOpCode extends LinearOpMode {

    // vuforia key
    private static final String VUFORIA_KEY = "AYHN1aL/////AAABmYrrSlCefkltl6fJdzJbMmsrPxVWJT3oTh/1nwkBjsa2mqa3lzXGv8PSdvit2XJmvJSo4yQbLZuJ/8GGiLyOUkxC+MSR6Xpc7zCnnWH3uhT/+PyaxU2+nrn67S3mxjLSC1oGXvdcbLhkoSDDyJ53K3sF4X0YdwtP9Jlg+i1RpJczM0t4Z1J2mkhufIpYCUgf4kqM4ie3T2Q/9EYkLgh1qlrM1yzTv8553fyxGtvLUc2rHWdqzuDuc32sQ7rQ81ZZNjKSjuesFKL2W7Fx2Pk660M7cWr6obPOa0KmL2NylbtEnP3RP0hQqBZ+6ZqRrWl6bAHZd0wjlxfnk+bzaIatkK2l3u2O057pHNg9vFE5CcsV";

    // tensorflow objects
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    // measurements
    private static final float mmPerInch = 25.4f;
    private static final float mmTargetHeight = 6 * mmPerInch;
    private static final float halfField = 72 * mmPerInch;
    private static final float halfTile = 12 * mmPerInch;
    private static final float oneAndHalfTile = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private VuforiaTrackables targets = null;
    private WebcamName webcam = null;

    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    private TFObjectDetector tfod;

    // move foward or backward
    public void goFoward(int tIme) {
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
            FR.setPower(0);
            BR.setPower(0);
        } else if (direction == "left") {
            FR.setPower(1);
            BR.setPower(1);
            FL.setPower(0);
            BL.setPower(0);
        } else {
            System.out.println(direction + " is not an option");
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

    // claw
    public void claw(String closeOpen) {
        if (closeOpen == "close") {
            CM.setPower(1);
        } else if (closeOpen == "open") {
            CM.setPower(-1);
        }

        sleep(500);

        CM.setPower(0);
    }

    // extend claw or pull it back in
    public void extendOrPull(String extendOrPull) {
        if (extendOrPull == "extend") {
            servo.setPosition(1);
        } else if (extendOrPull == "pull") {
            servo.setPosition(0);
        }
    }

    DcMotor FR, FL, BR, BL, RPM, LPM, CM;

    Servo servo, servo2;

    @Override
    public void runOpMode() throws InterruptedException {

        initVuforia();
        initTfod();

        // Our camera
        webcam = hardwareMap.get(WebcamName.class, "Webcam 1");

        // Camera stuff
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // Vuforia stuff
        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        // Camera being used
        parameters.cameraName = webcam;

        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects.
        targets = this.vuforia.loadTrackablesFromAsset("FreightFrenzy");
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targets);
        allTrackables.addAll(targets);

        // Targets
        identifyTarget(0, "Blue Storage",       -halfField,  oneAndHalfTile, mmTargetHeight, 90, 0, 90);
        identifyTarget(1, "Blue Alliance Wall",  halfTile,   halfField,      mmTargetHeight, 90, 0, 0);
        identifyTarget(2, "Red Storage",        -halfField, -oneAndHalfTile, mmTargetHeight, 90, 0, 90);
        identifyTarget(3, "Red Alliance Wall",   halfTile,  -halfField,      mmTargetHeight, 90, 0, 180);

        final float CAMERA_FORWARD_DISPLACEMENT = 0.0f * mmPerInch;   // eg: Enter the forward distance from the center of the robot to the camera lens
        final float CAMERA_VERTICAL_DISPLACEMENT = 6.0f * mmPerInch;   // eg: Camera is 6 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT = 0.0f * mmPerInch;   // eg: Enter the left distance from the center of the robot to the camera lens

        OpenGLMatrix cameraLocationOnRobot = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XZY, DEGREES, 90, 90, 0));

        // Some extra vuforia stuff
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setCameraLocationOnRobot(parameters.cameraName, cameraLocationOnRobot);
        }

        FR = hardwareMap.dcMotor.get("Front Right");
        FL = hardwareMap.dcMotor.get("Front Left");
        BR = hardwareMap.dcMotor.get("Back Right");
        BL = hardwareMap.dcMotor.get("Back Left");
        RPM = hardwareMap.dcMotor.get("Right Pulley");
        LPM = hardwareMap.dcMotor.get("Left Pulley");
        CM = hardwareMap.dcMotor.get("Claw");
        servo = hardwareMap.servo.get("daServo");
        servo2 = hardwareMap.servo.get("daServo2");
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        LPM.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        targets.activate();
        while (!isStopRequested()) {

            // Find visible trackables
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    // Updates on location
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            // Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (inches)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            } else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();
        }

        // Disable Tracking when we are done;
        targets.deactivate();

        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 16.0/9.0);
        }

        /* Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                // move robot
                FR.setPower(gamepad1.right_stick_y);
                BR.setPower(gamepad1.right_stick_y);
                FL.setPower(gamepad1.left_stick_y);
                BL.setPower(gamepad1.left_stick_y);

                // pick up things
                if (gamepad1.a) {
                    extendOrPull("extend");
                    claw("close");
                    extendOrPull("pull");
                }

                // drop object
                if (gamepad1.b) {
                    extendOrPull("extend");
                    claw("open");
                    extendOrPull("pull");
                }

                // bring claw down
                while (gamepad1.left_trigger > 0) {
                    wind("down", 0);
                }

                // bring claw up
                while(gamepad1.right_trigger > 0) {
                    wind("up", 0);
                }

                // to grab the nearest cube
                if (gamepad1.dpad_up) {
                    if (tfod != null) {
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                if (recognition.getLabel().equals("Cube")) {

                                    // center it on camera
                                    while (recognition.getRight() != 0) {
                                        if (recognition.getRight() > 0) {
                                            turn(0, "right");
                                        } else if (recognition.getRight() < 0) {
                                            turn(0, "left");
                                        }
                                    }

                                    // go foward to get it into claw
                                    while (recognition.getTop() > -5) {
                                        goFoward(0);
                                    }

                                    // grab object
                                    claw("close");
                                    wind("up", 500);
                                }
                                i++;
                            }
                            telemetry.update();
                        }
                    }
                }

                // to grab the nearest ball
                if (gamepad1.dpad_left) {
                    if (tfod != null) {
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                if (recognition.getLabel().equals("Ball")) {

                                    // center it on camera
                                    while (recognition.getRight() != 0) {
                                        if (recognition.getRight() > 0) {
                                            turn(0, "right");
                                        } else if (recognition.getRight() < 0) {
                                            turn(0, "left");
                                        }
                                    }

                                    // go foward to get it into claw
                                    while (recognition.getTop() > -5) {
                                        goFoward(0);
                                    }

                                    // grab object
                                    claw("close");
                                    wind("up", 500);
                                }
                                i++;
                            }
                            telemetry.update();
                        }
                    }
                }

                // to grab the nearest duck
                if (gamepad1.dpad_right) {
                    if (tfod != null) {
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null) {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());
                            int i = 0;
                            for (Recognition recognition : updatedRecognitions) {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());

                                if (recognition.getLabel().equals("Duck")) {

                                    // center it on camera
                                    while (recognition.getRight() != 0) {
                                        if (recognition.getRight() > 0) {
                                            turn(0, "right");
                                        } else if (recognition.getRight() < 0) {
                                            turn(0, "left");
                                        }
                                    }

                                    // go foward to get it into claw
                                    while (recognition.getTop() > -5) {
                                        goFoward(0);
                                    }

                                    // grab object
                                    claw("close");
                                    wind("up", 500);
                                }
                                i++;
                            }
                            telemetry.update();
                        }
                    }
                }
            }
        }
    }

    void    identifyTarget(int targetIndex, String targetName, float dx, float dy, float dz, float rx, float ry, float rz) {
        VuforiaTrackable aTarget = targets.get(targetIndex);
        aTarget.setName(targetName);
        aTarget.setLocation(OpenGLMatrix.translation(dx, dy, dz)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, rx, ry, rz)));
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }


    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
