
package com.example.bumblebee;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;



public class Demo {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        String inputValue = scan.nextLine();
        List<List<Integer>> arrValue = new ArrayList<>();
        String[] arrInput = inputValue.split(" ");
        List<Integer> arrInt = Arrays.stream(arrInput).map(Integer::parseInt).collect(Collectors.toList());
        arrValue.add(0, arrInt) ;

        System.out.println(arrValue);


    }
}
