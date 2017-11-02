#include "I2Cdev.h"
#include "MPU6050_6Axis_MotionApps20.h"
#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
#include "Wire.h"
#endif

#include "eMiuRobot.h"
#include "SerialCommand.h"
#include "MultiThread.h"

#include <EEPROM.h>

#define PWM1		9
#define DIR1		7
#define DIR2		8
#define PWM2		10
#define BALANCING_MODE		0
#define FREE_MODE			1

float PitchAngle;
float RollAngle;
float YawAngle;
float BalanceAngle = 0;

float KP = 11;
float KI = 0;
float KD = 0;
float Offset = 50;
float IError = 0;
float LastError = 0;

int Mode = BALANCING_MODE;

#include "RobotMPUSensor.h"

SerialCommand SerialCMD;

float LeftMotorSpeed = 0;
float RightMotorSpeed = 0;                       
float SpeedOffset = 0;

MultiThread GyroThread = MultiThread();
MultiThread PIDThread = MultiThread();

void setup()
{
	SerialCMD = SerialCommand(&Serial, 38400);
	SerialCMD.AddCommand("ls", &LeftMotorSpeed);
	SerialCMD.AddCommand("rs", &RightMotorSpeed);
	SerialCMD.AddCommand("ss", SetSpeed);
	SerialCMD.AddCommand("kp", &KP);
	SerialCMD.AddCommand("ki", &KI);
	SerialCMD.AddCommand("kd", &KD);
	SerialCMD.AddCommand("os", &Offset);
	SerialCMD.AddCommand("ba", &BalanceAngle);
	SerialCMD.AddCommand("spido", SendPIDO);
	SerialCMD.AddCommand("se", SaveEeprom);

	InitMPU();
	InitEppromReading();

	EMiuRobot.InitMotor(PWM1, PWM2, DIR1, DIR2);
}

void loop()
{
	SerialCMD.Execute();
	SendGyroValue();
	KeepBalance();
}

void InitEppromReading()
{
	uint8_t index = 0;
	EEPROM.get(index, BalanceAngle);
	index += 4;
	EEPROM.get(index, KP);
	index += 4;
	EEPROM.get(index, KI);
	index += 4;
	EEPROM.get(index, KD);
	index += 4;
	EEPROM.get(index, Offset);
}

void SaveEeprom()
{
	uint8_t index = 0;
	EEPROM.put(index, BalanceAngle);
	index += 4;
	EEPROM.put(index, KP);
	index += 4;
	EEPROM.put(index, KI);
	index += 4;
	EEPROM.put(index, KD);
	index += 4;
	EEPROM.put(index, Offset);
}

void SendPIDO()
{
	Serial.println(String("kp") + KP);
	Serial.println(String("ki") + KI);
	Serial.println(String("kd") + KD);
	Serial.println(String("os") + Offset);
	Serial.println(String("ba") + BalanceAngle);
}

void KeepBalance()
{
	//RUN_EVERY(PIDThread, 100)

	if (Mode != BALANCING_MODE)
		return; 

	float error = RollAngle - BalanceAngle;

	// ---- Calculate ouput using PID
	IError += error;
	IError = constrain(IError, -10, 10);
	float dError = error - LastError;
	LastError = error;
	float output = KP * error/* + KI * IError */+ KD * dError;

	output = output * -1;

	if (output > 0)
		output += Offset;
	else if (output < 0)
		output -= Offset;

	if (abs(RollAngle) > 50)
		output = 0;

	LeftMotorSpeed = output;
	RightMotorSpeed = output;
	SetSpeed();

	//Serial.println(String("op-") + output);
}

void SetSpeed()
{
	EMiuRobot.SetLeftMotorSpeed(LeftMotorSpeed + SpeedOffset);
	EMiuRobot.SetRightMotorSpeed(RightMotorSpeed);
}

void SendGyroValue()
{
	ReadGyroValue(&YawAngle, &PitchAngle, &RollAngle);
	RUN_EVERY(GyroThread, 100)
	Serial.println(String("Y") + YawAngle);
	Serial.println(String("P") + PitchAngle);
	Serial.println(String("R") + RollAngle);
}