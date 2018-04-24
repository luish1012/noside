package org.usfirst.frc.team653.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Activated the cylinders on the intake system
 */
public class Push  {

	DoubleSolenoid pushCylinder;
	
	public Push (DoubleSolenoid pushCylinder) {
		this.pushCylinder = pushCylinder;
	}
	
	public void push() {
		pushCylinder.set(DoubleSolenoid.Value.kForward);
	}
	public void in() {
		pushCylinder.set(DoubleSolenoid.Value.kReverse);
	}
	public void off() {
		pushCylinder.set(DoubleSolenoid.Value.kOff);
	}
  
}

