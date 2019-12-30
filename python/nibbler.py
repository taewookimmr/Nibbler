import tkinter 
import time
import threading 
import queue 
import random 

RIGHT = 0
LEFT = 1
UP = 2
DOWN = 3


class Nibbler(threading.Thread):

    def __init__(self, size=20, grids=[]):
        threading.Thread.__init__(self)
        self.mapsize = size 
        self.headDir = 0
        self.body = []
        self.speed = 20
        self.runFlag = True
        self.applePosition = -1 # 사과의 위치
        self.appleEatten = False # 사과 먹었는 지 유무

        self.root = None
        self.cvs = None
        self.grids = grids

    
    def run(self):
        self.init_nibbler()
        while self.runFlag:
            if self.applePosition < 0 :
                self.create_apple()
            
            if not self.move():
                break
        
            if self.is_apple_eatten():
                self.add_tail()
                self.applePosition = -1
            
            self.determine_dir()
            time.sleep(1/self.speed)
            
        



    def init_nibbler(self):
        self.headDir = 0
        self.body = [] 
        for i in range(5):
            self.body.append(5-1-i)

        for i in range(len(self.body)):
            r = self.body[i] // self.mapsize
            c = self.body[i] % self.mapsize
            self.draw(r, c)


    def draw(self, row, col, color='black'):
        b= self.grids[row*self.mapsize+col]
        b['bg'] = color


    def add_tail(self):
        prevTail = body[-1]
        r = prevTail//self.mapsize
        c = prevTail %self.mapsize
        dir = [[-1, 0], [0, -1], [1,0], [0,1]]
        for dr, dc in dir:
            nr = r + dr
            nc = c + dc 
            if nr >= 0 and nr < self.mapsize and nc >=0 and nc < self.mapsize:
                p = nr*self.mapsize+nc
                if p not in self.body:
                    self.body.append(p)
                    self.draw(nr, nc)
                    break
        else:
            print("fail to add tail")

 

    def move(self):
        r = self.body[0] // self.mapsize
        c = self.body[0] % self.mapsize

        nr,nc = r,c # 새로운 row, col

        # boundary condition check
        if self.headDir == RIGHT:
            if c+1 < self.mapsize:
                nc+=1
            else :
                return False # 죽는다
        elif self.headDir == LEFT : 
            if c-1 >= 0 :
                nc -=1
            else :
                return False
        elif self.headDir == UP :
            if r-1 >= 0 :
                nr -=1
            else :
                return False
        elif self.headDir == DOWN:
            if r+1 < self.mapsize:
                nr +=1
            else : 
                return False
        
        np = nr * self.mapsize + nc # 일단 boundary condition은 통과 
        
        # self body touch check
        if np not in self.body:
            # 현재 꼬리를 지우고, 새로운 머리에 붙이는 방식으로 이동한다.
            self.grids[self.body[-1]]['bg']='gray'
            self.body.pop()
            self.body.insert(0, np)
            self.grids[self.body[0]]['bg'] ='red'
            self.grids[self.body[1]]['bg'] ='black'
            return True 
        else:
            return False

    
    def is_able_to_go(self, direction):
        pass

    def determine_dir(self):
        pass

    def create_apple(self):
        pass

    def is_apple_eatten(self):
        pass

class Emulator():
    def __init__(self, size=20):
        self.mapsize = size
        self.grids = [] 
   
    def main(self):
        self.root=tkinter.Tk()
        for r in range(self.mapsize):
            for c in range(self.mapsize):
                b = tkinter.Button(self.root, width = 3, height=1)
                b.grid(row=r, column=c)
                b['bg']='gray'
                self.grids.append(b)
        Nibbler(self.mapsize, self.grids).start()
        self.root.mainloop()   

if __name__ == '__main__':
    emulator = Emulator()
    emulator.main()