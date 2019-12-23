package Team7159.ComplexRobots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import Team7159.BasicRobots.BasicMecanumSkystones;

/*
    CONFIGURATION:
    DR4BBotV1

    EXPANSION HUB 1
    MOTORS:
    0-RF NeveRest 40 Gearmotor
    1-LF NeveRest 40 Gearmotor
    2-LB NeveRest 40 Gearmotor
    3-RB NeveRest 40 Gearmotor
    SERVOS:
    2-liftServo Servo

    I2C Bus 0-imu REV Expansion Hub IMU
    -----------------------------------
    EXPANSION HUB 2
    MOTORS:
    0-vM NeveRest 40 Gearmotor
    1-cM Tetrix Motor
    2-lM NeveRest 40 Gearmotor
    3-LinearActuator Other
    SERVOS:
    0-rS Continuous Rotation Servo

    I2C Bus 0-imu 1 REV Expansion Hub IMU

 */

public class DR4BBotV2 extends BasicMecanumSkystones {

    //The motor controlling rotating the vacuum
    public DcMotor rightLiftMotor;

    //The motor controlling the chain input of the vacuum
    public DcMotor leftLiftMotor;

    //The motor controlling the linear actuator's movement
    public Servo rightLiftServo;

    //The left servo used for moving vacuum in and out
    public Servo leftLiftServo;

    //The foundation-moving claw servos
    public Servo leftFoundationServo;
    public Servo rightFoundationServo;

    public void init(HardwareMap Map){

        super.init(Map);

        //Gets the actual hardware names from phone's config
        rightLiftMotor = Map.dcMotor.get("RLift");
        leftLiftMotor = Map.dcMotor.get("LLift");
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftLiftServo = Map.servo.get("LSLift");
        rightLiftServo = Map.servo.get("RSLift");

        leftFoundationServo = Map.servo.get("LBServo");
        rightFoundationServo = Map.servo.get("RBServo");

        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    private ElapsedTime period = new ElapsedTime();

    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }

}
