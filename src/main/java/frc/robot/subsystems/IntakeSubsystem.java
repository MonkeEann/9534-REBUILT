package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax intakeArm = new SparkMax(Constants.IntakeConstants.kIntakeLiftMotorPort, MotorType.kBrushless);
    private final SparkMax intakeRoller = new SparkMax(Constants.IntakeConstants.kIntakeRollersMotorPort, MotorType.kBrushless);
    private final RelativeEncoder intakeArmEncoder = intakeArm.getEncoder();

    public IntakeSubsystem(){
    }

    public Command startIntakeRollersCmd(double speed) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
            intakeRoller.set(speed);
        });
}

/**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
    public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
}

@Override
    public void periodic() {
    // This method will be called once per scheduler run
}
    public void setIntakeArm(double speed) {
        intakeArm.set(speed);
    }
    public void setIntakeRollers(double speed) {
        intakeRoller.set(speed);
    }
    public double getIntakeArmPosition() {
        return intakeArmEncoder.getPosition();
    }

}


