package Team7159.ComplexRobots;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import Team7159.BasicRobots.BasicMecanum;
import Team7159.BasicRobots.BasicMecanumRoverRuckus;

/*
    CONFIGURATION:
    ExpandDong

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

public class VacuumBotV3 extends BasicMecanumRoverRuckus {

    //The motor controlling rotating the vacuum
    public DcMotor chainMotor;

    //The motor controlling the chain input of the vacuum
    public DcMotor vacuumMotor;

    //The motor controlling the linear actuator's movement
    public DcMotor linearActuator;

    //The left servo used for moving vacuum in and out
    public CRServo lServo;

    //The right servo used for moving vacuum in and out
    public CRServo rServo;

    //The servo for moving the arm back into the lander
   public Servo liftServo;

    public void init(HardwareMap Map){

        super.init(Map);

        //Gets the actual hardware names from phone's config
        linearActuator = Map.dcMotor.get("linAct");
        linearActuator.setDirection(DcMotorSimple.Direction.REVERSE);
        chainMotor = Map.dcMotor.get("cM");
        vacuumMotor = Map.dcMotor.get("vM");

        //The motors need to go backward so these are set to reverse.

        vacuumMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        vacuumMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        lServo = Map.crservo.get("lS");
        rServo = Map.crservo.get("rS");

        liftServo = Map.servo.get("liftServo");
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
