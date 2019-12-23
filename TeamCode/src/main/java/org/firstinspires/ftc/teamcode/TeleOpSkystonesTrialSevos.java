package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.DR4BBotV1;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpTrialServos")
public class TeleOpSkystonesTrialSevos extends LinearOpMode {

    //we make the robot
    private DR4BBotV1 robot = new DR4BBotV1();


    @Override
    public void runOpMode(){

        robot.init(hardwareMap);
        waitForStart();
        robot.leftLiftServo.setPosition(0);
        robot.rightLiftServo.setPosition(0);
        while(opModeIsActive()){
            robot.leftLiftServo.setPosition(1);
            robot.rightLiftServo.setPosition(1);
            telemetry.addData("servo pos changed", "0 to 1");
            telemetry.update();
        }

    }

}
