/*=============================================================================
| Assignment: HW 02 - Building and managing a Skiplist
|
| Author: Jarell Knight
| Language: Java
|
| To Compile: javac Hw02.java
|
| To Execute: java Hw02 filename
| where filename is in the current directory and contains
| commands to insert, search, delete, print & quit.
|
| Class: COP3503 - CS II Summer 2021
| Instructor: McAlpin
| Due Date: July 21, 2021
|
+=============================================================================*/
//package com.company;
import java.io.*;
import java.util.*;

public class Hw02 {
    public static void main(String[] args) throws IOException {
        //1st argument from command line is name of file
        String fileName = args[0];
        //Print NID, level of difficulty, time spent on assignment
        complexityIndicator();
        System.out.println("For the input file named " + fileName);
        //Seed the inputs
        long seed = 42;
        Random random = new Random();
        //conditional to determine whether RNG is seeded/unseeded
        try {//check for number of command line arguments
            if (args.length > 1 && args[1].equalsIgnoreCase("R")) {
                random.setSeed(System.currentTimeMillis());
                System.out.println("With the RNG seeded,");
            }
            else {
                random.setSeed(seed);
                System.out.println("With the RNG unseeded,");
            }
        }
        catch (ArrayIndexOutOfBoundsException aiobe) {
            random.setSeed(seed);
            System.out.println("With the RNG unseeded,");
        }
        //create file reader
        FileReader readFile;
        //SkipList Constructor
        SkipList skipList = new SkipList();
        BufferedReader br = null;//initialize scanner
        ArrayList<String> list = new ArrayList<>();//array list for file contents
        String s;//string for file input
        // Read Each Character/Integer to Determine the Command and Skip List Function
        try {
            // Initialize File Reader
            readFile = new FileReader(fileName);
            br = new BufferedReader(readFile);
            while ((s = br.readLine()) != null) {
                //import contents of file into arraylist
                list.add(s);
            }
        }//Exception handling
        catch (IOException e) {
            e.printStackTrace();
        }
        //stop scanning
        assert br != null;
        br.close();
        //iterate through list to parse commands
        int counter = 0;
        while (counter < list.size()) {
            String str = list.get(counter);
           //create skiplist based on commands
            if (str.contains("i")) {
                try{
                    int num = Integer.parseInt(str.replaceAll("[\\D]", ""));
                    skipList.insert(num, random);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
            if (str.contains("d")){
                try{
                    int num = Integer.parseInt(str.replaceAll("[\\D]", ""));
                    skipList.delete(num);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
            if (str.contains("s")){
                try{
                    int num = Integer.parseInt(str.replaceAll("[\\D]", ""));
                    skipList.search(num);
                }
                catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
            if (str.contains("p")){
                skipList.printAll();
            }
            if (str.contains("q")){
                break;
            }
            counter++;
        }
    }
    // Displays the Difficulty and Duration of the Assignment
    public static void complexityIndicator() {
        System.err.println("ja659301;3.8;32.0");
    }
}
//Skip List Class
class SkipList {
    //set head and tail nodes
    public Node N_INFINITY = new Node(Integer.MIN_VALUE);
    public Node P_INFINITY = new Node(Integer.MAX_VALUE);
    public Node head = N_INFINITY;
    public Node tail = P_INFINITY;
    //Skip List Constructor
    public SkipList() {
        head.next = tail;
        tail.prev = head;
    }
    //Method to insert number into list
    public void insert(int data, Random rand) {
        //Initialize nodes
        Node current = head;
        Node position = current;
        Node temp = new Node(data);
        //return if number is already in the list
        if (data == current.data) {
            return;
        }
        //go across list
        while (data > current.data) {
            position = current;
            current = current.next;
            //return if found
            if (data == current.data) {
                return;
            }
        }
        //reset the nodes
        temp.next = current;
        temp.prev = position;
        current.prev = temp;
        position.next = temp;
        //promote the nodes
        promote(temp, rand);
    }
    //Probabilistic method to determine which integer is promoted to new level
    public void promote(Node n, Random random) {
        // Flip a Coin to Determine Heads or Tails
        int flip = ((random.nextInt()%2) + 2)%2;
        //if heads add new level to skiplist
        if (flip == 1) {
            Node headLevel = head;
            Node newHead = new Node(head.data);
            Node tailLevel = tail;
            Node newTail = new Node(tail.data);
            Node currentPosition = headLevel;
            Node lastLevel = currentPosition;
            Node current = n;
            Node temp = new Node(n.data);
            //adjust level and nodes for heads
            while (flip == 1) {
                if (headLevel.up == null) {
                    headLevel.up = newHead;
                    newHead.down = headLevel;
                    newHead.level = headLevel.level + 1;
                    head.height++;

                    tailLevel.up = newTail;
                    newTail.down = tailLevel;
                    newTail.level = tailLevel.level + 1;
                    tail.height++;

                    newHead.next = newTail;
                    newTail.prev = newHead;

                    headLevel = headLevel.up;
                    currentPosition = headLevel;
                    lastLevel = currentPosition;
                    tailLevel = tailLevel.up;
                    // Set Temporary Node if Top Level is Empty
                    if (current.up == null) {
                        current.up = temp;
                        temp.down = current;
                    }
                    // If Not, Obtain the Current Value
                    else {
                        current = current.up;
                    }
                }
                else {
                    headLevel = headLevel.up;
                    currentPosition = headLevel;
                }
                // If New Node is Greater than Current Node, Place to the Right
                while (n.data > currentPosition.data) {
                    lastLevel = currentPosition;
                    currentPosition = currentPosition.next;
                }
                // Set Temporary Nodes
                temp.next = currentPosition;
                currentPosition.prev = temp;
                temp.prev = lastLevel;
                lastLevel.next = temp;
                //Add level
                n.height++;
                //flip again
                flip = ((random.nextInt()%2) + 2)%2;
                //establish new head and tail nodes
                temp = new Node(n.data);
                newHead = new Node(head.data);
                newTail = new Node(tail.data);
            }
        }
    }
    //Method to delete integers from skiplist
    public void delete(int data) {
        Node current = head;
        Node tempPrev;
        Node tempNext;
        //base case
        if (current != null) {
            //reverse for that iterates from top level to bottom level
            for (int x = 0; x > head.height; x--) {
                if (current.up != null) {
                    current = current.up;
                }
                else {
                    break;
                }
            }
            //traverse each level of list to find integer to be deleted
            while (true) {
                //key equals integer to be deleted
                if (data == current.data) {
                    //link nodes to lower level
                    while (current != null) {
                        tempPrev = current.prev;
                        tempNext = current.next;

                        tempPrev.next = tempNext;
                        tempNext.prev = tempPrev;
                        //decrease height
                        current.height--;
                        current = current.down;
                    }
                    //confirm
                    System.out.println(data + " deleted");
                    return;
                }// if the data in current node is less than key to be deleted
                if (data > current.data) {
                    if (current.next != null) {
                        current = current.next;
                    }
                    else {
                        System.out.println(data + " integer not found - delete not successful");
                        return;
                    }
                }
                //go to the left if the data in the current node is greater than key
                if (data < current.data) {
                    if (current.prev != null) {
                        current = current.prev;
                        if (current.down != null) {
                            current = current.down;
                        }
                        else {
                            System.out.println(data + " integer not found - delete not successful");
                            return;
                        }
                    }
                    else {
                          System.out.println(data + " integer not found - delete not successful");
                          return;
                    }
                }
            }
        }
    }
    //Method to search for integer in skip list
    public void search(int data) {
        //establish current node as head node
        Node current = head;
        // base case
        if (current != null) {
            //iterate down from max level of list
            for (int x = 0; x > head.height; x--) {
                if (current.up != null) {
                    current = current.up;
                }
                else {
                    //exit loop
                    break;
                }
            }
            //traverse all levels of list until integer is found
            while (true) {
                //return if found
                if (data == current.data) {
                    System.out.println(data + " found");
                    return;
                }
                if (data > current.data) {
                    if (current.next != null) {
                        current = current.next;
                    }
                    else {
                        System.out.println(data + " NOT FOUND");
                        return;
                    }
                }
                // go back if current value is greater than key
                if (data < current.data) {
                    if (current.prev != null) {
                        current = current.prev;
                        if (current.down != null) {
                            current = current.down;
                        }
                        else {
                            System.out.println(data + " NOT FOUND");
                            return;
                        }
                    }
                    else {
                        System.out.println(data + " NOT FOUND");
                        return;
                    }
                }
            }
        }
    }
    //Print the skip list
    public void printAll() {
        //establish head node
        Node current = head;
        //base case
        if (current != null) {
            System.out.println("the current Skip List is shown below: ");
            //start in ascending order
            while (true) {
                if (current.data == Integer.MIN_VALUE) {
                    System.out.println("---infinity");
                    current = current.next;
                }
                if (current.data == Integer.MAX_VALUE) {
                    System.out.println("+++infinity");
                    System.out.println("---End of Skip List---");
                    return;
                }
                else {
                    //display the integers for each level of list
                    for (int x = 0; x < current.height ; x++) {
                        if (x == current.height - 1) {
                            System.out.print(" " + current.data + "; \n");
                        }
                        else {
                            System.out.print(" " + current.data + "; ");
                        }
                    }
                    //get the next integer
                    current = current.next;
                }
            }
        }
    }
    //Node Class
    public class Node {
        //data, level, height, and nodes for node class
        int data, level, height;
        Node prev, next, down, up;
        //Node Constructor
        public Node(int data) {
            this.prev = null;
            this.next = null;
            this.down = null;
            this.up = null;
            this.data = data;
            level = 1;
            height = 1;
        }
    }
}
/*=============================================================================
| I Jarell Knight (ja659301/0722954) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/