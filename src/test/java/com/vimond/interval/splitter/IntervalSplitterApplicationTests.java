package com.vimond.interval.splitter;

import com.vimond.interval.splitter.Models.IntervalSet;
import com.vimond.interval.splitter.service.IntervalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.vimond.interval.splitter.utils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class IntervalSplitterApplicationTests {

	@Autowired
	IntervalService intervalService;
	@Test
	void contextLoads() {
	}
	List<IntervalSet> includeIntervalSets = null;
	List<IntervalSet>  excludeIntervalSets = null;
	List<IntervalSet>  expectedOutPutSets = null;
    @BeforeEach
	public void setup() {
		includeIntervalSets = new ArrayList<IntervalSet>();
		excludeIntervalSets = new ArrayList<IntervalSet>();
		expectedOutPutSets  = new ArrayList<IntervalSet>();
	}


	@Test
	public void testResultOutPutSuccessCase() {
		includeIntervalSets.add(new IntervalSet(10,100));
		excludeIntervalSets.add(new IntervalSet(20,30));
		expectedOutPutSets.add(new IntervalSet(10,19));
		expectedOutPutSets.add(new IntervalSet(31,100));
		includeIntervalSets = IntervalUtil.mergeOverlappingIntervals(includeIntervalSets);
		excludeIntervalSets = IntervalUtil.mergeOverlappingIntervals(excludeIntervalSets);
		List<IntervalSet> resultSets =   IntervalUtil.getOutputIntervalsMap(includeIntervalSets,excludeIntervalSets);
		//converting array
		int[] resultArr = convertArrayFromList(resultSets);
		int[] expectedArr = convertArrayFromList(expectedOutPutSets);
		Assertions.assertArrayEquals(resultArr,expectedArr);

	}

	@Test
	public void testResultOutPutSuccessCase2() {
		includeIntervalSets.add(new IntervalSet(50,5000));
		includeIntervalSets.add(new IntervalSet(10,100));
		includeIntervalSets = IntervalUtil.mergeOverlappingIntervals(includeIntervalSets);
		excludeIntervalSets = IntervalUtil.mergeOverlappingIntervals(excludeIntervalSets);
		expectedOutPutSets.add(new IntervalSet(10,5000));
		List<IntervalSet> resultSets =   IntervalUtil.getOutputIntervalsMap(includeIntervalSets,excludeIntervalSets);
		//converting array
		int[] resultArr = !resultSets.isEmpty() ? convertArrayFromList(resultSets) : null;
		int[] expectedArr = !expectedOutPutSets.isEmpty() ? convertArrayFromList(expectedOutPutSets): null;
		Assertions.assertArrayEquals(resultArr,expectedArr);

	}

	@Test
	public void testResultOutPutSuccessCase3() {
		includeIntervalSets.add(new IntervalSet(10,100));
		includeIntervalSets.add(new IntervalSet(200,300));
		excludeIntervalSets.add(new IntervalSet(95,205));
		includeIntervalSets = IntervalUtil.mergeOverlappingIntervals(includeIntervalSets);
		excludeIntervalSets = IntervalUtil.mergeOverlappingIntervals(excludeIntervalSets);
		expectedOutPutSets.add(new IntervalSet(10,94));
		expectedOutPutSets.add(new IntervalSet(206,300));
		List<IntervalSet> resultSets =   IntervalUtil.getOutputIntervalsMap(includeIntervalSets,excludeIntervalSets);
		//converting array
		int[] resultArr = !resultSets.isEmpty() ? convertArrayFromList(resultSets) : null;
		int[] expectedArr = !expectedOutPutSets.isEmpty() ? convertArrayFromList(expectedOutPutSets): null;
		Assertions.assertArrayEquals(resultArr,expectedArr);

	}

	@Test
	public void testResultOutPutFailureCase() {
		includeIntervalSets.add(new IntervalSet(10,100));
		excludeIntervalSets.add(new IntervalSet(20,30));
		expectedOutPutSets.add(new IntervalSet(10,19));
		expectedOutPutSets.add(new IntervalSet(31,50));
		includeIntervalSets = IntervalUtil.mergeOverlappingIntervals(includeIntervalSets);
		excludeIntervalSets = IntervalUtil.mergeOverlappingIntervals(excludeIntervalSets);
		List<IntervalSet> resultSets =   IntervalUtil.getOutputIntervalsMap(includeIntervalSets,excludeIntervalSets);
		//converting array
		int[] resultArr = !resultSets.isEmpty() ? convertArrayFromList(resultSets): null;
		int[] expectedArr = !expectedOutPutSets.isEmpty() ?  convertArrayFromList(expectedOutPutSets): null;
		Assertions.assertNotEquals("Expected Set intervals is not same as Result intervals" , Arrays.equals(resultArr,expectedArr));

	}

	private int[] convertArrayFromList(List<IntervalSet> resultSets) {
		int[] resultArr = new int[resultSets.size()*2];

		int j = 0;
		for(int index=0 ; index<resultSets.size() ; index ++) {
			resultArr[j] = resultSets.get(index).getFirst();
			resultArr[++j] = resultSets.get(index).getLast();
			++j;
		}

		return resultArr;
	}
}
