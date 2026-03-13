package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TurretSubsystem extends SubsystemBase {
    private final SparkMax flywheelMotor1 = new SparkMax(Constants.TurretConstants.kFlywheelLMotorID, MotorType.kBrushless);
    private final SparkMax flywheelMotor2 = new SparkMax(Constants.TurretConstants.kFlywheelRMotorID, MotorType.kBrushless);
    RelativeEncoder flywheelMotor1Encoder = flywheelMotor1.getEncoder();
    RelativeEncoder flywheelMotor2Encoder = flywheelMotor2.getEncoder();

    private final SparkMax rotationMotor = new SparkMax(Constants.TurretConstants.kTurretRotationMotorID, MotorType.kBrushless);
    private SparkMax

    public TurretSubsystem(){
    }

    public Command startTurretCmd() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
            flywheelMotor1.set(.8);
            //* TO DO: INVERT MOTOR & Brake Mode = Coast */
            flywheelMotor2.set(-0.8);
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

@Override
    public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
}


    public boolean isWindedUp(){
        return (flywheelMotor1Encoder.getVelocity() + flywheelMotor2Encoder.getVelocity())/2 > 3000;
    }
}


