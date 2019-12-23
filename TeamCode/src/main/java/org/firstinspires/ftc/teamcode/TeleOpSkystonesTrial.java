package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.DR4BBotV1;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp")
public class TeleOpSkystonesTrial extends LinearOpMode {

    //we make the robot
    private DR4BBotV1 robot = new DR4BBotV1();


    @Override
    public void runOpMode(){

        robot.init(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            robot.leftLiftServo.setPosition(0);
        }

    }

}
