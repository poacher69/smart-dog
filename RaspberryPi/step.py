#!/usr/bin/python
#coding=utf-8


import time
import Adafruit_PCA9685

# Initialise the PCA9685 using the default address (0x40).
pwm = Adafruit_PCA9685.PCA9685()

# Alternatively specify a different address and/or bus:
#pwm = Adafruit_PCA9685.PCA9685(address=0x41, busnum=2)

# Configure min and max servo pulse lengths
servo_min = 150  # Min pulse length out of 4096
servo_max = 600  # Max pulse length out of 4096
servo_mid = 350

# Helper function to make setting a servo pulse width simpler.
def set_servo_pulse(channel, pulse):
    pulse_length = 1000000    # 1,000,000 us per second
    pulse_length //= 60       # 60 Hz
    print('{0}us per period'.format(pulse_length))
    pulse_length //= 4096     # 12 bits of resolution
    print('{0}us per bit'.format(pulse_length))
    pulse *= 1000
    pulse //= pulse_length
    pwm.set_pwm(channel, 0, pulse)

# Set frequency to 60hz, good for servos.
pwm.set_pwm_freq(60)

def attention():

	pwm.set_pwm(0,0,250)
	pwm.set_pwm(1,0,450)
	pwm.set_pwm(2,0,250)
	pwm.set_pwm(3,0,450)
	time.sleep(0.2)
	pwm.set_pwm(4,0,150)
	time.sleep(0.2)
	pwm.set_pwm(4,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(5,0,550)
	time.sleep(0.2)
	pwm.set_pwm(5,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(6,0,150)
	time.sleep(0.2)
	pwm.set_pwm(6,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(7,0,500)
	time.sleep(0.2)
	pwm.set_pwm(7,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(0,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(1,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(2,0,servo_mid)
	time.sleep(0.2)
	pwm.set_pwm(3,0,servo_mid)
	time.sleep(0.2)



def swim():
	pwm.set_pwm(6, 0, 250)
	pwm.set_pwm(7, 0, 450)
	time.sleep(0.2)
	pwm.set_pwm(4, 0, 250)
	pwm.set_pwm(5, 0, 450)
	pwm.set_pwm(2, 0, 550)
	pwm.set_pwm(3, 0, 150)
	time.sleep(0.5)
	pwm.set_pwm(6, 0, 550)
	pwm.set_pwm(7, 0, 150)
	time.sleep(0.3)
	pwm.set_pwm(2, 0, 250)
	pwm.set_pwm(3, 0, 450)
	pwm.set_pwm(4, 0, 450)
	pwm.set_pwm(5, 0, 250)
	pwm.set_pwm(0, 0, 250)
	pwm.set_pwm(1, 0, 450)
	standup()

def sitdown():
	pwm.set_pwm(6, 0, 550)
	pwm.set_pwm(7, 0, 150)
	time.sleep(0.5)
	pwm.set_pwm(2, 0, 150)
	pwm.set_pwm(3, 0, 550)
	time.sleep(2)	

def standup():
	pwm.set_pwm(2, 0, 350)
	pwm.set_pwm(3, 0, 350)
	time.sleep(0.5)
	pwm.set_pwm(6, 0, 350)
	pwm.set_pwm(7, 0, 350)
	time.sleep(0.5)
	pwm.set_pwm(4, 0, 500)
	pwm.set_pwm(5, 0, 200)
	time.sleep(1)

def WC():

	pwm.set_pwm(4, 0, 550)
	pwm.set_pwm(5, 0, 150)
	time.sleep(2)
	pwm.set_pwm(1, 0, 150)
	pwm.set_pwm(0, 0, 550)
	pwm.set_pwm(3, 0, 150)
	pwm.set_pwm(2, 0, 550)
	time.sleep(2)
	pwm.set_pwm(1, 0, 350)
	pwm.set_pwm(0, 0, 350)
	pwm.set_pwm(3, 0, 350)
	pwm.set_pwm(2, 0, 350)
	time.sleep(1)

	pwm.set_pwm(4, 0, 350)
	pwm.set_pwm(5, 0, 350)
	time.sleep(0.5)

	pwm.set_pwm(2, 0, 550)
	pwm.set_pwm(6, 0, 150)
	time.sleep(0.2)
	pwm.set_pwm(2, 0, 150)
	pwm.set_pwm(6, 0, 550)
	time.sleep(0.2)
	pwm.set_pwm(2, 0, 550)
	pwm.set_pwm(6, 0, 150)
	time.sleep(0.2)
	pwm.set_pwm(2, 0, 150)
	pwm.set_pwm(6, 0, 550)

	time.sleep(0.2)
	pwm.set_pwm(2, 0, 500)
	pwm.set_pwm(6, 0, 200)
	time.sleep(0.2)
	pwm.set_pwm(2, 0, 150)
	pwm.set_pwm(6, 0, 550)
	time.sleep(0.2)
	pwm.set_pwm(2, 0, 500)
	pwm.set_pwm(6, 0, 200)
	time.sleep(0.2)
	pwm.set_pwm(2, 0, 350)
	pwm.set_pwm(6, 0, 350)
	time.sleep(0.2)
	standup()

def holdhand():
	pwm.set_pwm(4, 0, 600)
	time.sleep(1)
	pwm.set_pwm(0, 0, 150)
	time.sleep(3)	

