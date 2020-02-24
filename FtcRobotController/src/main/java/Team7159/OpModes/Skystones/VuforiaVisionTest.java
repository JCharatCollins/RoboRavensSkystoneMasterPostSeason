package Team7159.OpModes.Skystones;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.CameraDevice;
import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.ComplexRobots.DR4BBotV1point5;
import Team7159.Enums.Direction;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.nio.ByteBuffer;
import java.util.List;

import static Team7159.Utils.BitmapManip.RotateBitmap180;
import static Team7159.Utils.BitmapManip.saveImageToExternalStorage;

@Autonomous(name = "VuforiaVisionTest")
public class VuforiaVisionTest extends LinearOpMode {

    private DR4BBotV1point5 robot = new DR4BBotV1point5();

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY = "AVQx3Z//////AAABmVK/kYWBd0i+jtzhfVaTEEGB/9UU06YBrbJ7V/Q1i+5oKpBfvwQBWBRdubFFs3wQ1t61Nvq9y7OXZ+o15e0T4GcdhsuDp0bl4L3YN8UfTVKSiICs3raP/Q8ioy2AdBNZKoU0gir7kq8ITK20UvvMt7aPzg7bDn3V03CJnThh27Cxz5MPsaJXXpEDawy6hOq7aYWjmEoEgh/q+7A6pyct0Sut6kq0TJbRM5C59QmnRlxr7hZm3qCrvcgxZvEGExPLmyCCKn8k/hsebsbFiGvqgdBspWk7fGOLG63JOOp5DyZe4xVG1EtY3Innk2vb/Re8OJsdACAFTl0YhhAKNsCdmXTfTXqqDgqywfgs4XKqGvT/";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }

//        vuforia.setFrameQueueCapacity(6);
//        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

        robot.init(hardwareMap);

        waitForStart();

        drive(-0.25, 0.5);
        robot.LFMotor.setPower(0);
        robot.RFMotor.setPower(0);
        robot.LBMotor.setPower(0);
        robot.RBMotor.setPower(0);
        boolean stoneHasBeenFound = false;
        while (!stoneHasBeenFound) {
            if (tfod == null) {
                telemetry.addData("TFOD STATUS", "NULL");
            } else {
                telemetry.addData("TFOD STATUS", "NULLN'T");
            }
            telemetry.update();

            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                if (updatedRecognitions.size() >= 1) {
                    Recognition recObject = updatedRecognitions.get(0);
                    telemetry.addData("recObject:", updatedRecognitions.get(0));
                    if (recObject.getLabel().equals(LABEL_FIRST_ELEMENT)) {
                        telemetry.addData("OBJECT FOUND:", "STONE");
                        telemetry.update();
                        strafe(Direction.LEFT, 0.25, 0.25);
                    }
                    if (recObject.getLabel().equals(LABEL_SECOND_ELEMENT)) {
                        telemetry.addData("OBJECT FOUND:", "SKYSTONE");
                        telemetry.update();
                        drive(-0.25, 1);
                        stoneHasBeenFound = true;
                    }
                }
            }
        }
    }
    //Finds first skystone
    //Driving methods
    private void stopMotors(){
        robot.stop();
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.80;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    private void drive(double pow, double time) {
        double t = time*1000;
        int t1 = (int)t;
        robot.LFMotor.setPower(pow);
        robot.RFMotor.setPower(pow);
        robot.LBMotor.setPower(pow);
        robot.RBMotor.setPower(pow);
        sleep(t1);
        stopMotors();
    }

    private void strafe(Direction dir, double pow, double time){
        double t = time*1000;
        int t1 = (int)t;
        if(dir == Direction.LEFT){
            robot.LFMotor.setPower(-pow);
            robot.RFMotor.setPower(pow);
            robot.LBMotor.setPower(pow);
            robot.RBMotor.setPower(-pow);
            sleep(t1);
            stopMotors();
        }else if(dir == Direction.RIGHT){
            robot.LFMotor.setPower(pow);
            robot.RFMotor.setPower(-pow);
            robot.LBMotor.setPower(-pow);
            robot.RBMotor.setPower(pow);
            sleep(t1);
            stopMotors();
        }
    }

//    private void turn(Direction dir, double pow, double time){
//        double t = time*1000;
//        int t1 = (int)t;
//        if(dir == Direction.LEFT){
//            robot.RFMotor.setPower(pow);
//            robot.RBMotor.setPower(pow);
//            robot.LFMotor.setPower(-pow);
//            robot.LBMotor.setPower(-pow);
//            sleep(t1);
//        }else if(dir == Direction.RIGHT){
//            robot.RFMotor.setPower(-pow);
//            robot.RBMotor.setPower(-pow);
//            robot.LFMotor.setPower(pow);
//            robot.LBMotor.setPower(pow);
//            sleep(t1);
//        }
//        stopMotors();
//    }

//    private void moveStraight(Direction dir, double pow, double time){
//        double t = time*1000;
//        int t1 = (int)t;
//        if(dir == Direction.FORWARDS){
//            robot.RFMotor.setPower(pow);
//            robot.RBMotor.setPower(pow);
//            robot.LFMotor.setPower(pow);
//            robot.LBMotor.setPower(pow);
//            sleep(t1);
//        }else if(dir == Direction.BACKWARDS){
//            robot.RFMotor.setPower(-pow);
//            robot.RBMotor.setPower(-pow);
//            robot.LFMotor.setPower(-pow);
//            robot.LBMotor.setPower(-pow);
//            sleep(t1);
//        }
//        stopMotors();
//    }

    private Bitmap getBitmap() throws InterruptedException{
        Frame frame;
        Bitmap BM0 = Bitmap.createBitmap(new DisplayMetrics(), 100, 100, Bitmap.Config.RGB_565);
        if (vuforia.getFrameQueue().peek() != null) {
            frame = vuforia.getFrameQueue().take();
            for (int i = 0; i < frame.getNumImages(); i++) {
                if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                    Image image = frame.getImage(i);
                    ByteBuffer pixels = image.getPixels();
                    Matrix matrix = new Matrix();
                    matrix.preScale(-1, -1);
                    Bitmap bitmap = Bitmap.createBitmap(new DisplayMetrics(), image.getWidth(), image.getHeight(), Bitmap.Config.RGB_565);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                    bitmap.copyPixelsFromBuffer(pixels);
                    return bitmap;
                }
            }
        }
        return BM0;
    }

    private void takePic(){
        try {
            Bitmap bitmap = getBitmap();
            Bitmap newBitmap = RotateBitmap180(bitmap);
            saveImageToExternalStorage(newBitmap);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

/*    private void moveRight(int distance)
    {
        robot.turn(Direction.RIGHT, 90);
        robot.driveDir(Direction.FORWARDS, distance);
        robot.turn(Direction.LEFT, 90);
    }*/

}