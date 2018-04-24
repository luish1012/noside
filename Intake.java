package org.usfirst.frc.team653.robot;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Intake system to intake and eject the power up cubes
 */
public class Intake {
	
	private SpeedController intakeL, intakeLB, intakeR, intakeRB;
	
	public Intake(SpeedController intakeL, SpeedController intakeLB, SpeedController intakeR, SpeedController intakeRB){
		this.intakeL = intakeL;
		this.intakeLB = intakeLB;
		this.intakeR = intakeR;
		this.intakeRB = intakeRB;
	
	}
	
	
	public void intakeCube (double speed) {
		intakeL.set(speed);
		intakeLB.set(speed);
		intakeR.set(-speed);
		intakeRB.set(-speed);

	}
	
	public void ejectCube (double speed) {
		intakeL.set(-speed);
		intakeLB.set(-speed);
		intakeR.set(speed);
		intakeRB.set(speed);
	}
	
	public void ejectSlow (double speed) {
		intakeL.set(speed - 0.5);
		intakeLB.set(speed - 0.5);
		intakeR.set(-speed - 0.5);
		intakeRB.set(-speed - 0.5);

	}
	
	public void stopIntake (double speed) {
		intakeL.set(0);
		intakeLB.set(0);
		intakeR.set(0);
		intakeRB.set(0);
	}
	
}

