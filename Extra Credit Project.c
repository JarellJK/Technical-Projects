//Jarell Knight
//COP 3223C, Extra Credit Project
//(Calculating the Circumference, area, and volume of a circle using function pointers). Using the techniques you learned in Fig. 7.28, create a text-based, menu-driven program that allows the user to choose whether to calculate the circumference of a circle, the area of a circle or the vol-ume of a sphere. The program should then input a radius from the user, perform the appropriate calculation and display the result. Use an array of function pointers in which each pointer represents a function that returns void and receives a double parameter. The corresponding functions should each display messages indicating which calculation was performed, the value of the radius and the result of the calculation.

#include <stdio.h>
#include <math.h>

//declare prototypes
void circum(double radius);
void area(double radius);
void volume(double radius);
void menu();
const float  PI = 3.141593;
int main()//begin main function
{
     void(*f[3])(double)={circum, area, volume};//initialize array of 3 pointers that each take a double argument and return void
     size_t choice=0;//variable to hold user's choice
     while(choice >=0 && choice < 3)//loop to process user's choice until exit
     {
           menu();//function call
           scanf("%d", &choice);//always store choice until exit of program
        
        if(choice != 3)
        {
            (*f[choice])(choice);//invoke function at location choice in array f and pass choice as an argument
        }//end if
        else
        {
            printf("Exit program.");
        }//end else
     }//end while
  
    return 0;
}//end main function

void menu( void )
{
    printf( "Select an option followed by enter:\n\n"
    "0 Calculate the circumference of a circle\n"
    "1 Calculate the area of a circle\n"
    "2 Calculate the volume of a sphere\n"
    "3 Exit\n");
}//end function menu

void circum(double radius)
{
    printf("\n\nEnter the radius of the circle: ");
    scanf("%lf", &radius);
    printf("\nRadius= %.3f", radius);
    double circ = 2.0 * PI * radius;
    printf("%s%.2f%s\n\n", "\nThe circumference of the circle is ", circ, " meters.");
}//end circum function

void area(double radius)
{
    puts("\n\nEnter the radius of the circle: ");
    scanf("%lf",&radius);
    printf("\nRadius= %.3f", radius);
    double area = PI * pow(radius, 2);
    printf("\n%s%.2f%s\n\n","The area of the circle is ", area, " meters squared.");
}//end area function

void volume(double radius)
{
    puts("\n\nEnter the radius of the sphere: ");
    scanf("%lf",&radius);
    printf("\nRadius= %.3f", radius);
    double volume = (4.0/3.0) * PI * pow(radius ,3);
    printf("%s%.2f%s\n\n", "\nThe volume of the sphere is ", volume,  " cubic meters.");
}//end volume function
