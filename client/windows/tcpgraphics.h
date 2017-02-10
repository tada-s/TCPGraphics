#ifndef TCPGRAPHICS_H_INCLUDED
#define TCPGRAPHICS_H_INCLUDED

#include<stdio.h>
#include<winsock2.h>

#pragma comment(lib,"ws2_32.lib") //Winsock Library

/* Connection */
void initConnection();
void sendMessage(char message[]);
void closeConnection();

/* Drawing */
void clearCanvas();

void setColor(int r, int g, int b);

void drawLine(double x1, double y1, double x2, double y2);
void drawRect(double x, double y, double w, double h);
void drawOval(double x, double y, double w, double h);
void drawString(char str[], double x, double y);
void fillRect(double x, double y, double w, double h);

/* Other Options */
void turnAntialiasing(bool flag);
void setGridSize(double unit);

#endif // TCPGRAPHICS_H_INCLUDED
