package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
// Network Tables for Elastic
    private final NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    private final NetworkTable table = ntInstance.getTable("DashboardData");
    private final NetworkTableEntry intakeArmPositionEntry = table.getEntry("Intake Arm Position");

//* DEVICES & CONFIG */    
    private final SparkMax intakeArm = new SparkMax(Constants.IntakeConstants.kIntakeLiftMotorPort, MotorType.kBrushless);
    private final SparkMax intakeRoller = new SparkMax(Constants.IntakeConstants.kIntakeRollersMotorPort, MotorType.kBrushless);
    
    private final RelativeEncoder intakeArmEncoder = intakeArm.getEncoder();
    private final SparkClosedLoopController controller1 = intakeArm.getClosedLoopController();
    private final SparkMaxConfig config1 = new SparkMaxConfig();

    public IntakeSubsystem(){
        config1.closedLoop.pid(0.005, 0.0, 0.0);
        intakeArm.configure(config1, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        intakeArmEncoder.setPosition(0);
    }

    public Command startIntakeRollersCmd(double speed) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
            intakeRoller.set(1);
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
    intakeArmPositionEntry.setDouble(getIntakeArmPosition());
}
    public void setIntakeArm(double targetPosition) {
        controller1.setSetpoint(targetPosition, ControlType.kPosition);
    }
    public void setIntakeRollers(double speed) {
        intakeRoller.set(speed);
    }
    public double getIntakeArmPosition() {
        return intakeArmEncoder.getPosition();
    }

    
}


