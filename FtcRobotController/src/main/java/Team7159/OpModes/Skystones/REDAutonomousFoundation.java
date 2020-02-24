package Team7159.OpModes.Skystones;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.ComplexRobots.DR4BBotV1point5;
import Team7159.Enums.Direction;

@Autonomous(name = "REDAutonomousFoundation")
public class REDAutonomousFoundation extends LinearOpMode {

    private DR4BBotV1point5 robot = new DR4BBotV1point5();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        robot.leftLiftServo.setPosition(0);
        robot.rightLiftServo.setPosition(1);

        robot.leftFoundationServo.setPosition(0);
        robot.rightFoundationServo.setPosition(0.8);

        waitForStart();

        drive(-0.25, 1);
        strafe2(Direction.LEFT, 0.5, 3);
        drive(-0.25, 1.25);

        robot.leftFoundationServo.setPosition(1);
        robot.rightFoundationServo.setPosition(0);
        sleep(750);
        robot.LFMotor.setPower(0.4);
        robot.RFMotor.setPower(0.25);
        robot.LBMotor.setPower(0.4);
        robot.RBMotor.setPower(0.25);
        sleep(3950);
        robot.leftFoundationServo.setPosition(0);
        robot.rightFoundationServo.setPosition(0.8);
        sleep(500);
        drive(0.25, 0.75);
        sleep(750);
        strafe2(Direction.RIGHT, 0.5, 2);
        drive(-0.25, 0.5);
        strafe2(Direction.RIGHT, 0.5, 2);    }

    private void drive(double pow, double time) {
        double t = time*1000;
        int t1 = (int)t;
        robot.LFMotor.setPower(pow);
        robot.RFMotor.setPower(pow);
        robot.LBMotor.setPower(pow);
        robot.RBMotor.setPower(pow);
        sleep(t1);
        stopMotors();
    }

    private void stopMotors(){
        robot.stop();
    }

    public void strafe2(Direction direction, double power, int t) {
        if(direction == Direction.LEFT){
            robot.LFMotor.setPower(-power);
            robot.RFMotor.setPower(power);
            robot.LBMotor.setPower(power);
            robot.RBMotor.setPower(-power);
        }else if(direction == Direction.RIGHT) {
            robot.LFMotor.setPower(power);
            robot.RFMotor.setPower(-power);
            robot.LBMotor.setPower(-power);
            robot.RBMotor.setPower(power);
        }
        sleep(t*1000);
        stopMotors();
    }
}
