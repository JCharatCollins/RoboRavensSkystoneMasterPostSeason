package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import Team7159.ComplexRobots.DR4BBotV1point5;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpSkystonesV2.5")
public class TeleOpSkystonesV2point5 extends LinearOpMode {

    //We make the robot
    private DR4BBotV1point5 robot = new DR4BBotV1point5();

    public static int liftMotorTicks = 1440;

    public boolean clawClosed = true;

    public boolean foundClosed = false;

    public int clawCooldown = 0;

    public int clawSpecificPos = 3;

    @Override
    public void runOpMode() {
        //Initializes the hardware map using the active configurations
        robot.init(hardwareMap);

        //Resets lift encoders so their current position is set to 0 ticks.
        robot.rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //Sets the lift height as 0 to match the ticks.
        int liftHeight = 0;

        //Sets the position of the foundation servos and the lift servos to open.
        robot.leftFoundationServo.setPosition(0);
        robot.rightFoundationServo.setPosition(1);

        robot.leftLiftServo.setPosition(0);
        robot.rightLiftServo.setPosition(1);

        waitForStart();

        while (opModeIsActive()) {
            //Used to prevent claw inputs from being registered extremely rapidly, which would prevent the claw from moving at all. Decrements the cooldown by 1 every loop.
            clawCooldown -= 1;

            //Increments/decrements the target lift height based on both inputs from the A and Y buttons and the right trigger (which is used to slow down movement)
            if(gamepad1.a) {
                liftHeight -= 5 - (2*gamepad1.right_trigger);
            }
            if(gamepad1.y) {
                liftHeight += 5 - (2*gamepad1.right_trigger);
            }

            //Sets bound on ticks so that it can't go outside the maximum motor ticks or below 0.
            if (liftHeight<0) {
                liftHeight =0;
            } else if (liftHeight > liftMotorTicks) {
                liftHeight = liftMotorTicks;
            }

            //If the individual motor's current tick position is not equal to its target, it is readjusted.
            if (robot.rightLiftMotor.getCurrentPosition() != liftHeight) {
                robot.rightLiftMotor.setTargetPosition(liftHeight);
                robot.rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (robot.leftLiftMotor.getCurrentPosition() != liftHeight) {
                robot.leftLiftMotor.setTargetPosition(liftHeight);
                robot.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            //If the claw cooldown is up, then it allows the claw to move based on its current position.
            if (clawCooldown <= 0) {
                if (gamepad1.x) {
                    if (clawClosed) {
                        robot.leftLiftServo.setPosition(0);
                        robot.rightLiftServo.setPosition(1);
                        clawClosed = false;
                        clawCooldown = 500;
                    } else {
                        robot.leftLiftServo.setPosition(1);
                        robot.rightLiftServo.setPosition(0);
                        clawClosed = true;
                        clawCooldown = 500;
                    }
                }
            }
            //If the claw cooldown is up, then it allows the claw to move based on its current position.
            if(gamepad1.b) {
                if(foundClosed) {
                    robot.leftFoundationServo.setPosition(1);
                    robot.rightFoundationServo.setPosition(0);
                    foundClosed = false;
                } else {
                    robot.leftFoundationServo.setPosition(0);
                    robot.rightFoundationServo.setPosition(1);
                    foundClosed = true;
                }
            }

            //Left Stick--Acceleration
            double accel = -gamepad1.left_stick_y;

            //Right Stick--Rotation
            double rotate = gamepad1.right_stick_x;

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


