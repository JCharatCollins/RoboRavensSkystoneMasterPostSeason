package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.ComplexRobots.DR4BBotV1point5;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpSkystonesV3")
public class TeleOpSkystonesV3 extends LinearOpMode {

    //We make the robot
    private DR4BBotV1point5 robot = new DR4BBotV1point5();

    public static int magicNumber = 1440;

    public boolean clawClosed = true;

    public boolean foundClosed = false;

    @Override
    public void runOpMode() {
        //Initializes the hardware map (i.e. configuration)
        robot.init(hardwareMap);

        //resets lift encoders so all the way down is 0 ticks
        robot.rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int liftHeight = 0;
        //used to keep lift at a certain height without constant motor running
        int lastLiftHeight = 0;

        //servos move on init
        robot.leftFoundationServo.setPosition(0);
        robot.rightFoundationServo.setPosition(1);

        robot.leftLiftServo.setPosition(0);
        robot.rightLiftServo.setPosition(1);

        waitForStart();

        while (opModeIsActive()) {
            //Controls the target lift height
            if(gamepad1.a) {
                liftHeight -= 5 - (2*gamepad1.right_trigger);
//                telemetry.addData("A", "A Pressed");
            }
            if(gamepad1.y) {
                liftHeight += 5 - (2*gamepad1.right_trigger);
//                telemetry.addData("Y", "Y Pressed");
            }

            //prevents ticks from being negative
            if (liftHeight<0) {
                liftHeight =0;
            } else if (liftHeight > magicNumber) {
                liftHeight = magicNumber;
            }
//            if (lastLiftHeight == liftHeight) {
//                //don't adjust motors
//            } else {
//                //adjust motors
//                robot.leftLiftMotor.setTargetPosition(liftHeight);
//                robot.rightLiftMotor.setTargetPosition(liftHeight);
//            }
//
//            //to balance out power
//            robot.leftLiftMotor.setPower(1);
//            robot.rightLiftMotor.setPower(1);
//            //run the motors
//            if (liftHeight == robot.leftLiftMotor.getCurrentPosition() && liftHeight == robot.rightLiftMotor.getCurrentPosition()) {
//
//            } else {
//                robot.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
            telemetry.addData(" ", robot.rightLiftMotor.getCurrentPosition());
            telemetry.update();
            if (robot.rightLiftMotor.getCurrentPosition() != liftHeight) {
                robot.rightLiftMotor.setTargetPosition(liftHeight);
                robot.rightLiftMotor.setPower(1);
                robot.rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            if (robot.leftLiftMotor.getCurrentPosition() != liftHeight) {
                robot.leftLiftMotor.setTargetPosition(liftHeight);
                robot.leftLiftMotor.setPower(1);
                robot.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //reassign the last lift height

            //Controls the lift servos (Claw thing)
            if (gamepad1.x) {
                robot.leftLiftServo.setPosition(0);
                robot.rightLiftServo.setPosition(1);
            }
            if(gamepad1.b) {
                robot.leftLiftServo.setPosition(1);
                robot.rightLiftServo.setPosition(0);
            }

            //Left Stick--Acceleration
            double accel = -gamepad1.left_stick_y;

            //Right Stick--Rotation
            double rotate = gamepad1.right_stick_x;

            //Controls Award at Worlds 2020
            //Determines ratio of motor powers (by sides) using the right stick
            double rightRatio = 0.5 - (0.5 * rotate);
            double leftRatio = 0.5 + (0.5 * rotate);
            //Declares the maximum power any side can have
            double maxRatio = 1;

            //If we're turning left, the right motor should be at maximum power, so it decides the maxRatio. If we're turning right, vice versa.
            if (rotate < 0) {
                maxRatio = 1 / rightRatio;
            } else {
                maxRatio = 1 / leftRatio;
            }

            //Uses maxRatio to max out the motor ratio so that one side is always at full power.
            double powR = rightRatio * maxRatio;
            double powL = leftRatio * maxRatio;
            //Uses left trigger to determine slowdown.
            if(gamepad1.left_trigger>0.5) {
                robot.RFMotor.setPower((powR * accel)/2);
                robot.RBMotor.setPower((powR * accel)/2);
                robot.LFMotor.setPower((powL * accel)/2);
                robot.LBMotor.setPower((powL * accel)/2);
            } else {
                robot.RFMotor.setPower(powR * accel);
                robot.RBMotor.setPower(powR * accel);
                robot.LFMotor.setPower(powL * accel);
                robot.LBMotor.setPower(powL * accel);
            }

            //Tank rotation for the robot
            if(gamepad1.right_bumper) {
                if(gamepad1.left_trigger>0.5) {
                    robot.RFMotor.setPower(-0.5);
                    robot.LFMotor.setPower(0.5);
                    robot.RBMotor.setPower(-0.5);
                    robot.LBMotor.setPower(0.5);
                } else {
                    robot.RFMotor.setPower(-1);
                    robot.LFMotor.setPower(1);
                    robot.RBMotor.setPower(-1);
                    robot.LBMotor.setPower(1);
                }
            }
            if(gamepad1.left_bumper) {
                if(gamepad1.left_trigger>0.5) {
                    robot.RFMotor.setPower(0.5);
                    robot.LFMotor.setPower(-0.5);
                    robot.RBMotor.setPower(0.5);
                    robot.LBMotor.setPower(-0.5);
                } else {
                    robot.RFMotor.setPower(1);
                    robot.LFMotor.setPower(-1);
                    robot.RBMotor.setPower(1);
                    robot.LBMotor.setPower(-1);
                }
            }
            //Input cleaning (no weird priority)
            if(gamepad1.right_bumper && gamepad1.left_bumper) {
                robot.RFMotor.setPower(0);
                robot.LFMotor.setPower(0);
                robot.RBMotor.setPower(0);
                robot.LBMotor.setPower(0);
            }
            //Strafing controls (thanks Nick)
            if (gamepad1.dpad_up) {
                if (gamepad1.dpad_left) {
                    robot.RFMotor.setPower(1);
                    robot.LFMotor.setPower(0);
                    robot.RBMotor.setPower(0);
                    robot.LBMotor.setPower(1);
                } else if (gamepad1.dpad_right) {
                    robot.RFMotor.setPower(0);
                    robot.LFMotor.setPower(1);
                    robot.RBMotor.setPower(1);
                    robot.LBMotor.setPower(0);
                } else {
                    robot.RFMotor.setPower(1);
                    robot.LFMotor.setPower(1);
                    robot.RBMotor.setPower(1);
                    robot.LBMotor.setPower(1);
                }
            } else if (gamepad1.dpad_down) {
                if (gamepad1.dpad_left) {
                    robot.RFMotor.setPower(0);
                    robot.LFMotor.setPower(-1);
                    robot.RBMotor.setPower(-1);
                    robot.LBMotor.setPower(0);
                } else if (gamepad1.dpad_right) {
                    robot.RFMotor.setPower(-1);
                    robot.LFMotor.setPower(0);
                    robot.RBMotor.setPower(0);
                    robot.LBMotor.setPower(-1);
                } else {
                    robot.RFMotor.setPower(-1);
                    robot.LFMotor.setPower(-1);
                    robot.RBMotor.setPower(-1);
                    robot.LBMotor.setPower(-1);
                }
            }
            else {
                if (gamepad1.dpad_left) {
                    robot.RFMotor.setPower(1);
                    robot.LFMotor.setPower(-1);
                    robot.RBMotor.setPower(-1);
                    robot.LBMotor.setPower(1);
                } else if (gamepad1.dpad_right) {
                    robot.RFMotor.setPower(-1);
                    robot.LFMotor.setPower(1);
                    robot.RBMotor.setPower(1);
                    robot.LBMotor.setPower(-1);
                }
            }

        }

    }
}


