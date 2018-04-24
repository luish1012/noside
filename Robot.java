package org.usfirst.frc.team653.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;



/**
 * Created for NOSIDE Engineering
 * This class is being used for COMPETITION.
 * Edit carefully.
 */
public class Robot extends IterativeRobot {
//public class Competiton653 extends IterativeRobot {	
	DifferentialDrive myRobot = new DifferentialDrive(new VictorSP(1), new VictorSP(2));
	
	private SpeedController intakeL;
	private SpeedController intakeLB;
	private SpeedController intakeR;
	private SpeedController intakeRB;
	private SpeedController liftMech;
	
	private DoubleSolenoid pushCylinder;
	
	public static Intake intake;
	public static Lift lift;
	public static Push push;
	
	DigitalInput lowerSwitch = new DigitalInput(0);
	DigitalInput upperSwitch = new DigitalInput(1);
	
	Joystick gamepad1 = new Joystick(0);	
	
	//DoubleSolenoid intakeCylinders = new DoubleSolenoid(1,0);
	Compressor compressor = new Compressor();
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();
	
	
	public static final double DRIVE_SPEED = 0.85;
	final double DRIVE_TEST = gamepad1.getRawAxis(3);
	public static final double LIFT_SPEED = 1.0;
	
	public final int left_bumper = 5;
	public final int right_bumper = 6;
	
	
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();		
		
		intake = new Intake(intakeL, intakeLB, intakeR, intakeRB);
		lift = new Lift(liftMech);
		push = new Push(pushCylinder);
		
		intakeL = new Spark(3);
		intakeR = new Spark(4);
		intakeLB = new Spark(5);
		intakeRB = new Spark(6);
		liftMech = new Talon(0);	
		
        System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());
        
   
	}

	
	public void teleopPeriodic() {
		
		//Verify raw axis port and drive speed value 
		myRobot.tankDrive( -1.0 * gamepad1.getY() * DRIVE_SPEED, -1.0 * gamepad1.getRawAxis(5) * DRIVE_SPEED);
		
		intakeR.setInverted(true);
		intakeRB.setInverted(true);
		
		if (gamepad1.getRawButton(1)){
			intakeR.set(-1);
			intakeRB.set(-1);
			intakeL.set(1);
			intakeLB.set(1);
			
			
		}

		else if (gamepad1.getRawButton(2)) {
			
			intakeR.set(1.0);
			intakeRB.set(-1.0);
			intakeL.set(1.0);
			intakeLB.set(-1.0);
			pushCylinder.set(DoubleSolenoid.Value.kForward);
		}
		
		else {
			
			intakeR.set(0.0);
			intakeRB.set(0.0);
			intakeL.set(0.0);
			intakeLB.set(0.0);
			pushCylinder.set(DoubleSolenoid.Value.kReverse);

		}
		
		/*
if upbutton && uplimit
go up

else if downbutton && downlimit
go down

else stop
		 * */
		
		if(gamepad1.getRawButton(3) && !gamepad1.getRawButton(4) && upperSwitch.get()) {  // && !gamepad1.getRawButton(6) && !input01.get()) {
			liftMech.set(LIFT_SPEED);
		}
		
		else if(!gamepad1.getRawButton(3) && gamepad1.getRawButton(4) && lowerSwitch.get()) {
			liftMech.set(-LIFT_SPEED);
		}
		
		else {
			liftMech.set(0.0);
		}	
		

		//**************** Pneumatics below ***************************
		
		compressor.start();
		compressor.setClosedLoopControl(true); //Lets PCM handle the automatic turning on and off of compressor once pressure hits 120 PSI
		
		
		//if (gamepad1.getRawButton(11) && gamepad1.getRawButton(12)) {
			//intakeCylinders.set(DoubleSolenoid.Value.kOff);
		//}
		
		if (gamepad1.getRawButton(9)) {
			//intakeCylinders.set(DoubleSolenoid.Value.kForward);
			push.push();
		}
		else if (gamepad1.getRawButton(10)) {
			//intakeCylinders.set(DoubleSolenoid.Value.kReverse);
			push.in();
		}
		
		startCompetition();
		
	}
	
	double time = 0;
	
	public void autonomousInit() {
		time = Timer.getFPGATimestamp();
		
	}
	
	public void autonomousPeriodic() {
		
					
		if(Timer.getFPGATimestamp() - time < 1) {
			intakeL.set(1.0);
			intakeR.set(1.0);
		}
		
		if(Timer.getFPGATimestamp() - time < 1) {
			myRobot.tankDrive(0.5, 0.5);
			liftMech.set(1.0);
		}
		
		if(Timer.getFPGATimestamp() - time < 1) {
			intakeL.set(-1.0);
			intakeR.set(-1.0);
		}
		else {
			myRobot.tankDrive(0, 0);
		}
	
}
	
	@Override
	public void testInit() {
		CameraServer.getInstance().startAutomaticCapture();
        System.out.println(accel.getX() + ", " + accel.getY() + ", " + accel.getZ());
	}
	
	@Override
	public void testPeriodic() {
		
		//myRobot.tankDrive(gamepad1.getY() * -DRIVE_SPEED, gamepad1.getY() * - DRIVE_SPEED);
		myRobot.arcadeDrive(gamepad1.getY() * -0.8, gamepad1.getZ() * 0.55);
		
		if(gamepad1.getRawButton(1)) {
			intake.intakeCube(1.0);
		}
		else if(gamepad1.getRawButton(2)) {
			intake.ejectCube(1.0);
			push.push();
			}
		else {
			intake.stopIntake(0);
			push.in();
		}
		
		if(gamepad1.getRawButton(9)) {
			intake.ejectSlow(1.0);
		}
		else {
			intake.stopIntake(0.0);
		}
		
		
		//Would be looking for bottom buttons on 3D Extreme Joystick
		if(gamepad1.getRawButton(5) || gamepad1.getRawButton(10)) {
			lift.lift(1.0);
		}
		else if(gamepad1.getRawButton(6) || gamepad1.getRawButton(11)) {
			lift.lower(-1.0);
		}
		else {
			lift.hold(0.0);
		}
		
		
		if(gamepad1.getRawButton(3)) {
			//intakeCylinders.set(DoubleSolenoid.Value.kForward);
			push.push();
		}
		else if(gamepad1.getRawButton(4)) {
			//intakeCylinders.set(DoubleSolenoid.Value.kReverse);
			push.in();
		}
				
	}
}

