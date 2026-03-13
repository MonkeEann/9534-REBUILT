package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TurretSubsystem extends SubsystemBase {
    private final NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    private final NetworkTable table = ntInstance.getTable("DashboardData");
    private final NetworkTableEntry flywheelRPMEntry = table.getEntry("Flywheel RPM");
    private final NetworkTableEntry flywheelStatusEntry = table.getEntry("Flywheel Status");

    private final SparkMax flywheelMotor1 = new SparkMax(Constants.TurretConstants.kTurretLMotorID, MotorType.kBrushless);
    private final SparkMax flywheelMotor2 = new SparkMax(Constants.TurretConstants.kTurretRMotorID, MotorType.kBrushless);
    
    RelativeEncoder flywheelMotor1Encoder = flywheelMotor1.getEncoder();
    RelativeEncoder flywheelMotor2Encoder = flywheelMotor2.getEncoder();
    
    private final SparkClosedLoopController controller1 = flywheelMotor1.getClosedLoopController();
    private final SparkMaxConfig config1 = new SparkMaxConfig();
    private final SparkMaxConfig config2 = new SparkMaxConfig();



    private final SparkMax rotationMotor = new SparkMax(Constants.TurretConstants.kTurretRotationMotorID, MotorType.kBrushless);

    public TurretSubsystem(){
        config1.closedLoop.pid(.0002, 0, 0);
        config2.follow(flywheelMotor1, true);

        flywheelMotor1.configure(config1, com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        flywheelMotor2.configure(config2, com.revrobotics.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command startTurretCmd() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
            controller1.setSetpoint(Constants.TurretConstants.kTurretSetpoint, ControlType.kVelocity);
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
        double currentRPM = (flywheelMotor1Encoder.getVelocity() + flywheelMotor2Encoder.getVelocity())/2;
        flywheelRPMEntry.setDouble(currentRPM);
        if (isAtSpeed()) {
            flywheelStatusEntry.setString("Ready To Shoot");
        } else {
            flywheelStatusEntry.setString("Not Ready To Shoot");
        }
    // This method will be called once per scheduler run
}

@Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
}
public void stopFlywheel() {
    controller1.setSetpoint(0, ControlType.kVelocity);
};

public boolean isAtSpeed(){
        double currentRPM = (flywheelMotor1Encoder.getVelocity() + flywheelMotor2Encoder.getVelocity())/2;
        return Math.abs(currentRPM - Constants.TurretConstants.kTurretSetpoint) < 100;
}

public double getTurretRotation() {
    return rotationMotor.getEncoder().getPosition();
};
}


