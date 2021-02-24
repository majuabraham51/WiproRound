package com.test.data.structure.learning.serviceImpl;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class LRUCacheImplementation {

	private final int size=4;
	HashSet<Integer> numbers;
	Deque<Integer> dataElements;
	List<String> operation = new ArrayList<String>();

	LRUCacheImplementation() {
		dataElements = new LinkedList<>();
		numbers = new HashSet<>();
	}

	public void cacheValue(List<Integer> list) {
		
		list.stream().forEach(action -> {
			
			if (!numbers.contains(action)) {
				if (dataElements.size() == size) {
					int last = dataElements.removeLast();
					numbers.remove(last);
					operation.add("remove "+last);
				}
			} else {
				dataElements.remove(action);
				System.out.println("hi"+action);
			}
			dataElements.push(action);
			numbers.add(action);
			operation.add("add "+action);
		});
		
		
	}


	public void displayOpertion() {
		operation.stream().forEach(list->{
			System.out.println(list);
		});
	}

	public void display() {
		dataElements.stream().forEach(data->
		System.out.println(data)
		);
	}

	public static void main(String[] args) {
		LRUCacheImplementation cache = new LRUCacheImplementation();
		/*Scanner myInput = new Scanner( System.in );
		myInput.nextInt();*/
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(11);
		list.add(12);
		list.add(10);
		list.add(1);
		list.add(2);
		list.add(11);
		list.add(3);
		cache.cacheValue(list);
		cache.displayOpertion();
		
	}
}
