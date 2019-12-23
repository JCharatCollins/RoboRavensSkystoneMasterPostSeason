package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.VacuumBotTrial;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOpTrial")
public class TeleOpTrial extends LinearOpMode {

    //Defines the direction of vertical movement, and to go while a button is pressed
    //Stands for Vertical Direction
    int vDir = 0;

    //Defines the direction of horizontal movement, and to go while a button is pressed
    //Stands for Horizontal Direction
    int hDir = 0;

    //Defines the direction of the chain rotational movement, and to go while a button is pressed
    //Stands for chain Direction
    int cDir = 0;

    //The robot
    private VacuumBotTrial robot = new VacuumBotTrial();


    @Override
    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();
        while(opModeIsActive()){

            //Sets the direction of the liftMotor for moving up or down

            if(gamepad1.y){
                vDir = 1;
            } else if(gamepad1.a){
                vDir = 2;
            }else{
                vDir = 0;
            }

            //Sets the direction of the CR servos for moving either in or out
            //Up should be moving out, down should be moving in

            if(gamepad1.dpad_up){
                robot.LinearActuator.setPower(1);
            }else if(gamepad1.dpad_down){
                robot.LinearActuator.setPower(-1);
            }else{
                robot.LinearActuator.setPower(0);
            }

            //Sets the direction of the chain for moving the cleaners in and out
            //X should be picking up, B should be shooting out

            if(gamepad1.x){
                cDir = 1;
            }else if(gamepad1.b){
                cDir = 2;
            }else{
                cDir = 0;
            }

            //Makes the lift motor go up and down

            if(vDir == 0){
//                robot.liftMotor.setPower(0);
            }else if(vDir == 1){
//                robot.liftMotor.setPower(1);
            }else if(vDir == 2){
//                robot.liftMotor.setPower(-1);
            }


            //TODO: Find the values to set armServo to dump back
            if(gamepad1.right_bumper){
                robot.liftServo.setPosition(1);
            }

            if(gamepad1.right_trigger>0.1){
                robot.liftServo.setPosition(0.32);
            }

            double rf = -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
            double rb = -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
            double lf = -gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
            double lb = -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;

            double maxr = Math.max(Math.abs(rf),Math.abs(rb));
            double maxl = Math.max(Math.abs(lf),Math.abs(lb));

            double max = Math.max(maxr, maxl);
            if(max != 0 && max > 1) {
                rf /= max;
                rb /= max;
                lf /= max;
                lb /= max;
            }

            robot.RFMotor.setPower(rf);
            robot.RBMotor.setPower(rb);
            robot.LFMotor.setPower(lf);
            robot.LBMotor.setPower(lb);
        }

    }


    //TODO: Find the positions for lowering the motor for the Vacuum using encoders or time
    public void lower(){

    }

    //TODO: Find the positions for raising the motor for the Vacuum using encoders or time
    public void raise(){

    }

}
