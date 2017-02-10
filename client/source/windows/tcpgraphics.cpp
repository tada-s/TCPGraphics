#include "tcpgraphics.h"

SOCKET tcpGraphicsSocket;

void initConnection(){
    WSADATA wsa;
    struct sockaddr_in server;
    char *message , server_reply[2000];
    int recv_size;

    // Initializing winsock
    if (WSAStartup(MAKEWORD(2,2),&wsa) != 0){
        printf("Socket failed initalizing. Error Code : %d\n", WSAGetLastError());
        exit(1);
    }

    //Create a socket
    if((tcpGraphicsSocket = socket(AF_INET , SOCK_STREAM , 0 )) == INVALID_SOCKET)
    {
        printf("Could not create socket : %d\n" , WSAGetLastError());
        exit(1);
    }

    server.sin_addr.s_addr = inet_addr("127.0.0.1");
    server.sin_family = AF_INET;
    server.sin_port = htons( 6789 );

    //Connect to remote server
    if (connect(tcpGraphicsSocket , (struct sockaddr *)&server , sizeof(server)) < 0)
    {
        printf("Socket could not connect to 127.0.0.1:6789\n");
        exit(1);
    }

    printf("Connected\n");
}

void sendMessage(char message[]){
    if( send(tcpGraphicsSocket , message , strlen(message) , 0) < 0){
        printf("Failed sending the message\n");
    }
}

void closeConnection(){
    closesocket(tcpGraphicsSocket);
    WSACleanup();
    printf("Disconnected\n");
}





void clearCanvas(){
    sendMessage("clear\n");
}

void drawLine(double x1, double y1, double x2, double y2){
    char str[128];
    sprintf(str, "drawLine\n%lf\n%lf\n%lf\n%lf\n", x1, y1, x2, y2);
    sendMessage(str);
}

void drawRect(double x, double y, double w, double h){
    char str[128];
    sprintf(str, "drawRect\n%lf\n%lf\n%lf\n%lf\n", x, y, w, h);
    sendMessage(str);
}

void drawOval(double x, double y, double w, double h){
    char str[128];
    sprintf(str, "drawOval\n%lf\n%lf\n%lf\n%lf\n", x, y, w, h);
    sendMessage(str);
}

void drawString(char str[], double x, double y){
    char str2[128];
    sprintf(str2, "drawString\n%s\n%lf\n%lf\n", str, x, y);
    sendMessage(str2);
}

void fillRect(double x, double y, double w, double h){
    char str[128];
    sprintf(str, "fillRect\n%lf\n%lf\n%lf\n%lf\n", x, y, w, h);
    sendMessage(str);
}

void setColor(int r, int g, int b){
    char str[128];
    sprintf(str, "setColor\n%d\n%d\n%d\n", r, g, b);
    sendMessage(str);
}

void turnAntialiasing(bool flag){
    char str[128];
    if(flag){
        strcpy(str, "setAntialiasing\nTrue\n");
    }else{
        strcpy(str, "setAntialiasing\nFalse\n");
    }
    sendMessage(str);
}

void setGridSize(double unit){
    char str[128];
    sprintf(str, "setGridSize\n%lf\n", unit);
    sendMessage(str);
}
