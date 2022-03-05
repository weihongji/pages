package com.jesse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Test {

	public static void main(String[] args) {
		test1();
		test2();
	}

	public static void test1() {
		String text = "1,2,51-58\n101,103-105\n225";
		System.out.println("------------- TEXT---------------");
		System.out.println(text);
		System.out.println("-------------SUMMARY-------------");
		System.out.println(new Pages(text).getSummary());

		System.out.println();

		text = "1,@,e,4,5,1-5,5,6,,8,  9,  ,11,12-10,9998-20000";
		System.out.println("------------- TEXT---------------");
		System.out.println(text);
		System.out.println("-------------SUMMARY-------------");
		System.out.println(new Pages(text).getSummary());
	}

	public static void test2() {
		System.out.println("\n\n");
		System.out.println("====================================================");
		System.out.println("Enter pages below and see result: ('exit' to end)");
		System.out.println("====================================================");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				String input = in.readLine();
				if (input.isEmpty()) {
					continue;
				}
				if (input.equals("exit")) {
					break;
				}
				Pages p = new Pages(input);
				System.out.println(p.getSummary());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}