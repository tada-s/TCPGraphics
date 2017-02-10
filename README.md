# TCP Graphics

A simple drawing tool for C/C++.

### Instructions:
* Download the TCP Graphics server.
* Download the TCP Client source code and copy to the C/C++ project or working directory
* Write a code (see sample code)
* Open TCP Graphics server
* Compile and Run the C/C++ code
* :D

## It may require
To run the jar binary, it may require JRE 1.7 or compile with an appropiate JDK.

### Sample code:

```c
#include "tcpgraphics.h"
int main(int argc , char *argv[])
{
    displayMessage(false);
    initConnection();

    setAntialiasing(true);
    setGridSize(1);

    clearCanvas();
    setColor(0, 0, 0);
    drawLine(1, 2, 5, 6);
    setColor(0, 0, 255);
    drawRect(-1, -1, 3, 3);
    drawOval(5, 5, 10, 10);
    drawString("I'm a text :D", 0, -5);

    closeConnection();
    return 0;
}
```

