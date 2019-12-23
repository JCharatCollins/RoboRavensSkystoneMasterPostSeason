package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.ComplexRobots.DR4BBotV2;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpSkystonesV3")
public class TeleOpSkystonesV3 extends LinearOpMode {

    //We make the robot
    private DR4BBotV2 robot = new DR4BBotV2();

    @Override
    public void runOpMode() {
        //Initializes the hardware map (i.e. configuration)
        robot.init(hardwareMap);
        waitForStart();

        int targetLiftPos = 0;

        while (opModeIsActive()) {

            //Controls the lift motors
            if(gamepad1.a) {
                if(gamepad1.right_trigger>0.5){
                    targetLiftPos -= 0.05;
                } else {

                }
            }
            if(gamepad1.y) {
                if(gamepad1.right_trigger>0.5){
                    targetLiftPos += 0.05;
                } else {
                    targetLiftPos += 0.1;
                }
            }
            robot.leftLiftMotor.setTargetPosition(targetLiftPos);
            robot.rightLiftMotor.setTargetPosition(targetLiftPos);

            if(targetLiftPos<0) {
                targetLiftPos = 0;
            }
            if(targetLiftPos>1) {
                targetLiftPos = 1;
            }

            robot.rightLiftMotor.setTargetPosition(targetLiftPos);
            //Controls the lift servos (Claw thing)
            if(gamepad1.x) {
                if(robot.leftLiftServo.getPosition()==1 && robot.rightLiftServo.getPosition()==1) {
                    robot.leftLiftServo.setPosition(0);
                    robot.rightLiftServo.setPosition(0);
                } else if(robot.leftLiftServo.getPosition()==0 && robot.rightLiftServo.getPosition()==0) {
                    robot.leftLiftServo.setPosition(1);
                    robot.rightLiftServo.setPosition(1);
                }
            }
            if(gamepad1.b) {
                if(robot.leftFoundationServo.getPosition()==1 && robot.rightFoundationServo.getPosition()==1) {
                    robot.leftFoundationServo.setPosition(0);
                    robot.rightFoundationServo.setPosition(0);
                } else if(robot.leftFoundationServo.getPosition()==0 && robot.rightFoundationServo.getPosition()==0) {
                    robot.leftFoundationServo.setPosition(1);
                    robot.rightFoundationServo.setPosition(1);
                }
            }
            //Input cleaning
            if(gamepad1.a && gamepad1.b) {
                robot.leftLiftServo.setPosition(0);
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


