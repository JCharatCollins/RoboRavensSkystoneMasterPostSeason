package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.ComplexRobots.DR4BBotV1point5;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpSkystonesV2.5")
public class TeleOpSkystonesV2point5 extends LinearOpMode {

    //We make the robot
    private DR4BBotV1point5 robot = new DR4BBotV1point5();

    public static int magicNumber = 5000;


    @Override
    public void runOpMode() {
        //Initializes the hardware map (i.e. configuration)
        robot.init(hardwareMap);

        robot.rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int liftHeight = 0; //it does the swag
        int lastLiftHeight = 0;
        waitForStart();

        while (opModeIsActive()) {
            robot.leftFoundationServo.setPosition(0);
            robot.rightFoundationServo.setPosition(0);

            //Controls the lift motors
            if(gamepad1.a) {
                liftHeight -= 10 - (5*gamepad1.right_trigger);
//                telemetry.addData("A", "A Pressed");
            }
            if(gamepad1.y) {
                liftHeight += 10 - (5*gamepad1.right_trigger);
//                telemetry.addData("Y", "Y Pressed");
            }

            if (liftHeight<0) {
                liftHeight =0;
            } else if (liftHeight > magicNumber) {
                liftHeight = magicNumber;
            }
            if (lastLiftHeight == liftHeight) {

            } else {
                robot.leftLiftMotor.setTargetPosition(liftHeight);
                robot.rightLiftMotor.setTargetPosition(liftHeight);
            }
//            telemetry.addData("Lift Height", liftHeight);
//            telemetry.update();
            robot.leftLiftMotor.setPower(0.5);
            robot.rightLiftMotor.setPower(0.5);
            robot.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lastLiftHeight = liftHeight;

            //Controls the lift servos (Claw thing)
            if(gamepad1.x) {
                if(robot.leftLiftServo.getPosition()>0.70 && robot.rightLiftServo.getPosition()<0.30) {
                    robot.leftLiftServo.setPosition(0.75);
                    robot.rightLiftServo.setPosition(0.25);
                } else if(robot.leftLiftServo.getPosition()<0.30 && robot.rightLiftServo.getPosition()>0.70) {
                    robot.leftLiftServo.setPosition(0.25);
                    robot.rightLiftServo.setPosition(0.75);
                }
            }
            if(gamepad1.b) {
                if(robot.leftFoundationServo.getPosition()>0.70 && robot.rightFoundationServo.getPosition()<0.30) {
                    robot.leftFoundationServo.setPosition(0.75);
                    robot.rightFoundationServo.setPosition(0.25);
                } else if(robot.leftFoundationServo.getPosition()<0.30 && robot.rightFoundationServo.getPosition()>0.70) {
                    robot.leftFoundationServo.setPosition(0.25);
                    robot.rightFoundationServo.setPosition(0.75);
                }
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

            //Spinny funny
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
            //Input cleaning
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


