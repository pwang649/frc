// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */

  private Drive mDrive = new Drive();
  private Flywheel mFlywheel = new Flywheel();
  private Joystick mDriveStick = new Joystick(Constants.kDriveStickId);
  private Joystick mOperatorStick = new Joystick(Constants.kOperatorStickId);

  private boolean runOnce = true;

  @Override
  public void robotInit() {
    mDrive.setOpenLoop(0, 0);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    if (runOnce) {
      // 1. Drive straight
      mDrive.setCloseLoopStraight(10);

      // 2. Turn right
      mDrive.setCloseLoopTurn(90);

      runOnce = false;
    } else {
      // 3. Shoot
      mFlywheel.setCloseLoop(1000);
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    double throttle = -mDriveStick.getRawAxis(1);
    double turn = mDriveStick.getRawAxis(4);
    mDrive.setOpenLoop(throttle, turn);

    if (mOperatorStick.getRawButton(1))
      mFlywheel.setOpenLoop(1);
    else if (mOperatorStick.getRawButton(2))
      mFlywheel.setOpenLoop(0.5);
    else if (mOperatorStick.getRawButton(3))
      mFlywheel.setCloseLoop(1000);
    else if (mOperatorStick.getRawButton(4))
      mFlywheel.setCloseLoop(500);
    else
      mFlywheel.setOpenLoop(0);
  }

  @Override
  public void disabledInit() {
    mDrive.setOpenLoop(0, 0);
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
