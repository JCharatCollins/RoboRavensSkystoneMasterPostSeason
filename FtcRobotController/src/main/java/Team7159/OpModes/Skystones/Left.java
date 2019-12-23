package Team7159.OpModes.Skystones;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.Enums.Direction;

import org.firstinspires.ftc.robotcore.external.ClassFactory;

@Autonomous(name = "Left Side Line")
public class Left extends LinearOpMode {

    private DR4BBotV1 robot = new DR4BBotV1();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        try {
            robot.driveDirPower(Direction.FORWARDS, 0.5, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sleep(500);

        try {
            robot.strafe(Direction.RIGHT, 0.5, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sleep(500);
    }
}
