from firebase import firebase
import serial
pre=''
firebase = firebase.FirebaseApplication('https://jyoti-1836c.firebaseio.com/', None)
while(1):
    result = firebase.get('/speech', None)
    print(result)
    if(pre != result):
        ser = serial.Serial('/dev/ttyACM0', 9600)
        s=result
        pre=result
        print("hi")
        a=list(s)
        for l in a:
            v=(l.encode('ASCII'))
            ser.write(v)
#ser.write(b'h')

