#!/usr/bin/python
#coding=utf-8

import RPi.GPIO as GPIO
from step import *
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BOARD)

from flask import Flask, render_template
# from flask_socketio import SocketIO
from flask import request, url_for

import threading

import sys
import requests
import urllib2
import json
import Adafruit_DHT
import time
import datetime
from time import sleep
from time import strftime


firebase_url = 'https://ottoapp-ea5ec.firebaseio.com/'

def get_distance():

    TRIG = 16
    ECHO = 18

    GPIO.setup(TRIG, GPIO.OUT)
    GPIO.setup(ECHO, GPIO.IN)

    GPIO.output(TRIG, False)
    time.sleep(2)
    GPIO.output(TRIG, True)
    time.sleep(0.00001)
    GPIO.output(TRIG, False)

    while GPIO.input(ECHO)==0:
        start = time.time()

    while GPIO.input(ECHO)==1:
        end = time.time()

    return (end - start) * 17150


def upfirebase():
    while True:
        humidity, temperature = Adafruit_DHT.read_retry(Adafruit_DHT.AM2302,4)
        dis = get_distance();
        now=strftime("%Y/%m/%d %H:%M:%S")
        humidity=((humidity*100000000000)//10000000000)/10
        temperature=((temperature*1000000000000)//100000000000)/10
        dis=((dis*10000000000)//1000000000)/10
        data = {'1':{'date':now,'temperature':temperature,'humidity':humidity,'distance':dis}}
        result = requests.put(firebase_url + '/TH.json',json.dumps(data))

        print "Date :" , now , "\nTemperature :" , temperature , " C\nHumidity :" , humidity , " %\nDistance: ", dis, " cm\n"
        sleep(5)


detect = threading.Thread(target = upfirebase)
detect.setDaemon(True)
detect.start()




# from camera_pi import Camera

app = Flask(__name__)
# app.config['SECRET_KEY'] = 'secret!123456'

# socketio = SocketIO(app)

##################------------Otto step-------------###########################

@app.route('/',methods=['GET','POST'])
def index():
        if (request.args.get('control')=='swim'):
            swim()
#            return render_template('index.html')
            return "swim"
        elif (request.args.get('control')=="sitdown"):
            sitdown()
#            return render_template('index.html')
            return "sitdown"
        elif (request.args.get('control')=="WC"):
            WC()
#            return render_template('index.html')
            return "WC"
        elif (request.args.get('control')=="standup"):
            standup()
           # return render_template('index.html')
            return "standup"
        elif (request.args.get('control')=="attention"):
            attention()
           # return render_template('index.html')
            return "attention"
#        else:
#            return render_template('index.html')
        
#            return "no data"

        # return render_template('index.html')


##################------------Camera-------------###########################
##################------------不支援IE-------------#########################

# def gen(camera):
#     """Video streaming generator function."""
#     while True:
#         frame = camera.get_frame()
#         yield (b'--frame\r\n'
#                b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

# @app.route('/video_feed')
# def video_feed():
#     """Video streaming route. Put this in the src attribute of an img tag."""
#     return Response(gen(Camera()),
#                     mimetype='multipart/x-mixed-replace; boundary=frame')

##################------------進入點-------------###########################

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, threaded=True)

# # here you would put all your code for setting up GPIO,
# # we'll cover that tomorrow 
# # initial values of variables etc...
# counter = 0

# try:
#     # here you put your main loop or block of code
#     while counter < 9000000:
#         # count up to 9000000 - takes ~20s
#         counter += 1
#     print "Target reached: %d" % counter

# except KeyboardInterrupt:
#     # here you put any code you want to run before the program
#     # exits when you press CTRL+C
#     print "\n", counter # print value of counter

# except:
#     # this catches ALL other exceptions including errors.
#     # You won't get any error messages for debugging
#     # so only use it once your code is working
#     print "Other error or exception occurred!"

# finally:
#     pwmr1.stop()
#     pwmr2.stop()
#     pwml1.stop()
#     pwml2.stop()
#     GPIO.cleanup() # this ensures a clean exit

