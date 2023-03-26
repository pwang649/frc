package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import frc.robot.Constants;

public class Drive {

    private final TalonFX mLeftMain, mLeftFollower, mRightMain, mRightFollower;
    private final Pigeon2 mGyro;

    public Drive() {
        mLeftMain = new TalonFX(Constants.kDriveLeftMainId);
        mLeftMain.setInverted(true);
        mLeftMain.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        mLeftFollower = new TalonFX(Constants.kDriveLeftFollowerId);
        mLeftFollower.setInverted(true);
        mLeftFollower.follow(mLeftMain);

        mRightMain = new TalonFX(Constants.kDriveRightMainId);
        mRightMain.setInverted(false);
        mRightFollower = new TalonFX(Constants.kDriveRightFollowerId);
        mRightFollower.setInverted(false);
        mRightFollower.follow(mRightMain);

        mGyro = new Pigeon2(Constants.kDriveGyro);
    }

    public void setOpenLoop(double throttle, double steering) {
        double overallLeft = 0.0;
        double overallRight = 0.0;

        // Compute overallLeft and overallRight
        overallLeft = throttle + steering;
        overallRight = throttle - steering;

        // Output
        mLeftMain.set(TalonFXControlMode.PercentOutput, overallLeft);
        mRightMain.set(TalonFXControlMode.PercentOutput, overallRight);
    }

    public void setCloseLoopStraight(double distance) {
        double currLeft = mLeftMain.getSelectedSensorPosition();
        double currRight = mRightMain.getSelectedSensorPosition();
        while (currLeft < distance || currRight < distance) {
            currLeft = mLeftMain.getSelectedSensorPosition();
            currRight = mRightMain.getSelectedSensorPosition();

            if (currLeft < distance)
                mLeftMain.set(TalonFXControlMode.PercentOutput, 0.5);
            else 
                mLeftMain.set(TalonFXControlMode.PercentOutput, 0.0);

            if (currRight < distance)
                mRightMain.set(TalonFXControlMode.PercentOutput, 0.5);
            else
                mRightMain.set(TalonFXControlMode.PercentOutput, 0.0);
        }
    }
   
    public void setCloseLoopTurn(double angle) {
        double yaw = mGyro.getYaw();
        while (yaw < angle) {
            mLeftMain.set(TalonFXControlMode.PercentOutput, 0.5);
            mRightMain.set(TalonFXControlMode.PercentOutput, -0.5);
            yaw = mGyro.getYaw();
        }
        mLeftMain.set(TalonFXControlMode.PercentOutput, 0.0);
        mRightMain.set(TalonFXControlMode.PercentOutput, 0.0);
    }
}
