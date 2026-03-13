package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;

public class StorageSubsystem extends SubsystemBase {
    private final SparkMax storageRollers = new SparkMax(Constants.StorageConstants.kStorageRollersMotorPort, MotorType.kBrushless);

    public StorageSubsystem(){

    }

    public Command startStorageRollersCmd(double speed) {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
            setStorageRollers(speed);
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
    public void setStorageRollers(double speed) {
        storageRollers.set(speed);
    }


    
}


