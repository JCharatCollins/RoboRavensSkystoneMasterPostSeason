package Team7159.BasicRobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Team7159.Enums.Direction;
import Team7159.Enums.Version;
import Team7159.Utils.MotorGroup;
import Team7159.Utils.RobotComp;
import Team7159.Utils.RobotMath;

public class BasicMecanumSkystones {

    public RobotComp Comp = new RobotComp();

    MotorGroup Left;
    MotorGroup Right;

    public DcMotor LFMotor;
    public DcMotor RFMotor;
    public DcMotor LBMotor;
    public DcMotor RBMotor;

    public void init(HardwareMap Map) {

        LFMotor = Map.dcMotor.get("FLDrive");
        RFMotor = Map.dcMotor.get("FRDrive");
        LBMotor = Map.dcMotor.get("BLDrive");
        RBMotor = Map.dcMotor.get("BRDrive");

        LFMotor.setPower(0);
        RFMotor.setPower(0);
        LBMotor.setPower(0);
        RBMotor.setPower(0);

        //TODO: Figure out which motors need to be reversed, etc. so that the robot actually goes forward lmao
        LFMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RFMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LBMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RBMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        //for now, we do this (maybe change later)
        LFMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RFMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LBMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RBMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LBMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RBMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//      :crab: william is gone :crab:
    }

    //i honestly don't know what this stuff is for so uhhHhHhH leave it in?
    public void moveStraight(double power) {
        LFMotor.setPower(power);
        RFMotor.setPower(power);
        LBMotor.setPower(power);
        RBMotor.setPower(power);
    }

    public void stop() {
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        LBMotor.setPower(0);
        RBMotor.setPower(0);
    }

    public void turn(Direction direction, int degrees) {
        Right.resetEncoders();
        Left.resetEncoders();
        //TODO: Make sure wDistance is actually correct
        int LeftDistance = Comp.computeTurningPos(direction, degrees, Direction.LEFT, 17.5, Version.TWO);
        int RightDistance = Comp.computeTurningPos(direction, degrees, Direction.RIGHT, 17.5, Version.TWO);
        Left.setTargetPosition(LeftDistance);
        Right.setTargetPosition(RightDistance);
        Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        moveStraight(0.5);
        while (Left.isBusy() && Right.isBusy()) {
        }
        stop();
        Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void driveDir(Direction direction, double distance) {
        Right.resetEncoders();
        Left.resetEncoders();
        Right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        switch (direction) {
            case FORWARDS:
                int pos = -Comp.computePositionD(RobotMath.toMeters(distance), Version.TWO);
                Right.setTargetPosition(pos);
                Left.setTargetPosition(pos);
                break;
            case BACKWARDS:
                pos = Comp.computePositionD(RobotMath.toMeters(distance), Version.TWO);
                Right.setTargetPosition(pos);
                Left.setTargetPosition(pos);
                break;
        }
        moveStraight(0.5);
        while (Right.isBusy() && Left.isBusy()) {
        }
        stop();
        Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void driveDirPower(Direction direction, double power, double time) throws InterruptedException{
        switch (direction) {
            case FORWARDS:
                LFMotor.setPower(power);
                RFMotor.setPower(power);
                RBMotor.setPower(power);
                LBMotor.setPower(power);
                wait((int)time * 1000);
                stop();
                break;
            case BACKWARDS:
                LFMotor.setPower(-power);
                RFMotor.setPower(-power);
                RBMotor.setPower(-power);
                LBMotor.setPower(-power);
                wait((int)time * 1000);
                stop();
                break;
        }
        moveStraight(0.5);
        while (Right.isBusy() && Left.isBusy()) {
        }
        stop();
        Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void strafe(Direction direction, double power, double time) throws InterruptedException{

        if(direction == Direction.LEFT){
            LFMotor.setPower(-power);
            RFMotor.setPower(power);
            LBMotor.setPower(power);
            RBMotor.setPower(-power);
            wait((int)time * 1000);
            stop();
        }else if(direction == Direction.RIGHT){
            LFMotor.setPower(power);
            RFMotor.setPower(-power);
            LBMotor.setPower(-power);
            RBMotor.setPower(power);
            wait((int)time * 1000);
            stop();
        }else{
            //Throw an exception
        }
    }
}