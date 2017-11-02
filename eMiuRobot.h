// eMiuRobot.h

#ifndef _EMIUROBOT_h
#define _EMIUROBOT_h

#if defined(ARDUINO) && ARDUINO >= 100
	#include "arduino.h"
#else
	#include "WProgram.h"
#endif

#include "Servo.h"

enum Direction
{
	FORWARD = 0,
	BACKWARD,
	LEFT,
	RIGHT
};

class EMiuRobotClass
{
 public:
	void InitMotor(uint8_t pwm1, uint8_t pwm2, uint8_t dir1, uint8_t dir2);

	void SetLeftMotorSpeed(int16_t speed);
	void SetRightMotorSpeed(int16_t speed);
	void SetTwoMotorSpeed(int16_t speed);

private:
	uint8_t mPWM1;
	uint8_t mPWM2;
	uint8_t mDir1;
	uint8_t mDir2;

	uint8_t mLeftMotorSpeed;
	uint8_t mRightMotorSpeed;

	void setDirection(Direction motorPosition, uint8_t direction);

};

extern EMiuRobotClass EMiuRobot;

#endif

