/*Assignment: HW 01 - Building and managing a BST
|
| Author: Jarell Knight
| Language: Java
|
| To Compile: javac Hw01.java
|
| To Execute: java Hw01 filename
| where filename is in the current directory and contains
| commands to insert, delete, print.
|
| Class: COP3503 - CS II Summer 2021
| Instructor: Michael McAlpin
[| Due Date: June 23rd, 2021
*/
//package com.company;
import org.w3c.dom.Node;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;

class binarySearchTree
{
    // inner class to make nodes
    class Node {
        int value;
        Node left;
        Node right;

        public Node(int data) {
            value = data;
            left = null;
            right = null; // set left and right leaves to NULL
        }
    }

    // Define a parent Node
    public static Node rootNode;

    // Initialize as an Empty Tree
    binarySearchTree() {
        rootNode = null;
    }

    // method for inserting a node into the value
    public void insertNode(int value) {
        rootNode = insertData(rootNode, value);
    }

    // insertNode calls insert method recursively
    public Node insertData(Node rootNode, int value) {

        // insert node into empty tree
        if (rootNode == null) {
            rootNode = new Node(value);
            return rootNode;
        }
        // walk the tree inorder
        if (value <= rootNode.value) {
            rootNode.left = insertData(rootNode.left, value); //inserting node to the left
        }

        else if (value >= rootNode.value) {
            rootNode.right = insertData(rootNode.right, value); // inserting node to the right
        }

        return rootNode;
    }

    // delete method for removing node from value
    public void deleteNode(int value) {
        rootNode = deleteData(rootNode,value);
    }

    //recursive method to delete
    public Node deleteData(Node parent, int value) {

        // base case for empty tree or the node doesn't exist
        if(parent == null) {
            System.out.print(value +": NOT found - NOT deleted\n");
            return parent;
        }

        // walk tree in order
        if (value < parent.value) {
            parent.left = deleteData(parent.left, value); //remove left node of parent
        }

        else if (value > parent.value) {
            parent.right = deleteData(parent.right, value); //remove right child of parent
        }

        else {
            // base case for a node with one leaf
            if (parent.left == null) {
                return parent.right;
            }

            else if (parent.right == null) {
                return parent.left;
            }

            //when node has two children, find the min value of the right Subtree
            parent.value = smallestValue(parent.right);

            // Delete the min value Node
            parent.right = deleteData(parent.right, parent.value);
        }
        return parent;
    }

    // method to find minimum value of a given subtree
    public int smallestValue(Node parent) {
        // make parent the value being searched
        int min = parent.value;
        //iterate through tree to find min value
        while (parent.left != null) {
            //leftmost node in tree is min value
            min = parent.left.value;
            parent = parent.left;
        }
        return min;
    }

    //method to search for node in value
    public void searchData(int value) {
        Node newNode = rootNode;
        // Check to see if tree is not empty
        if (newNode != null) {
            //walk the tree inorder iteratively
            while (newNode.left != null || newNode.right != null) {
                if (newNode.value > value) {//make sure left has child
                    if (newNode.left != null) newNode = newNode.left;
                    else {
                        break;
                    }
                }

                if (newNode.value < value) {
                    if (newNode.right != null)/*check that parent has right child*/ {
                        newNode = newNode.right;
                    } else {
                        break;
                    }
                }
            }
        }
        //check to see if node in tree matches input
        if (value == newNode.value) {
            System.out.println(value + ": found");
        } else {
            System.out.println(value + ": NOT found");
        }
    }

    // method to print the value
    public void printInOrder() {
        if (rootNode == null){
            return;
        }
        printTree(rootNode);
        System.out.print("\n");
    }

    //printing value inorder
    public void printTree(Node parent) {
        if (parent != null) {
            printTree(parent.left);
            System.out.print(parent.value + " ");
            printTree(parent.right);
        }
    }
}

// Main Class
public class Hw01 {
    // method to count left and right children of tree
    public static int countChildren(binarySearchTree.Node parent) {
        //base case
        if (parent == null) {
            return 0;
        }
       
        // return all the children except the root/parent
        return 1 + countChildren(parent.left) + countChildren(parent.right);
    }

    // method to find tree height
    public static int getDepth(binarySearchTree.Node parent) {
        //base case
        if (parent == null) {
            return 0;
        }
        //return height not counting parent node
        int left_height = getDepth(parent.left);
        int right_height = getDepth(parent.right);
        if (left_height > right_height) {
            return (1 + left_height);
        }
        else {
            return (1 + right_height);
        }
    }
    //print to STDERR my NID, difficulty rating, and time spent on assignment
    public static void complexityIndicator(){
        System.err.print("ja659301;3.6;30.0");
    }


    // Here program executes reading in text file and displaying output
public static void main(String[] args) {
        
        //args command input is file name
        String fileName = args[0];
        FileReader readFile = null;

        //constructor
        binarySearchTree tree = new binarySearchTree();
        
        //scanner to read file
        BufferedReader br = null;
        
        //print out file
        try {
            //fileread is name of file
            readFile = new FileReader(fileName);
            br = new BufferedReader(readFile);
            // Base Case
            String line = null;
            System.out.println(fileName + " contains:");
            //print out each line until the end of file
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        //exception handling
        catch (Exception e) {
            e.printStackTrace();
        }

        //read file contents to determine commands and integers of bst
        try {
            //file reader
            readFile = new FileReader(fileName);
            br = new BufferedReader(readFile);
            
            //initialize variable for command input
            int num = 0;
            
            //scan all input until q is reached
            while ((num = br.read()) != 'q') {
                char command = (char) num;
                
                
                //ignore whitespace or new line
                if (command == ' ' || command == '\n') {
                    continue;
                }
                // 'i' Command invokes insert method
                if (command == 'i') {
                    
                    num = br.read();
                    num = br.read();
                    
                    //grab integer to be inserted into tree
                    String s = new String();
                    while ((char) num >= '0' && (char) num <= '9') {
                        s += Character.toString((char) num);
                        num = br.read();
                    }
                    
                    //parse integer
                    int number = Integer.parseInt(s);
                    
                    // insert into tree
                    tree.insertNode(number);
                }
                // 'd' Command invokes delete method
                else if (command == 'd') {
                    // 
                    num = br.read();
                    num = br.read();
                    
                    //grab int to be removed from tree
                    String s = new String();
                    while ((char) num >= '0' && (char) num <= '9') {
                        s += Character.toString((char) num);
                        num = br.read();
                    }
                    
                    //parse int
                    int number = Integer.parseInt(s);
                    
                    // remove node from tree
                    tree.deleteNode(number);
                }
                // 's' Command invokes search method
                else if (command == 's') {
                   
                    num = br.read();
                    num = br.read();
                    
                    //grab integer to be queried
                    String s = new String();
                    while ((char) num >= '0' && (char) num <= '9') {
                        s += Character.toString((char) num);
                        num = br.read();
                    }
                    
                    //parse int
                    int number = Integer.parseInt(s);
                    
                    //search for node
                    tree.searchData(number);
                }
                // p' Command invokes print method
                else if (command == 'p') {
                    //print the nodes of tree in-order
                    tree.printInOrder();
                }
                // 'q' command to quit scanning file
                else if (command == 'q') {
                    break;
                }
            }
        }//exception handling
        catch (Exception e) {
            e.printStackTrace();
        }

        //print out the number of children and height of value
        System.out.println("left children:" + "\t\t" + countChildren(tree.rootNode.left));
        System.out.println("left depth:" + "\t\t" + getDepth(tree.rootNode.left));
        System.out.println("right children:" + "\t\t" + countChildren(tree.rootNode.right));
        System.out.println("right depth:" + "\t\t" + getDepth(tree.rootNode.right));
        //call to display student information
        complexityIndicator();
        return;
    }
}



/*I Jarell Knight 0722954 affirm that this program is entirely my own work and that I have neither developed my code together with
any another person, nor copied any code from any other person, nor permitted my code to be copied or otherwise used by any other person, nor have I
copied, modified, or otherwise used programs created by others. I acknowledge  that any violation of the above terms will be treated as academic dishonesty.
*/