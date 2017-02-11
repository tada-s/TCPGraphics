#ifndef TCPGRAPHICS_H_INCLUDED
#define TCPGRAPHICS_H_INCLUDED

/* Connection */
void setPort(int port);
void setIP(char* ip);

void startConnection();
void closeConnection();

/* Drawing */
void clearCanvas();

void setColor(int r, int g, int b);

void drawLine(double x1, double y1, double x2, double y2);
void drawRect(double x, double y, double w, double h);
void drawOval(double x, double y, double w, double h);
void drawString(char str[], double x, double y);
void fillRect(double x, double y, double w, double h);
void fillOval(double x, double y, double w, double h);

/* Other Options */
void setGridSize(double unit);
void setGridColor(int r, int g, int b);
void setAxisColor(int r, int g, int b);
void setAntialiasing(bool flag);

#endif // TCPGRAPHICS_H_INCLUDED
