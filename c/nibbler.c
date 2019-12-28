#include "nibbler.h"

/*================================== 함수 구현부==================================== */

void gotoxy(int x, int y) {
	COORD Pos = { x - 1 , y - 1 };
	SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), Pos);
}

void gotoxy_draw(int x, int y, char c)
{
	gotoxy(offset_x + 2 * x, offset_y + y + 1);
	printf("%c", c);
	Sleep(30);
}

void gotoxy_draw2(int x, int y, char c, int time){

	gotoxy(offset_x + 2*x, offset_y + y + 2);
	printf("%c", c);
	Sleep(time);
}

int inputCheck(char* what, int min, int max) {
	int input = 0;
	printf("> %s(%d-%d) : ", what, min, max);
	scanf("%d", &input);
	rewind(stdin);
	if ((input < min) || (input > max))     // 허용 범위의 숫자가 아니면 반환
	{
		printf("@ Not Valid Number\n");
		return -1;
	}
	return input;
}

void general(int size) {
	struct Position pos = { 0,0 };
	for (int i = 0; i < size * size; i++) {
		pos.x = i % size;
		pos.y = i / size;
		gotoxy_draw(pos.x, pos.y, '@');
	}
	draw_border();
}

void zigzag(int size) {
	struct Position pos = { 0,0 };
	for (int i = 0; i < size * size; i++) {
		pos.y = i / size;
		if (pos.y % 2 == 0) {pos.x = i % size;}
		else {pos.x = size - 1 - i % size;}
		gotoxy_draw(pos.x, pos.y, '@');
	}
	draw_border();
}

void rightAngle(int size) {
	struct Position pos = { 0,0 };
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < 2 * i + 1; j++) {
			pos.x = j <= i ? i : 2*i - j;
			pos.y = j <= i ? j : i;
			gotoxy_draw(pos.x, pos.y, '@');
		}
	}
	draw_border();
}

void rightAngleZigzag(int size) {
	struct Position pos = { 0,0 };

	for (int i = 0; i < size; i++) {
		for (int j = 0; j < 2 * i + 1; j++) {
			if (i%2 == 0) {
				pos.x = j <= i ? i : 2 * i - j;
				pos.y = j <= i ? j : i;
			}
			else {
				pos.x = j <= i ? j : i;
				pos.y = j <= i ? i : 2 * i - j;
			}
			
			gotoxy_draw(pos.x, pos.y, '@');
		}
	}
	draw_border();
}

void inclined(int size) {
	struct Position pos = { 0,0 };
	for (int i = 0; i < 2 * size - 1; i++) {
		if (i < size) {
			for (int j = 0; j <= i; j++) {
				pos.x = j;
				pos.y = i - j;
				gotoxy_draw(pos.x, pos.y, '@');
			}
		}
		else {
			for (int j = 0; j < 2 * size - i - 1; j++) {
				pos.x = i % size + j + 1;
				pos.y = size - 1 - j;
				gotoxy_draw(pos.x, pos.y, '@');
			}
		}
	}
	draw_border();

}

void inclinedZigzag(int size) {
	struct Position pos = { 0,0 };
	for (int i = 0; i < 2*size-1; i++) {
		if (i < size) {
			for (int j = 0; j <= i; j++) {
				pos.x = i % 2 == 0? j : i - j;
				pos.y = i % 2 == 0? i - j : j ;
				gotoxy_draw(pos.x, pos.y, '@');
			}
		}
		else {
			for (int j = 0; j < 2 * size - i - 1; j++) {
				pos.x = i % 2 == 0 ? i % size + j + 1 : size -1 - j;
				pos.y = i % 2 == 0 ? size - 1 - j : i % size + j + 1;
				gotoxy_draw(pos.x, pos.y, '@');
			}
		}
	}
	draw_border();
}

void snail(int size) {

	int period = size;
	// 달팽이가 시계방향으로 감아 들어감에 따라
	// 방향 전환과 그 다음 방향 전환 사이에 이동해야 할 거리가 점점 짧아진다.
	// 즉, 주기가 짧아진다고 할 수 있다. 그 주기를 나타내는 변수에 해당한다.

	int direction = 0;
	// direction이 가질 수 있는 값 : 0,1,2,3	// 0,1,2,3은 순서대로 right, down, left, up을 뜻함

	int prev = 0; // 직전에 짝힌 인덱스
	int now = 0;  // 바로 찍힐 인덱스

	int *list_index = (int *)malloc(sizeof(int) * size * size); // 인덱스 리스트
	struct Position pos = { 0, 0 };

	for (int i = 0; i < size*size;) {
		for (int j = 0; j < period; j++, i++) {

			prev = i != 0 ? list_index[i - 1] : -1;
			now = prev + step_snail(direction, size);

			// 새로 찍히게 될 인덱스값을 추가한다. 
			list_index[i] = now;

			pos.x = (now) % size;
			pos.y = (now) / size;
			gotoxy_draw(pos.x, pos.y, '@');
		}

		// 다음 period는 이전 direction에 의존적으로 변하는데 다음과 같이 변한다.
		if (direction % 2 == 0) { period--; }
		// 다음 period가 결정이 되었다면 다음 방향으로 틀어준다.
		direction = ++direction % 4;
	}
	draw_border();
}

int step_snail(int direction, int size) {
	switch (direction) {
	case 0: return 1;
	case 1: return size;
	case 2: return -1;
	case 3: return -size;
	default: return 0;
	}
}

void draw_border() {
	offset_y += OFFSET;
	gotoxy(1, offset_y);
	printf("-----------------------------------------\n");
}






void init_nibbler(int size) {
	headDir = 0;
	length_nibbler = 1;

	runFlag = 1;
	myApplePosition = -1;
	appleEatten = 0;


	nibbler = (int *)malloc(sizeof(int) * size * size);
	for (int i = 0; i < size*size; i++) {
		nibbler[i] = -1;
	}

	nibbler[0] = 5;
	nibbler[1] = 4;
	nibbler[2] = 3;
	nibbler[3] = 2;
	nibbler[4] = 1;
	length_nibbler = 5;

	int x = 0;
	int y = 0;

	for (int i = 0; i < length_nibbler; i++) {
		x = nibbler[i] % size;
		y = nibbler[i] / size;
		gotoxy_draw2(x, y, 'O', 0);
	}

	for (int i = -1; i <= size; i++) {
		gotoxy_draw2(-1, i,		'*', 0);
		gotoxy_draw2(size, i,	'*', 0);
		gotoxy_draw2(i, -1,		'*', 0);
		gotoxy_draw2(i, size,	'*', 0);
	}


}

void add_unit() {
	int prevPosition = nibbler[length_nibbler - 1];
	int row = prevPosition / size;
	int rowMin = size * row;
	int rowMax = rowMin + size - 1;

	int presPosition = 0;
	if (prevPosition != rowMin) { presPosition = prevPosition - 1; }
	else { presPosition = rowMax; }

	nibbler[length_nibbler] = presPosition;
	int x = nibbler[length_nibbler] % size;
	int y = nibbler[length_nibbler] / size;
	gotoxy_draw2(x, y, 'O', 0);

	length_nibbler++;
}

void clear_nibbler() {
	int x = 0;
	int y = 0;

	x = nibbler[length_nibbler - 1] % size;
	y = nibbler[length_nibbler - 1] / size;
	gotoxy_draw2(x, y, ' ', 0);

}

void draw_nibbler() {
	int x = 0;
	int y = 0;
	x = nibbler[0] % size;
	y = nibbler[0] / size;
	gotoxy_draw2(x, y, '@', 0);

	x = nibbler[1] % size;
	y = nibbler[1] / size;
	gotoxy_draw2(x, y, 'O', 0);

}

int move_nibbler() {

	int prevHP = nibbler[0];
	int row = prevHP / size;
	int rowMin = size * row;
	int rowMax = rowMin + size - 1;
	int col = prevHP - rowMin;
	int colMin = 0 + col;
	int colMax = size * (size - 1) + col;
	int newHP = 0;

	switch (headDir) {

	case RIGHT:
		if (prevHP >= rowMin && prevHP < rowMax) { newHP = nibbler[0] + 1; }
		else { newHP = rowMin; }
		break;
	case LEFT:
		if (prevHP > rowMin && prevHP <= rowMax) { newHP = nibbler[0] - 1; }
		else { newHP = rowMax; }
		break;
	case UP:
		if (prevHP > colMin && prevHP <= colMax) { newHP = nibbler[0] - size; }
		else { newHP = colMax; }
		break;
	case DOWN:
		if (prevHP >= colMin && prevHP < colMax) { newHP = nibbler[0] + size; }
		else { newHP = colMin; }
		break;
	}

	int delta = newHP - prevHP;
	int moveYourHead = 1;

	if (delta == 1 || delta == -1 || delta == size || delta == -size) { moveYourHead = 1; }
	else { moveYourHead = 0; }

	if (moveYourHead) {

		clear_nibbler();

		nibbler[0] = newHP;
		int beforeMePos = prevHP;
		for (int i = 1; i < length_nibbler; i++) {
			int mePos = nibbler[i];
			nibbler[i] = beforeMePos;
			beforeMePos = mePos;
		}
		draw_nibbler();

		if (touchMyBody()) { moveYourHead = 0; }
	}
	else {
		moveYourHead = 0;
	}

	draw_nibbler();

	return moveYourHead;
}

int isAbleToGoTo(int direction) {

	struct Position hp = { 0,0 };
	hp.x = nibbler[0] % size;
	hp.y = nibbler[0] / size;

	struct Position np = { 0,0 };

	switch (direction) {
		case RIGHT: np.x = hp.x + 1; np.y = hp.y; break;
		case LEFT:	np.x = hp.x - 1; np.y = hp.y; break;
		case UP:	np.x = hp.x ;    np.y = hp.y-1; break;
		case DOWN:	np.x = hp.x ;    np.y = hp.y+1; break;
	}

	int npi = np.y * size + np.x;

	for (int i = 1; i < length_nibbler; i++) {
		if (npi == nibbler[i]) {
		
			return 0;
		}
	}

	if (np.x < 0 || np.x >= size || np.y < 0 || np.y >=size){ 
		return 0;
	}
	
	return 1;
}

void determine_dir() {

	struct Position here = { 0,0 };
	here.x = nibbler[0] % size;
	here.y = nibbler[0] / size;

	struct Position there = { 0,0 };
	there.x = myApplePosition % size;
	there.y = myApplePosition / size;

	struct Position delta = { 0,0 };
	delta.x = there.x - here.x;
	delta.y = there.y - here.y;
	
	int r=0, l=0, u=0, d=0;

	switch (headDir){
		case RIGHT:
			 r = isAbleToGoTo(RIGHT);
			 u = isAbleToGoTo(UP);
			 d = isAbleToGoTo(DOWN);
			 break;
		case LEFT:
			l = isAbleToGoTo(LEFT);
			u = isAbleToGoTo(UP);
			d = isAbleToGoTo(DOWN);
			break;
		case UP:
			r = isAbleToGoTo(RIGHT);
			l = isAbleToGoTo(LEFT);
			u = isAbleToGoTo(UP);
			break;
		case DOWN:
			r = isAbleToGoTo(RIGHT);
			l = isAbleToGoTo(LEFT);
			d = isAbleToGoTo(DOWN);
			break;
	}

	int x = delta.x >= 0 ? delta.x : -1;
	int y = delta.y >= 0 ? delta.y : -1;
	
	if (x == 0) {
		if (y < 0 && u) {
			headDir = UP; return;
		}
		if (y > 0 && d) {
			headDir = DOWN; return;
		}
		if (r) {
			headDir = RIGHT; return;
		}
		if (l) {
			headDir = LEFT; return;
		}
	}

	if (y == 0) {

		if (x>0 && r) {
			headDir = RIGHT; return;
		}
		if (x<0 && l) {
			headDir = LEFT; return;
		}
		if (u) {
			headDir = UP; return;
		}
		if (d) {
			headDir = DOWN; return;
		}
		
	}

	if (x > 0) {
		if (r) {
			headDir = RIGHT; return;
		}
	
		if (u && y < 0) {
			headDir = UP; return;
		}
		if (d && y > 0) {
			headDir = DOWN; return;
		}

		if (l) {
			headDir = LEFT; return;
		}
	}

	if (x < 0) {

		if (l) {
			headDir = LEFT; return;
		}


		if (u && y < 0) {
			headDir = UP; return;
		}
	
		if (d && y > 0) {
			headDir = DOWN; return;
		}

	
		if (r) {
			headDir = RIGHT; return;
		}

	
	}

	if (y > 0) {

		if (d) {
			headDir = DOWN; return;
		}

		if (r && x>0) {
			headDir = RIGHT; return;
		}
		if (l && y<0) {
			headDir = LEFT; return;
		}

		if (u) {
			headDir = UP; return;
		}

	
	}

	if (y < 0) {

		if (u) {
			headDir = UP; return;
		}
	
		if (r && x>0) {
			headDir = RIGHT; return;
		}
		if (l && x<0) {
			headDir = LEFT; return;
		}

		if (d) {
			headDir = DOWN; return;
		}
	
	}

	headDir = -1; return;
}

int touchMyBody() {

	int  result = 0;
	int hp = nibbler[0];

	for (int i = 1; i < length_nibbler; i++) {
		if (hp == nibbler[i]) {
			result = 1;
			break;
		}
	}

	return result;
}

void createApple() {

	int applePosition = 0;
	int flag = 1;
	srand((unsigned)time(NULL));
	while (flag) {
		applePosition =  101*rand() % (size*size);

		for (int i = 0; i < length_nibbler; i++) {
			if (applePosition == nibbler[i]) { break; }
			else { flag = 0; }
		}
	}
	myApplePosition = applePosition;
	int x = myApplePosition % size;
	int y = myApplePosition / size;
	gotoxy_draw2(x, y, '$', 0);
}

int isAppleEaten() {
	int result = 0;
	for (int i = 0; i < length_nibbler; i++) {
		if (nibbler[i] == myApplePosition) { result = 1; break; }
		else { result = 0; }
	}
	return result;
}

void doNibbler(int size) {

	init_nibbler(size);

	while (runFlag) {

		if (myApplePosition < 0) {
			createApple();
		}

		if (!move_nibbler()) {
			break;
		}

		if (isAppleEaten()) {
			add_unit();
			myApplePosition = -1;
		}

		determine_dir();
		Sleep(900/speed);
	}

	draw_border();
	// printf("%d칸 중에서 %d칸이 채워졌습니다.\n", size*size, length_nibbler);
	// printf("손 볼게 많은 길 찾기 알고리즘입니다. ^0^;\n");
	length_nibbler = 0;// 초기화
	printf("-----------------------------------------\n");

}

void main_of_project() {

	while (1) {

		// 방식 선택 및 사이즈 초기화
		method = -1;
		size = -1;

		// 목록 출력
		printf("Program showing the order of filling N*N matrix\n");
		printf("===================List==================\n\n");
		for (int i = 0; i < sizeof(actionList) / sizeof(actionList[0]); i++) {
			printf("#%d : %s\n", i + 1, actionList[i]);
		}
		printf("\n");


		// 방식 질의
		while (method == -1) {
			method = inputCheck("Select Method", 1, 8);
		}

		// 행렬 사이즈 질의
		while (size == -1) {
			size = inputCheck("N for NxN  Matrix", 5, 20);
		}

		switch (method) {
		case 1: fp = general; break;
		case 2: fp = zigzag; break;
		case 3: fp = rightAngle; break;
		case 4: fp = rightAngleZigzag; break;
		case 5: fp = inclined; break;
		case 6: fp = inclinedZigzag; break;
		case 7: fp = snail; break;
		case 8: fp = doNibbler; break;
		default: break; // 여기까지 올 일은 없다.
		}
		fp(size);
	}
}


void main(){
    main_of_project();
    return ;
}