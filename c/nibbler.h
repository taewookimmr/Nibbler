#pragma once
#define _CRT_SECURE_NO_WARNINGS


#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <Windows.h>

/*======================== 매크로, 전역 변수, 구조체, 함수 포인터 선언========================== */

#define OFFSET 23 // 콘솔 상에서 이전 질의와 다음 질의를 구분하는 선을 그리는데 사용되는 숫자
#define RIGHT 0
#define LEFT  1
#define UP	  2
#define DOWN  3


// 좌표를 나타내는 구조체
struct Position {
	int x;
	int y;
};

char *actionList[8] = { "General", "Zigzag",  "90", "90Zigzag", "Inclined", "InclinedZigzag", "Snail", "Nibbler" }; // 채우기 방식 문자열
int method = -1;   // 방식 선택
int size = -1;	   // 정사각형 행렬 크기 
int offset_x = 50; // 그려지는 시작 위치를 정하기 위한 오프셋
int offset_y = 0;  // 그려지는 시작 위치를 정하기 위한 오프셋


////////////////////////Nibbler 관련 변수/////////////////////////
int *nibbler; // 지렁이를 구성하는 각 칸의 인덱스들을 저장할 수 있도록, 동적 할당하는데 사용되는 포인터
int headDir = 0; // 지렁이의 머리 위치를 나타내는 변수
int length_nibbler = 1; // 지렁이의 길이(머리 포함)
int speed = 20; // 지렁이의 진행 속도 
int runFlag = 1; // if문에 쓰임
int myApplePosition = -1; // 사과의 위치를 나타내는데 사용되는 변수
int appleEatten = 0; // 지렁이가 사과를 먹었는지 나타내는 변수


// 채울 행렬 크기, 채움 방식을 파라미터로 받는 함수를 가리키는 포인터
void(*fp) (int size);


/*================================== 함수 프로토타입==================================== */

// 콘솔위의 특정 좌표로 커서를 옮기는 함수
void gotoxy(int x, int y);

// 콘솔위의 특정 좌표로 가서 매개변수로 전달한 char를 찍는 함수
void gotoxy_draw(int x, int y, char c);
void gotoxy_draw2(int x, int y, char c, int time);


// 입력의 유효함을 확인하는 함수
int inputCheck(char* what, int min, int max);

// 행렬 채우기를 실제로 실행하는 함수
void general(int size);
void zigzag(int size);
void rightAngle(int size);
void rightAngleZigzag(int size);
void inclined(int size);
void inclinedZigzag(int size);
void snail(int size);

// snail 함수 내부에서 주기를 계산하는데 사용되는 함수
int step_snail(int direction, int size);

// 다음 질의를 구분하기 위해서 구분선 그려주는 함수
void draw_border();

// Nibbler 관련 함수 구현
void init_nibbler(int size); // 지렁이를 초기화
void add_unit(); // 지렁이가 사과를 먹었을 때, 지렁이 길이를 실질적으로 증가시켜주는 부분
void  clear_nibbler(); // 지렁이가 움직이는 모습을 구현하기 위해서는 매 순간마다 꼬리만 지워주고,
void  draw_nibbler();  // 진행 뱡항으로 머리만 하나 추가적으로 그려주면 된다. 즉 모든 몸체를 매번 새로 그릴 필요가 없다
int  move_nibbler(); // 지렁이의 움직임을 실질적으로 표현하는 함수
int  isAbleToGoTo(int direction); // 지렁이의 진행 방향을 결정하는 함수
void  determine_dir(); // 지렁이의 진행 방향을 결정하는 함수
int  touchMyBody(); // 머리가 자기 몸에 부딪히면 끝나는 게임 규칙을 적용하기 위한 판별식
void createApple(); // 사과를 생성하는 함수
int isAppleEaten(); // 지렁이가 사과를 먹었는지 판별하는 함수
void doNibbler(int size); // 일종의 지렁이 메인 함수


// 일종의 main함수
void main_of_project();



