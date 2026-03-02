package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.controller.PIDController;

public class SwerveModule {
    private final TalonFX driveMotor;
    private final TalonFX turnMotor;

    private final PIDController turnPIDController;

    private final CANcoder absoluteEncoder;
    private final boolean absoluteEncoderReversed;
    private final double absoluteEncoderOffsetRad;    

    private static final double wheelDiameterMeters = 0.1016; // 4 inches in meters
    private static final double driveGearRatio = 6.75;        // example: motor rotations per wheel rotation
    private static final double driveConversionFactor = (Math.PI * wheelDiameterMeters) / driveGearRatio;

    public SwerveModule(int driveMotorID, int turnMotorID, boolean driveMotorReversed, boolean turnMotorReversed, int absoluteEncoderID, double absoluteEncoderOffset, boolean absoluteEncoderReversed) {
        this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
        this.absoluteEncoderReversed = absoluteEncoderReversed;
        absoluteEncoder = new CANcoder(absoluteEncoderID);

        driveMotor = new TalonFX(driveMotorID);
        turnMotor = new TalonFX(turnMotorID);

        TalonFXConfiguration driveConfig = new TalonFXConfiguration();
        driveConfig.MotorOutput.Inverted = 
                    driveMotorReversed 
                        ? InvertedValue.Clockwise_Positive 
                        : InvertedValue.CounterClockwise_Positive;

        driveMotor.getConfigurator().apply(driveConfig);

        TalonFXConfiguration turnConfig = new TalonFXConfiguration();
        turnConfig.MotorOutput.Inverted = 
                    turnMotorReversed 
                        ? InvertedValue.Clockwise_Positive 
                        : InvertedValue.CounterClockwise_Positive;
    
        turnMotor.getConfigurator().apply(turnConfig);

        double driveEncoder = driveMotor.getPosition().getValueAsDouble();
    }

    // Returns wheel distance in meters
    public double getDrivePositionMeters() {
        return driveMotor.getPosition().getValueAsDouble() * driveConversionFactor;
    }

    // Returns wheel speed in meters per second
    public double getDriveVelocityMetersPerSecond() {
        return driveMotor.getVelocity().getValueAsDouble() * driveConversionFactor;
    }
}
