package Team7159.OpModes.Skystones;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.Enums.Direction;

@Autonomous(name = "REDAutonomousPark")
public class REDAutonomousPark extends LinearOpMode {

    private DR4BBotV1 robot = new DR4BBotV1();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        drive(-0.25, 0.1);
        strafe2(Direction.LEFT, 0.35, 5);
        sleep(2000);
    }

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
