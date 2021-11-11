package org.firstinspires.ftc.teamcode;

import android.graphics.Region;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

@TeleOp
@Disabled
public class GoingOverVuforia extends LinearOpMode {

        private static final String TFOD_MODEL_ASSET = "StonesAndChips.tflite";
        private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
        private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

        public static final String TAG = "Vuforia Navigation Sample";

        OpenGLMatrix lastLocation = null;

        VuforiaLocalizer vuforia;

        private TFObjectDetector tfod;

        @Override
        public void runOpMode() {
                /* c&p */
                int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
                /* ... */

                /* vuforia key */
                parameters.vuforiaLicenseKey = "AYHN1aL/////AAABmYrrSlCefkltl6fJdzJbMmsrPxVWJT3oTh/1nwkBjsa2mqa3lzXGv8PSdvit2XJmvJSo4yQbLZuJ/8GGiLyOUkxC+MSR6Xpc7zCnnWH3uhT/+PyaxU2+nrn67S3mxjLSC1oGXvdcbLhkoSDDyJ53K3sF4X0YdwtP9Jlg+i1RpJczM0t4Z1J2mkhufIpYCUgf4kqM4ie3T2Q/9EYkLgh1qlrM1yzTv8553fyxGtvLUc2rHWdqzuDuc32sQ7rQ81ZZNjKSjuesFKL2W7Fx2Pk660M7cWr6obPOa0KmL2NylbtEnP3RP0hQqBZ+6ZqRrWl6bAHZd0wjlxfnk+bzaIatkK2l3u2O057pHNg9vFE5CcsV";
                /* decide camera */

                parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

                /*idk*/
                /* c&p */
                vuforia = ClassFactory.getInstance().createVuforia(parameters);
                /* ... */

                /* trackables */
                VuforiaTrackables stonesAndChips = this.vuforia.loadTrackablesFromAsset("StonesAndChips");
                /* stones */
                VuforiaTrackable redTarget = stonesAndChips.get(0);
                redTarget.setName("RedTarget");
                /* chips */
                VuforiaTrackable blueTarget = stonesAndChips.get(1);
                redTarget.setName("BlueTarget");
                /* grouped trackables */
                List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
                allTrackables.addAll(stonesAndChips);

                /*measurements of bot and field*/
                float mmPerInch = 25.4f;
                float mmBotWidth = 18 * mmPerInch;
                float mmFieldWidth = (12 * 12 - 2) * mmPerInch;

                /* positioning of the pictures */
                OpenGLMatrix redTargetLocationOnField = OpenGLMatrix
                        .translation(-mmFieldWidth / 2, 0, 0)
                        .multiplied(Orientation.getRotationMatrix(
                                AxesReference.EXTRINSIC, AxesOrder.XZX,
                                AngleUnit.DEGREES, 90, 90, 0));
                redTarget.setLocation(redTargetLocationOnField);

                OpenGLMatrix blueTargetLocationOnField = OpenGLMatrix
                        .translation(0, mmFieldWidth / 2, 0)
                        .multiplied(Orientation.getRotationMatrix(
                                /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                                AxesReference.EXTRINSIC, AxesOrder.XZX,
                                AngleUnit.DEGREES, 90, 0, 0));
                blueTarget.setLocation(blueTargetLocationOnField);

                OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                        .translation(mmBotWidth / 2, 0, 0)
                        .multiplied(Orientation.getRotationMatrix(
                                AxesReference.EXTRINSIC, AxesOrder.YZY,
                                AngleUnit.DEGREES, -90, 0, 0));

                /* positioning completed */

                ((VuforiaTrackableDefaultListener) redTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);
                ((VuforiaTrackableDefaultListener) blueTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection);


                telemetry.addData(">", "Press Play to start tracking");
                telemetry.update();
                waitForStart();

                stonesAndChips.activate();

                int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                        "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
                TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
                tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
                tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);

                tfod.activate();

                while (opModeIsActive()) {
                        for (VuforiaTrackable trackable : allTrackables) {
                                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ? "Visible" : "Not Visible");

                                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                                if (robotLocationTransform != null) {
                                        lastLocation = robotLocationTransform;
                                }

                                float RobotCoords[] = lastLocation.getData();

                                if (RobotCoords[0] < 500) {
                                        //move foward
                                } else if (RobotCoords[0] > 500) {
                                        //move backward
                                } else {
                                        //yay!!!!!!!!
                                }



                        }
                        if (lastLocation != null) {
                                telemetry.addData("Pos", format(lastLocation));
                        } else {
                                telemetry.addData("Pos", "Unknown");
                        }
                        telemetry.update();

                }
        }

        String format(OpenGLMatrix transformationMatrix) {
                return transformationMatrix.formatAsTransform();
        }
}

