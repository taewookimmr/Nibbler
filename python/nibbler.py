import tkinter 
import time
import threading 
import queue 
import random 

KEEP = -1
RIGHT = 0
LEFT = 1
UP = 2
DOWN = 3

dirList = [RIGHT, LEFT, UP, DOWN]

dir = {}
dir[RIGHT] = [0,1]
dir[LEFT]  = [0,-1]
dir[UP]    = [-1,0]
dir[DOWN]  = [1,0]

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
        prevTail = self.body[-1]
        r = prevTail//self.mapsize
        c = prevTail %self.mapsize
        for dr, dc in dir.values():
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
        cango, nr, nc = self.is_able_to_go()
        if cango:
            np = nr*self.mapsize+nc
            # 현재 꼬리를 지우고, 새로운 머리에 붙이는 방식으로 이동한다.
            self.grids[self.body[-1]]['bg']='gray'
            self.body.pop()
            self.body.insert(0, np)
            self.grids[self.body[0]]['bg'] ='blue'
            self.grids[self.body[1]]['bg'] ='black'
            return True 
        else:
            for p in self.body:
                self.grids[p]['bg']='orange'
            return False

    def is_able_to_go(self, newDir=KEEP):
        r = self.body[0] // self.mapsize
        c = self.body[0] % self.mapsize
        nr,nc = r,c # 새로운 row, col
        if newDir == KEEP:
            # boundary condition check
            if self.headDir == RIGHT:
                if c+1 < self.mapsize:
                    nc+=1
                else :
                    return False , -1 , -1
            elif self.headDir == LEFT : 
                if c-1 >= 0 :
                    nc -=1
                else :
                    return False, -1 , -1
            elif self.headDir == UP :
                if r-1 >= 0 :
                    nr -=1
                else :
                    return False, -1 , -1
            elif self.headDir == DOWN:
                if r+1 < self.mapsize:
                    nr +=1
                else : 
                    return False, -1 , -1
            
            np = nr * self.mapsize + nc # 일단 boundary condition은 통과 

            if np not in self.body:
                return True, nr, nc
            else:
                return False, -1, -1

        else :
            dr,dc = dir[newDir]
            nr, nc = r+dr, c+dc
            if newDir == RIGHT and nc >= self.mapsize:
                return False, -1,-1
            elif newDir == LEFT and nc < 0:
                return False, -1, -1
            elif newDir == UP and nr < 0:
                return False, -1, -1
            elif newDir == DOWN and nr >= self.mapsize:
                return False, -1, -1
        
            np = nr*self.mapsize+nc
            if np not in self.body:
                return True, nr, nc
            else:
                return False, -1, -1

    def determine_dir(self):
        hr = self.body[0] // self.mapsize
        hc = self.body[0] % self.mapsize

        ar = self.applePosition // self.mapsize
        ac = self.applePosition % self.mapsize

        dr = ar - hr
        dc = ac - hc
        
        right,left,up,down = [False]*4
        for nextdir in dirList:
            if self.headDir == nextdir:
                 right = self.is_able_to_go(RIGHT)
                 left  = self.is_able_to_go(LEFT)
                 up    = self.is_able_to_go(UP)
                 down  = self.is_able_to_go(DOWN)
        
        r = dr if dr >=0 else -1
        c = dc if dc >=0 else -1

        if c == 0 :
            if (r < 0 and up) :
                self.headDir = UP 
                return
            
            if r > 0 and down :
                self.headDir = DOWN
                return

            if right:
                self.headDir = RIGHT
                return
            
            if left :
                headDir = LEFT
                return

        if r == 0:

            if c>0 and right :
                self.headDir = RIGHT
                return
            
            if c<0 and left :
                self.headDir = LEFT
                return
            
            if up:
                self.headDir = UP
                return

            if down:
                selfheadDir = DOWN
                return
            
            
        if c > 0:
            if right :
                self.headDir = RIGHT
                return 
        
            if up and r < 0 : 
                self.headDir = UP
                return
            
            if down and r > 0:
                self.headDir = DOWN
                return
            

            if left:
                self.headDir = LEFT
                return
        

        if c < 0:

            if left :
                self.headDir = LEFT
                return

            if up and r < 0:
                selfheadDir = UP
                return
        
        
            if down and r > 0:
                self.headDir = DOWN
                return
        

        
            if right: 
                headDir = RIGHT
                return

        if r > 0 :

            if down:
                headDir = DOWN
                return


            if right and c>0:
                self.headDir = RIGHT
                return
            
            if left and r<0:
                self.headDir = LEFT
                return
            

            if up:
                self.headDir = UP
                return

        if r < 0 :

            if up:
                self.headDir = UP
                return

            if right and c>0:
                self.headDir = RIGHT
                return
            
            if left and c<0:
                self.headDir = LEFT
                return

            if down:
                self.headDir = DOWN
                return

        self.headDir = -1
        self.runFlag = False
        return 


        

    def create_apple(self):
        while True:
            p = random.randint(0, self.mapsize*self.mapsize-1)
            if p not in self.body:
                self.applePosition=p
                self.grids[p]['bg'] = 'red'
                self.appleEatten=False
                break

    def is_apple_eatten(self):
        for p in self.body:
            if p == self.applePosition:
                self.applePosition = -1
                return True
        return False

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