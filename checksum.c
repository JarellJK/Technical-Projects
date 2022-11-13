//Jarell Knight
//CIS 3360, Sec 001
//HW #2, Checksum

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

uint8_t checksum_8(const unsigned char *message, size_t length)
{
	unsigned int checksum;
	
	for(checksum = 0; length != 0; length--)
	{
		checksum += *(message++);
	}
	
	return (uint8_t)sum;
}
	
	//parse string, get hex value of each
	//add next character, if sum is > 2^n, subtract 2^n
	//use 2s complement
	//if char > 9, subtract 55


int main(int argc, char **argv)
{
  char *fname = argv[ 1 ];
  FILE *fp = fopen(fname, "r");
    
   //check file exists
  if (fp == NULL)
  {
    fprintf(stderr, "Could not open file.\n");
  }
     
  char text[200];
  int characterCnt;
  int checkSumSize = atoi(argv[ 2 ]);
  int c, i = 0;
  int checksum;
  
  if(checkSumSize != 8 || checkSumSize != 16 || checkSumSize != 32)
  {
	fprintf(stderr, "Valid checksum sizes are 8, 16, or 32\n");
  }
  
   while((c = (unsigned)fgetc(fp)) != EOF)
   {
      text[i] = c;
      printf("%c", c);
	  i++;
   }
  
  printf("%2d bit checksum is %8lx for all %4d chars\n", checkSumSize, checksum, characterCnt);
  
  fclose(fp);
  
  return EXIT_SUCCESS;
}

/*while(byte>0)  //len = Total num of bytes
{
    Word = ((Buf[i]<<8) + Buf[i+1]) + Checksum; //get two bytes at a time and  add previous calculated checsum value

    Checksum = Word & 0x0FFFF; //Discard the carry if any

    Word = (Word>>16);     //Keep the carryout for value exceeding 16 Bit

    Checksum = Word + Checksum; //Add the carryout if any

    len -= 2; //decrease by 2 for 2 byte boundaries
    i += 2;
}

 Checksum = (unsigned int)~Checksum;*/

/*uint16_t
fletcher16(const uint8_t *data, size_t len)
{
        uint32_t c0, c1;
        unsigned int i;

        for (c0 = c1 = 0; len >= 5802; len -= 5802) {
                for (i = 0; i < 5802; ++i) {
                        c0 = c0 + *data++;
                        c1 = c1 + c0;
                }
                c0 = c0 % 255;
                c1 = c1 % 255;
        }
        for (i = 0; i < len; ++i) {
                c0 = c0 + *data++;
                c1 = c1 + c0;
        }
        c0 = c0 % 255;
        c1 = c1 % 255;
        return (c1 << 8 | c0);
} */ 
 
 /*uint32_t
fletcher32(const uint16_t *data, size_t len)
{
        uint32_t c0, c1;
        unsigned int i;

        for (c0 = c1 = 0; len >= 360; len -= 360) {
                for (i = 0; i < 360; ++i) {
                        c0 = c0 + *data++;
                        c1 = c1 + c0;
                }
                c0 = c0 % 65535;
                c1 = c1 % 65535;
        }
        for (i = 0; i < len; ++i) {
                c0 = c0 + *data++;
                c1 = c1 + c0;
        }
        c0 = c0 % 65535;
        c1 = c1 % 65535;
        return (c1 << 16 | c0);
}*/

/*Mark, the first value is shifted 24

The second 16

The third 8

Then the last needs no shift

For 32 bits

And yeah that's right Daniel

X = ('A' << 8) + 'A'
Y = ('A' << 8) + 'A'

The 16 bit checksum of AAAA is X+Y % 2^16





*/