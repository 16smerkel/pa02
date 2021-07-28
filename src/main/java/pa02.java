/*=============================================================================
|   Assignment:  pa02 - Calculating an 8, 16, or 32 bit checksum
|                       for a simple ASCII file containing only ASCII
|                       characters. This file is terminated by a NEWLINE
|                       character which has a hexadecimal value of ’0a’.
|
|       Author:  Sean Merkel
|     Language:  c, c++, Java
|
|   To Compile:  javac pa02.java
|                gcc -o pa02 pa02.c
|                g++ -o pa02 pa02.cpp
|
|   To Execute:  java -> java pa02 inputFilename.txt 8
|          or    c++  -> ./pa02
|          or    c    -> ./pa02
|                         where inputFilename.txt is the ASCII text file
|                           and 8 is the checksum size in bits
|                           (Valid options are 8, 16, & 32)
|
|         Note:  All input files are simple 8 bit ASCII input
|
|        Class:  CIS3360 - Security in Computing - Summer 2021
|   Instructor:  McAlpin
|     Due Date:  per assignment
|+=============================================================================*/
import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.*;

public class pa02{
    public static void main(String[] args) throws IOException {
        File inFile = new File(args[0]);
        int bitNum = 0;
        int theCount = 0;
        int theXCount = 0;
        BufferedReader theBr = new BufferedReader(new FileReader(inFile));
        String theLine;
        String theTest = "";
        int lineCount = 0;
        while ((theLine = theBr.readLine()) != null) {
            for (char c : theLine.toCharArray()) {
                theTest += c;
                lineCount++;
                if (lineCount % 80 == 0) {
                    theTest += '\n';
                }
            }
        }
        System.out.println(theTest);
        theTest = theTest.replace("\n", "");
        String checkSumBit = args[1];
        if(checkSumBit.contains("8")) {
            int theSum = 0;
            char theChar[] = theTest.toCharArray();
            theCount = theTest.length();
            for(int i = 0; i < theChar.length; i++) {
                String hexString = Integer.toHexString(theChar[i]);
                int theDecimal = Integer.parseInt(hexString, 16);
                theSum += theDecimal;
            }
            theSum += 10;
            theCount++;
            String result = "";

            int binary[] = new int[40];
            int index = 0;
            while(theSum > 0){
                binary[index++] = theSum % 2;
                theSum = theSum/2;
            }
            for(int i = 7; i >= 0; i--){  // if 8 input 8 -1 if 16 then 16 -1
                result += binary[i];
            }
            int theBinary = Integer.parseInt(result);
            int i = 0, finalDecimal = 0;
            while(theBinary > 0)
            {
                finalDecimal += Math.pow(2, i++) * (theBinary % 10);
                theBinary /= 10;
            }
            String checkSum = Long.toHexString(finalDecimal);
            String fourZero = "0000";
            String finalSum = checkSum.replace(fourZero, "");
            int spaceLen = 8 - finalSum.length();
            bitNum = 8;
            System.out.print("\n" + bitNum + " bit checksum is ");
            for (int j = 0; j < spaceLen; j++) {
                System.out.print(" ");
            }
            System.out.print(checkSum + " for all ");
            int spaceCount = theCount;
            spaceLen = 0;
            while (spaceCount > 0) {
                spaceCount /= 10;
                spaceLen++;
            }
            spaceLen = 4 - spaceLen;
            for (int j = 0; j < spaceLen; j++) {
                System.out.print(" ");
            }
            System.out.print(theCount + " chars");

        }
        else if (checkSumBit.contains("16")) {
            long theSum = 0;
            char theChar[] = theTest.toCharArray();
            StringBuffer sb = new StringBuffer();
            String hexString = "";
            for (int i = 0; i < theChar.length; i++) {
                i = i + 2;
                hexString = "";
                for (int j = i - 2; j < i; j++) {
                    if (j >= theChar.length) {
                        theXCount++;
                    } else {
                        hexString += Integer.toHexString(theChar[j]);
                        theCount++;
                    }
                }
                i = i - 1;
                if (theXCount == 0 && i != theChar.length - 1) {
                    long theDecimal = Integer.parseInt(hexString, 16);
                    theSum += theDecimal;
                } else if (theXCount >= 1 && i == theChar.length - 1) {
                    long theDecimal = Integer.parseInt(hexString, 16);
                    theSum += theDecimal;
                }
            }
            if (hexString.length() == 4) {
                long theDecimal = Integer.parseInt(hexString, 16);
                theSum += theDecimal;
                hexString = "";
            }
            hexString += "0A";
            theCount++;
            theXCount = theCount % 2;
            if (theXCount != 0) {
                theXCount = 2 - theXCount;
            }
            if (theXCount != 0) {
                for (int i = 0; i < theXCount; i++) {
                    System.out.print("X");
                    hexString += "58";
                    theCount++;
                }
            }
            System.out.print("\n");
            int theDecimal = Integer.parseInt(hexString, 16);
            theSum += theDecimal;
            String result = "";

            Long theBinaryArr[] = new Long[40];
            int index = 0;
            while (theSum > 0) {
                theBinaryArr[index++] = theSum % 2;
                theSum = theSum / 2;
            }
            for (int i = 15; i >= 0; i--) {
                result += theBinaryArr[i];
            }
            long finalDecimal = Long.parseLong(result, 2);
            String checkSum = Long.toHexString(finalDecimal);
            String fourZero = "0000";
            String finalSum = checkSum.replace(fourZero, "");
            int spaceLen = 8 - finalSum.length();
            bitNum = 16;
            System.out.print(bitNum + " bit checksum is ");
            for (int i = 0; i < spaceLen; i++) {
                System.out.print(" ");
            }
            System.out.print(checkSum + " for all ");
            int spaceCount = theCount;
            spaceLen = 0;
            while (spaceCount > 0) {
                spaceCount /= 10;
                spaceLen++;
            }
            spaceLen = 4 - spaceLen;
            for (int i = 0; i < spaceLen; i++) {
                System.out.print(" ");
            }
            System.out.print(theCount + " chars");
            // System.err.printf("%2d bit checksum is %8lx for all %4d chars\n",checkSumSize, finalSum, theCount);
        } else if(checkSumBit.contains("32")) {
            long theSum = 0;
            char theChar[] = theTest.toCharArray();
            String hexString = "";
            for(int i = 0; i < theChar.length; i++) {
                i = i + 4;
                hexString = "";
                for(int j = i - 4; j < i; j++) {
                    if(j >= theChar.length)
                    {
                        theXCount++;
                    }
                    else {
                        hexString += Integer.toHexString(theChar[j]);
                        theCount++;
                    }
                }
                i = i - 1;
                if (theXCount == 0 && i != theChar.length - 1)
                {
                    long theDecimal = Integer.parseInt(hexString, 16);
                    theSum += theDecimal;
                }
                else if(theXCount >= 1 && i == theChar.length - 1)
                {
                    long theDecimal = Integer.parseInt(hexString, 16);
                    theSum += theDecimal;
                }
            }
            if(hexString.length() == 8)
            {
                long theDecimal = Integer.parseInt(hexString, 16);
                theSum += theDecimal;
                hexString = "";
            }
            hexString += "0A";
            theCount++;
            theXCount = theCount % 4;
            if(theXCount != 0)
            {
                theXCount = 4 - theXCount;
            }
            if (theXCount != 0)
            {;
                for (int i = 0; i < theXCount; i++)
                {
                    System.out.print("X");
                    hexString += "58";
                    theCount++;
                }
            }
            System.out.print("\n");
            int theDecimal = Integer.parseInt(hexString, 16);
            theSum += theDecimal;
            String result = "";

            Long theBinaryArr[] = new Long[40];
            int index = 0;
            while(theSum > 0){
                theBinaryArr[index++] = theSum % 2;
                theSum = theSum/2;
            }
            for(int i = 31; i >= 0; i--){
                result += theBinaryArr[i];
            }
            long finalDecimal = Long.parseLong(result,2);
            String checkSum = Long.toHexString(finalDecimal);
            String fourZero = "0000";
            String finalSum = checkSum.replace(fourZero, "");
            int spaceLen = 8 - finalSum.length();
            bitNum = 32;
            System.out.print(bitNum + " bit checksum is ");
            for(int i = 0; i < spaceLen; i++)
            {
                System.out.print(" ");
            }
            System.out.print(checkSum + " for all ");
            int spaceCount = theCount;
            spaceLen = 0;
            while(spaceCount > 0)
            {
                spaceCount /= 10;
                spaceLen++;
            }
            spaceLen = 4 - spaceLen;
            for(int i = 0; i < spaceLen; i++)
            {
                System.out.print(" ");
            }
            System.out.print(theCount + " chars");
        }
    }
}

/*=============================================================================
|     I Sean Merkel (se758574) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied  or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
 +=============================================================================*/
