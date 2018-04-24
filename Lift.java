package org.usfirst.frc.team653.robot;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Raises and lowers the elevator lift
 */
public class Lift {

	private SpeedController liftMech;
	
	public Lift (SpeedController liftMech) {
		this.liftMech = liftMech;
	}
	
	public void lift (double speed) {
		liftMech.set(speed);
	}
	
	public void lower(double speed) {
		liftMech.set(-speed);
	}
	public void hold(double speed) {
		liftMech.set(0);
	}
}

