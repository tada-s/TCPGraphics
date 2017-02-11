#include "tcpgraphics.h"
#include <string.h>

/**************/
/* Connection */
/**************/

int TCPG_PORT = 6789;
char TCPG_IP[16] = "127.0.0.1";

/* Change the default port */
void setPort(int port){
    TCPG_PORT = port;
}

/* Change the default IP */
void setIP(char* ip){
    strcpy(TCPG_IP, ip);
}

#ifdef __WINDOWS__
    #include <stdio.h>
    #include <stdlib.h>
    #include <winsock2.h>

    SOCKET TCPG_SOCKET;

    void startConnection(){
        WSADATA wsa;
        struct sockaddr_in server;

        /* Initializing winsock */
        if (WSAStartup(MAKEWORD(2,2),&wsa) != 0){
            printf("Socket failed initalizing. Error Code : %d\n", WSAGetLastError());
            exit(-1);
        }

        /* Create a socket */
        if((TCPG_SOCKET = socket(AF_INET , SOCK_STREAM , 0 )) == INVALID_SOCKET)
        {
            printf("Could not create socket : %d\n" , WSAGetLastError());
            exit(-1);
        }

        server.sin_addr.s_addr = inet_addr(TCPG_IP);
        server.sin_family = AF_INET;
        server.sin_port = htons(TCPG_PORT);

        /* Connect to remote server */
        if (connect(TCPG_SOCKET , (struct sockaddr *)&server , sizeof(server)) < 0)
        {
            printf("Socket could not connect to %s:%d\n", TCPG_IP, TCPG_PORT);
            exit(-1);
        }

        printf("Connected\n");
    }

    void sendMessage(char message[]){
        if( send(TCPG_SOCKET , message , strlen(message) , 0) < 0){
            printf("Failed sending the message\n");
        }
    }

    void closeConnection(){
        closesocket(TCPG_SOCKET);
        WSACleanup();
        printf("Disconnected\n");
    }



#elif __linux__
    #include <stdio.h>
    #include <stdlib.h>
    #include <unistd.h>
    #include <arpa/inet.h>

    int TCPG_FDSOCKET;

    void startConnection(){
        struct hostent *he;
        struct sockaddr_in server;

        /* Create a socket */
        if ((TCPG_FDSOCKET=socket(AF_INET, SOCK_STREAM, 0))==-1){
            printf("Could not create socket\n");
            exit(-1);
        }

        server.sin_family = AF_INET;
        server.sin_port = htons(TCPG_PORT); 
        server.sin_addr.s_addr = inet_addr(TCPG_IP);
        bzero(&(server.sin_zero),8);

        /* Connect to remote server */
        if(connect(TCPG_FDSOCKET, (struct sockaddr *)&server,
            sizeof(struct sockaddr))==-1){ 
            printf("Socket could not connect to %s:%d\n", TCPG_IP, TCPG_PORT);
            exit(-1);
        }

        printf("Connected\n");

    }

    void sendMessage(char message[]){
        if(send(TCPG_FDSOCKET, message, strlen(message), 0) < 0){
            printf("Failed sending the message\n");
        }
    }

    void closeConnection(){
        close(TCPG_FDSOCKET);
        printf("Disconnected\n");
    }
    
    
#endif

/************/
/* Messages */
/************/

void clearCanvas(){
    char str[128] = "clear\n";
    sendMessage(str);
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

void fillOval(double x, double y, double w, double h){
    char str[128];
    sprintf(str, "fillOval\n%lf\n%lf\n%lf\n%lf\n", x, y, w, h);
    sendMessage(str);
}

void setColor(int r, int g, int b){
    char str[128];
    sprintf(str, "setColor\n%d\n%d\n%d\n", r, g, b);
    sendMessage(str);
}

void setGridSize(double unit){
    char str[128];
    sprintf(str, "setGridSize\n%lf\n", unit);
    sendMessage(str);
}

void setGridColor(int r, int g, int b){
    char str[128];
    sprintf(str, "setGridColor\n%d\n%d\n%d\n", r, g, b);
    sendMessage(str);
}

void setAxisColor(int r, int g, int b){
    char str[128];
    sprintf(str, "setAxisColor\n%d\n%d\n%d\n", r, g, b);
    sendMessage(str);
}

void setAntialiasing(bool flag){
    char str[128];
    sprintf(str, "setAntialiasing\n%s\n", flag ? "True" : "False");
    sendMessage(str);
}


