import sys
import socket
from threading import Thread
import readline

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
HOST = "localhost"
PORT = 5000
s.connect((HOST,PORT))

def read_socket_input():
    data=s.recv(5000)
    while data is not None:
        print(data.decode(encoding="utf-8").rstrip())
        data = s.recv(5000)

def print_to_socket():
    user_input=''
    while user_input is not None:
        user_input=input()
        if user_input == 'Close':
            s.send(str.encode('CLOSE_CONNECTION\n'))
        else:
            s.send(str.encode(user_input + '\n'))

if __name__ == '__main__':
    Thread(target=read_socket_input).start()
    Thread(target=print_to_socket).start()
