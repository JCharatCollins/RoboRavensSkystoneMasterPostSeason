//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import Team7159.ComplexRobots.DR4BBotV1;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpSkystonesV2")
//
//public class Amogh_Trash extends LinearOpMode{
//
//    //We make the robot
//    private DR4BBotV1 robot = new DR4BBotV1();
//
//    public void runOpMode() {
//        //Initializes the hardware map (i.e. configuration)
//        robot.init(hardwareMap);
//        waitForStart();
//
//        while (opModeIsActive()) {
//            if (gamepad1.right_bumper && gamepad1.left_bumper) {
//                if (gamepad1.dpad_up) {
//                    robot.RFMotor.setPower(1);
//                    robot.LFMotor.setPower(1);
//                    robot.RBMotor.setPower(1);
//                    robot.LBMotor.setPower(1);
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(0);
//                        robot.LFMotor.setPower(0);
//                        robot.RBMotor.setPower(-1);
//                        robot.LBMotor.setPower(-1);
//                    }
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(-1);
//                        robot.LFMotor.setPower(-1);
//                        robot.RBMotor.setPower(0);
//                        robot.LBMotor.setPower(0);
//                    }
//
//                }
//                if (gamepad1.dpad_down) {
//                    robot.RFMotor.setPower(1);
//                    robot.LFMotor.setPower(1);
//                    robot.RBMotor.setPower(1);
//                    robot.LBMotor.setPower(1);
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(0);
//                        robot.LFMotor.setPower(0);
//                        robot.RBMotor.setPower(-1);
//                        robot.LBMotor.setPower(-1);
//                    }
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(-1);
//                        robot.LFMotor.setPower(-1);
//                        robot.RBMotor.setPower(0);
//                        robot.LBMotor.setPower(0);
//                    }
//            }
//            else if (gamepad1.left_bumper) {
//                if (gamepad1.dpad_up) {
//                    robot.RFMotor.setPower(.75);
//                    robot.LFMotor.setPower(.75);
//                    robot.RBMotor.setPower(.75);
//                    robot.LBMotor.setPower(.75);
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(0);
//                        robot.LFMotor.setPower(0);
//                        robot.RBMotor.setPower(-.75);
//                        robot.LBMotor.setPower(-.75);
//                    }
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(-.75);
//                        robot.LFMotor.setPower(-.75);
//                        robot.RBMotor.setPower(0);
//                        robot.LBMotor.setPower(0);
//                    }
//            }
//                    if (gamepad1.dpad_down) {
//                        robot.RFMotor.setPower(.75);
//                        robot.LFMotor.setPower(.75);
//                        robot.RBMotor.setPower(.75);
//                        robot.LBMotor.setPower(.75);
//                        if(gamepad1.dpad_right){
//                            robot.RFMotor.setPower(0);
//                            robot.LFMotor.setPower(0);
//                            robot.RBMotor.setPower(-.75);
//                            robot.LBMotor.setPower(-.75);
//                        }
//                        if(gamepad1.dpad_right){
//                            robot.RFMotor.setPower(-.75);
//                            robot.LFMotor.setPower(-.75);
//                            robot.RBMotor.setPower(0);
//                            robot.LBMotor.setPower(0);
//                        }
//                        else {
//                if (gamepad1.dpad_up) {
//                    robot.RFMotor.setPower(.5);
//                    robot.LFMotor.setPower(.5);
//                    robot.RBMotor.setPower(.5);
//                    robot.LBMotor.setPower(.5);
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(0);
//                        robot.LFMotor.setPower(0);
//                        robot.RBMotor.setPower(-.5);
//                        robot.LBMotor.setPower(-.5);
//                    }
//                    if(gamepad1.dpad_right){
//                        robot.RFMotor.setPower(-.5);
//                        robot.LFMotor.setPower(-.5);
//                        robot.RBMotor.setPower(0);
//                        robot.LBMotor.setPower(0);
//                    }
//        }
//    }
//}
