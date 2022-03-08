package com.sopra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Check_Function {

	public static List<List<String>> comp(HashMap<String, Integer> original, HashMap<String, Integer> target) {

		List<String> miss = new ArrayList<>();
		List<String> addi = new ArrayList<>();
		List<List<String>> res = new ArrayList<>();

		for (Map.Entry<String, Integer> entry : target.entrySet()) {
			String targetKey = entry.getKey();
			int targetValue = entry.getValue();
			
			if(!original.containsKey(targetKey) || original.get(targetKey) < targetValue)
				addi.add(targetKey);
			else if(!original.containsKey(targetKey) || original.get(targetKey) > targetValue)
				miss.add(targetKey);
		}

		res.add(addi);
		res.add(miss);
		return res;
	}
}
