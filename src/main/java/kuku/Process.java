package kuku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Process{
	private static boolean mazeWalkFromTopLeftToBottomRightResult = false;
	
	public static void main(String[] args){
//		long[] longArray = new long[6];
//		longArray[0] = 10;
//		longArray[1] = 456;
//		longArray[2] = 456;
//		longArray[3] = 123;
//		longArray[4] = 123;
//		longArray[5] = 10;
//		long[] resultLong = getLessCountNumber(longArray);
		
//		long[][] longArray = new long[3][3];
//		longArray[0] = new long[]{0,0,1};
//		longArray[1] = new long[]{1,0,0};
//		longArray[2] = new long[]{1,1,0};
//		
//		boolean result = mazeWalkFromTopLeftToBottomRight(longArray,3);
//		System.out.println("result = " + result);
		
//		String word = changeWordToCiper("hello","poiuytrewqasdfghjklmnbvcxz");
//		System.out.println("word = " + word);
		
		
//		long[] resultLong = findPrimeNumbersFromFibonacci(6);
		
//		String s = "abc";
//		List<String> operations = new ArrayList<String>();
//		operations.add("0 0 L");
//		operations.add("2 2 L");
//		operations.add("0 2 R");
//		
//		String resultString = shiftLetters(s,operations);
		
		//test case
		//[3, 6, 2, 9, -1, 10]"Left"
		//[1, 4, 100, 5]"Right"
		//[1, 10, 5, 1, 0, 6]""
		//[]""
		//[1]""
		
		//traversal沒通過，輸入1，nullpoint
		//traversal沒通過，輸入1,2,3,4,5,6,7,8，回[1, 2, 3, 4, 5, 6, 7, 8, 6, 7]
		Integer[] dataArray = new Integer[8];
		dataArray[0] = 1;
		dataArray[1] = 2;
		dataArray[2] = 3;
		dataArray[3] = 4;
		dataArray[4] = 5;
		dataArray[5] = 6;
		dataArray[6] = 7;
		dataArray[7] = 8;
//		dataArray[8] = 9;
//		dataArray[9] = 10;
//		dataArray[10] = 11;
//		dataArray[11] = 12;
//		dataArray[12] = 13;
//		dataArray[13] = 14;
//		dataArray[14] = 15;
//		dataArray[15] = 16;
//		dataArray[16] = 17;
		
		String result = findRightOrLeftBiggerFromBinaryTree(dataArray);
		System.out.println("result = " + result);
	}
	
	//找出出現次數最少的數字，如果有多個數字，照ASC排序
	public static long[] getLessCountNumber(long[] numbers){
		if(numbers.length == 0){
			return new long[]{};
		}
        
        Set<Integer> numberSet = new HashSet<Integer>();
        List<Integer> longList = new ArrayList<Integer>();
        
        for(int x=0;x<numbers.length;x++){
            numberSet.add((int)numbers[x]);
            longList.add((int)numbers[x]);
        }
        
        int smallestCount = Integer.MAX_VALUE;
        List<Integer> smallestNumberList = new ArrayList<Integer>();
        
        Iterator<Integer> it = numberSet.iterator(); 
        
        while(it.hasNext()){
        	int tempNumber = it.next();
        	int tempCount = Collections.frequency(longList,tempNumber);
        	
        	if(tempCount == smallestCount){
        		smallestNumberList.add(tempNumber);
        	}
        	else if(tempCount < smallestCount){
        		smallestCount = tempCount;
        		
        		smallestNumberList.clear();
        		smallestNumberList.add(tempNumber);
        	}
        }
        
        Collections.sort(smallestNumberList);
        
        long[] returnLong = new long[smallestNumberList.size()];
        for(int x=0;x<smallestNumberList.size();x++){
        	returnLong[x] = smallestNumberList.get(x);
        }
        
        return returnLong;
    }
	//迷宮走路，從左上走到右下，陣列長度大於等於2，呈正方形，0表示路；1表示牆，有路回true，反之false
	public static boolean mazeWalkFromTopLeftToBottomRight(long[][] maze,long n){
		int start = (int)maze[0][0];
		int end = (int)maze[(int)n-1][(int)n-1];
		
		if(start == 1 || end == 1){
			return mazeWalkFromTopLeftToBottomRightResult;
		}
		
		walkMaze(maze,0,0);
		
		return mazeWalkFromTopLeftToBottomRightResult;
	}
	//迷宮走路的遞迴程式
	private static void walkMaze(long[][] maze,int nowX,int nowY){
		//向右
		//判斷不能超出邊界且有路
		if(nowX + 1 < maze[0].length && (int)maze[nowY][nowX + 1] == 0){
			//到終點
			if(nowX + 1 == maze[0].length - 1 && nowY == maze[0].length - 1){
				mazeWalkFromTopLeftToBottomRightResult = true;
				return;
			}
			
			walkMaze(maze,nowX + 1,nowY);
		}
		
		//向下
		//判斷不能超出邊界且有路
		if(nowY + 1 < maze[0].length && (int)maze[nowY + 1][nowX] == 0){
			//到終點
			if(nowX == maze[0].length - 1 && nowY + 1 == maze[0].length - 1){
				mazeWalkFromTopLeftToBottomRightResult = true;
				return;
			}
			
			walkMaze(maze,nowX,nowY + 1);
		}
	}
	//word是要被轉換的文字；ciper是英文字abc到z依序對應的英文字母。例入ciper=rty，表示a=r，b=t，c=y來做轉換
	public static String changeWordToCiper(String word,String cipher){
        //未滿足a到z的對應
		if(cipher.length() < 26){
            return "";
        }
        
        for(int x=0;x<cipher.length();x++){
            String thisChar = cipher.substring(x,x + 1);
            String spliceCipher = cipher.substring(x + 1);
            //ciper裡面不能有兩個以上相同的字母
            if(spliceCipher.contains(thisChar)){
                return "";
            }
        }
        //要被轉換的文字只能是小寫英文
        if(!word.matches("^[a-z]+$")){
            return "";
        }
        
        Map<String,String> ciperMap = new HashMap<String,String>();
        for(int x=0;x<cipher.length();x++){
            if(x == 0){ciperMap.put("a",cipher.substring(x,x + 1));}
            if(x == 1){ciperMap.put("b",cipher.substring(x,x + 1));}
            if(x == 2){ciperMap.put("c",cipher.substring(x,x + 1));}
            if(x == 3){ciperMap.put("d",cipher.substring(x,x + 1));}
            if(x == 4){ciperMap.put("e",cipher.substring(x,x + 1));}
            if(x == 5){ciperMap.put("f",cipher.substring(x,x + 1));}
            if(x == 6){ciperMap.put("g",cipher.substring(x,x + 1));}
            if(x == 7){ciperMap.put("h",cipher.substring(x,x + 1));}
            if(x == 8){ciperMap.put("i",cipher.substring(x,x + 1));}
            if(x == 9){ciperMap.put("j",cipher.substring(x,x + 1));}
            if(x == 10){ciperMap.put("k",cipher.substring(x,x + 1));}
            if(x == 11){ciperMap.put("l",cipher.substring(x,x + 1));}
            if(x == 12){ciperMap.put("m",cipher.substring(x,x + 1));}
            if(x == 13){ciperMap.put("n",cipher.substring(x,x + 1));}
            if(x == 14){ciperMap.put("o",cipher.substring(x,x + 1));}
            if(x == 15){ciperMap.put("p",cipher.substring(x,x + 1));}
            if(x == 16){ciperMap.put("q",cipher.substring(x,x + 1));}
            if(x == 17){ciperMap.put("r",cipher.substring(x,x + 1));}
            if(x == 18){ciperMap.put("s",cipher.substring(x,x + 1));}
            if(x == 19){ciperMap.put("t",cipher.substring(x,x + 1));}
            if(x == 20){ciperMap.put("u",cipher.substring(x,x + 1));}
            if(x == 21){ciperMap.put("v",cipher.substring(x,x + 1));}
            if(x == 22){ciperMap.put("w",cipher.substring(x,x + 1));}
            if(x == 23){ciperMap.put("x",cipher.substring(x,x + 1));}
            if(x == 24){ciperMap.put("y",cipher.substring(x,x + 1));}
            if(x == 25){ciperMap.put("z",cipher.substring(x,x + 1));}
        }
        System.out.println(ciperMap);
        
        String result = "";
        for(int x=0;x<word.length();x++){
            result += ciperMap.get(word.substring(x,x + 1));
        }
        
        return result;
    }
	//從第n項之前的費波南希數列找出其中的質數
	public static long[] findPrimeNumbersFromFibonacci(long n){
        List<Long> FibonacciList = new ArrayList<Long>();
        
        for(int x=0;x<(int)n;x++){
            if(x == 0){
                FibonacciList.add(1L);
                continue;
            }
            else if(x == 1){
                FibonacciList.add(1L);
                continue;
            }
            
            FibonacciList.add(FibonacciList.get(x-2)+FibonacciList.get(x-1));
        }
        
        for(int x=0;x<FibonacciList.size();x++){
            System.out.print(" " + FibonacciList.get(x));
        }
        
        //前兩個都是1，所以沒有質數
        if(FibonacciList.size() <= 2){
            return new long[]{};
        }
        
        List<Long> resultList = new ArrayList<Long>();
        for(int x=2;x<FibonacciList.size();x++){
            long thisNum = FibonacciList.get(x);
            
            //2比較特別，所以獨立出來判斷
            if(thisNum == 2){
                resultList.add(2L);
            }
            
            for(int y=2;y<thisNum;y++){
                if(thisNum % y == 0){
                    break;
                }
                
                if(y == thisNum - 1){
                    resultList.add(thisNum);
                }
            }
        }
        
        long[] resultLong = new long[resultList.size()];
        for(int x=0;x<resultList.size();x++){
            resultLong[x] = resultList.get(x);
        }
        
        return resultLong;
    }
	/**
	 * 位移字母
	 * @param s 原始字串
	 * @param operations 好幾組指令，每一組由2個數字及一個方向字母組成，以空白分隔
	 * @return 結果字串
	 */
	public static String shiftLetters(String s,List<String> operations){
		Map<String,String> forwardMap = new HashMap<String,String>();
        Map<String,String> backwardMap = new HashMap<String,String>();
        
        forwardMap.put("a","b");forwardMap.put("b","c");forwardMap.put("c","d");
        forwardMap.put("d","e");forwardMap.put("e","f");forwardMap.put("f","g");
        forwardMap.put("g","h");forwardMap.put("h","i");forwardMap.put("i","j");
		forwardMap.put("j","k");forwardMap.put("k","l");forwardMap.put("l","m");
		forwardMap.put("m","n");forwardMap.put("n","o");forwardMap.put("o","p");
		forwardMap.put("p","q");forwardMap.put("q","r");forwardMap.put("r","s");
		forwardMap.put("s","t");forwardMap.put("t","u");forwardMap.put("u","v");
		forwardMap.put("v","w");forwardMap.put("w","x");forwardMap.put("x","y");
		forwardMap.put("y","z");forwardMap.put("z","a");
    
		backwardMap.put("a","z");backwardMap.put("b","a");backwardMap.put("c","b");
		backwardMap.put("d","c");backwardMap.put("e","d");backwardMap.put("f","e");
		backwardMap.put("g","f");backwardMap.put("h","g");backwardMap.put("i","h");
		backwardMap.put("j","i");backwardMap.put("k","j");backwardMap.put("l","k");
		backwardMap.put("m","l");backwardMap.put("n","m");backwardMap.put("o","n");
		backwardMap.put("p","o");backwardMap.put("q","p");backwardMap.put("r","q");
		backwardMap.put("s","r");backwardMap.put("t","s");backwardMap.put("u","t");
		backwardMap.put("v","u");backwardMap.put("w","v");backwardMap.put("x","w");
		backwardMap.put("y","x");backwardMap.put("z","y");
		
        int loopTime = operations.size();
        List<String> resultList = new ArrayList<String>(Arrays.asList(s.split("")));
        
        for(int x=0;x<loopTime;x++){
            String operationThis = operations.get(x);
            
            int i = Integer.valueOf(operationThis.split(" ")[0]);
            System.out.println("i=" + i);
            int j = Integer.valueOf(operationThis.split(" ")[1]);
            System.out.println("j=" + j);
            String direction = operationThis.split(" ")[2];
            System.out.println("direction=" + direction);
            
            for(int y=i;y<=j;y++){
                String kuku = resultList.get(y);
                System.out.println("kuku=" + kuku);
                
                if("R".equals(direction)){
                	resultList.set(y,forwardMap.get(kuku));
                }
                else{
                	resultList.set(y,backwardMap.get(kuku));
                }
            }
        }
        
		return String.join("",resultList);
	}
	
	//二元樹，找出左邊或右邊的節點總合誰比較大。左邊總合大回"LEFT"；右邊則回"RIGHT"，如果沒有節點或兩邊相等的話回""
	public static String findRightOrLeftBiggerFromBinaryTree(Integer[] dataAray){
		if(dataAray.length == 0){
			return "";
		}
		
		BinaryTree binaryTree = new BinaryTree();
		
		BinaryTreeNode rootNode = binaryTree.setValuesFromArray(dataAray);
		
//		BinaryTreeNode[] breadthFirstTraversalArray = binaryTree.breadthFirstTraversal(rootNode);
		Integer[] breadthFirstTraversalArray = binaryTree.breadthFirstTraversal(rootNode);
		
		System.out.println("");
		
		
		return "kuku";
	}
}
