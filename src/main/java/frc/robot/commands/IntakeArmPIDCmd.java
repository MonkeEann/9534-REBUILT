// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.SwerveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class IntakeArmPIDCmd extends Command {
    @SuppressWarnings("PMD.UnusedPrivateField")
    private final IntakeSubsystem IntakeSubsystem;
    private final PIDController pidController;

    public IntakeArmPIDCmd(IntakeSubsystem IntakeSubsystem, double setPoint) {
        this.IntakeSubsystem = IntakeSubsystem;
        this.pidController = new PIDController(3, 0, 0.08);
        pidController.setSetpoint(setPoint);
        addRequirements(IntakeSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        pidController.reset();
    }
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double output = pidController.calculate(IntakeSubsystem.getIntakeArmPosition());
        IntakeSubsystem.setIntakeArm(output);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        IntakeSubsystem.setIntakeArm(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
