#include "eMiuRobot.h"

void EMiuRobotClass::InitMotor(uint8_t pwm1, uint8_t pwm2, uint8_t dir1, uint8_t dir2)
{
	mPWM1 = pwm1;
	mPWM2 = pwm2;
	mDir1 = dir1;
	mDir2 = dir2;

	pinMode(mPWM1, OUTPUT);
	pinMode(mPWM2, OUTPUT);
	pinMode(mDir1, OUTPUT);
	pinMode(mDir2, OUTPUT);

	setDirection(LEFT, FORWARD);
	setDirection(RIGHT, FORWARD);
	SetTwoMotorSpeed(0);
}

void EMiuRobotClass::SetLeftMotorSpeed(int16_t speed)
{
	speed = constrain(speed, -255, 255);

	if (speed >= 0)
	{
		setDirection(LEFT, FORWARD);
	}
	else
	{
		setDirection(LEFT, BACKWARD);
	}

	speed = abs(speed);

	analogWrite(mPWM1, speed);

}
void EMiuRobotClass::SetRightMotorSpeed(int16_t speed)
{
	speed = constrain(speed, -255, 255);

	if (speed >= 0)
	{
		setDirection(RIGHT, FORWARD);
	}
	else
	{
		setDirection(RIGHT, BACKWARD);
	}

	speed = abs(speed);

	analogWrite(mPWM2, speed);

}

void EMiuRobotClass::SetTwoMotorSpeed(int16_t speed)
{
	SetRightMotorSpeed(speed);
	SetLeftMotorSpeed(speed);

}

void EMiuRobotClass::setDirection(Direction motorPosition, uint8_t direction)
{ 
	uint8_t dirPin = mDir2;

	if (motorPosition == LEFT)
	{
		dirPin = mDir1;
	}

	if (direction == FORWARD)
	{
		digitalWrite(dirPin, HIGH);
	}
	else
	{
		digitalWrite(dirPin, LOW);
	}
}

EMiuRobotClass EMiuRobot;

