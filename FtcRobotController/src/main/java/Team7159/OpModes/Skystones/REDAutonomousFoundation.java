package Team7159.OpModes.Skystones;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Team7159.ComplexRobots.DR4BBotV1;
import Team7159.Enums.Direction;

@Autonomous(name = "REDAutonomousFoundation")
public class REDAutonomousFoundation extends LinearOpMode {

    private DR4BBotV1 robot = new DR4BBotV1();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        try {
            robot.driveDirPower(Direction.FORWARDS, 1, 0.4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            robot.strafe(Direction.RIGHT, 1, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO: Stuff with claw/grabber needs to go here.
        try {
            robot.strafe(Direction.LEFT, 1, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            robot.turn(Direction.RIGHT, 90);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            robot.strafe(Direction.RIGHT, 1, 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sleep(500);
    }
}
