package Team7159.ComplexRobots;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import Team7159.BasicRobots.BasicMecanum;

/*
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

public class VacuumBotTrial extends BasicMecanum {

    //The motor controlling the chain lift.
//    public DcMotor liftMotor;


    //The servo for moving the arm back into the lander.
    public Servo liftServo;

    //The motor for the linear actuator ascension.
    public DcMotor LinearActuator;

    public void init(HardwareMap Map){

        super.init(Map);

        //Gets the actual hardware names from phone's config
//        liftMotor = Map.dcMotor.get("lM");
        LinearActuator =Map.dcMotor.get("LinearActuator");

        //The motors need to go backward so these are set to reverse.
//        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
