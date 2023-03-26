package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;

public class Flywheel {

    private final TalonFX mMain;

    public Flywheel() {
        mMain = new TalonFX(Constants.kFlywheelMainId);
        mMain.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    }

    public void setOpenLoop(double output) {
        mMain.set(TalonFXControlMode.PercentOutput, output);
    }

    public void setCloseLoop(double setpoint) {

        double currentVelocity = mMain.getSelectedSensorVelocity();

        if (currentVelocity < setpoint)
            setOpenLoop(1);
        else
            setOpenLoop(0);

    }
}
