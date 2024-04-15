package kuku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kuku.BinaryTreeNode.Direction;

public class BinaryTree{
	/**
	 * 建構子
	 */
	public BinaryTree(){
		binaryTreeNodeMap = new HashMap<String,BinaryTreeNode>();
	}
	
	private static Map<String,BinaryTreeNode> binaryTreeNodeMap;
	
	/**
	 * 取節點
	 * @param UUID 找節點的唯一值
	 * @return 節點
	 */
	public BinaryTreeNode getBinaryTreeNode(String UUID){
		return binaryTreeNodeMap.get(UUID);
	}
	
	/**
	 * 在Map設定節點
	 * @param UUID 節點的唯一值
	 * @param binaryTreeNode 節點
	 */
	public void setBinaryTreeNode(String UUID,BinaryTreeNode binaryTreeNode){
		binaryTreeNodeMap.put(UUID,binaryTreeNode);
	}
	
	/**
	 * 將整數陣列裡面的值依序塞進二元樹的節點
	 * 如果陣列裡面的值是-1的話，該節點之後無法被使用
	 * 至少要有根節點
	 * 每一個階層至少有一個節點是可以使用的
	 * 節點塞值的順序：，同一階層從左向右塞，最右邊的節點塞完後，往下一階層的最左邊節點開始塞
	 * @param dataAray 整數陣列，大於等於-1的整數
	 * @return 根節點
	 */
	public BinaryTreeNode setValuesFromArray(Integer[] dataAray){
		//根節點
		BinaryTreeNode rootNode = new BinaryTreeNode();
		
		rootNode.setValue(dataAray[0]);
		rootNode.setCanUse(true);
		
		setBinaryTreeNode(rootNode.getUUID(),rootNode);
		
		//基準節點
		BinaryTreeNode baseNode = rootNode;
		//目前的階層數
		int layerCount = 1;
		
		//將陣列裡的值塞進每個節點
		for(int x=1;x<dataAray.length;x++){
			int thisValue = dataAray[x];
			System.out.println("thisValue = " + thisValue);
			
			BinaryTreeNode nextNode = findNextNodeAtSameLayer(0,baseNode);
			
			//同一階層的節點都已被塞值
			if(nextNode == null){
				nextNode = findNextNodeAtNewLayer(rootNode,layerCount);
				layerCount += 1;
			}
			
			BinaryTreeNode newNode = new BinaryTreeNode();
			newNode.setParentNodeUUID(nextNode.getUUID());
			newNode.setValue(thisValue);
			
			//該節點之後無法使用
			if(thisValue == -1){
				newNode.setCanUse(false);
			}
			//可以使用
			else{
				newNode.setCanUse(true);
			}
			
			//左邊子節點塞值
			if(nextNode.getLeftChildNodeUUID() == null){
				newNode.setDirection(Direction.LEFT);
				
				nextNode.setLeftChildNodeUUID(newNode.getUUID());
			}
			//右邊子節點塞值
			else{
				newNode.setDirection(Direction.RIGHT);
				
				nextNode.setRightChildNodeUUID(newNode.getUUID());
			}
			
			setBinaryTreeNode(newNode.getUUID(),newNode);
			
			//更新基準節點
			baseNode = newNode;
		}
		
		return rootNode;
	}
	/**
	 * 向右找同一階層的節點
	 * @param upCount 往上走了幾次
	 * @param baseNode 基準節點
	 * @return 可以塞值的母節點
	 */
	public BinaryTreeNode findNextNodeAtSameLayer(int upCount,BinaryTreeNode baseNode){
		//根節點
		if(baseNode.getParentNodeUUID() == null){
			return null;
		}
		//找出這個節點是在左邊還是右邊
		Direction direction = baseNode.getDirection();
		
		//往上走
		baseNode = getBinaryTreeNode(baseNode.getParentNodeUUID());
		upCount += 1;
		
		//左邊，要往右邊
		if(direction == Direction.LEFT){
			BinaryTreeNode checkNode = getBinaryTreeNode(baseNode.getRightChildNodeUUID());
			
			//到達目標母節點
			if(upCount == 1 && checkNode == null){
				return baseNode;
			}
			//往下走
			else if(checkNode.isCanUse()){
				upCount -= 1;
				
				if(upCount == 1){
					return checkNode;
				}
				
				BinaryTreeNode tempNode = digDown(null,upCount,null,checkNode);
				
				if(tempNode == null){
					return findNextNodeAtSameLayer(upCount,baseNode);
				}
				else{
					return tempNode;
				}
			}
			//往上走
			else{
				return findNextNodeAtSameLayer(upCount,baseNode);
			}
		}
		//右邊，要往上走
		else{
			return findNextNodeAtSameLayer(upCount,baseNode);
		}
	}
	/**
	 * 往下探可用的節點
	 * @param tempNode 暫存節點，遞迴使用
	 * @param upCount 離目標階層多遠的距離，1的話表示在目標階層的上一階層
	 * @param direction 從上往下走的方向
	 * @param startNode 開始節點，用來判斷是否回到起點，如果是，要往上再找其他節點
	 * @return 目標節點
	 */
	public BinaryTreeNode digDown(BinaryTreeNode tempNode,int upCount,Direction direction,BinaryTreeNode startNode){
		//回到起點且下面能走的路都走了
		if(tempNode == startNode && direction == Direction.UP){
			return null;
		}
		
		//往下走
		//第一次進來
		if(direction == null){
			tempNode = getBinaryTreeNode(startNode.getLeftChildNodeUUID());
			
			if(tempNode == null){
				if(upCount == 1){
					return startNode;
				}
				else{
					return digDown(startNode,upCount,Direction.RIGHT,startNode);
				}
			}
			//節點無法使用
			else if(!tempNode.isCanUse()){
				return digDown(startNode,upCount,Direction.RIGHT,startNode);
			}
			//節點可以使用
			else{
				if(upCount == 1){
					return digDown(startNode,upCount,Direction.RIGHT,startNode);
				}
				//繼續往下
				else{
					return digDown(tempNode,upCount -= 1,Direction.LEFT,startNode);
				}
			}
		}
		else if(direction == Direction.LEFT){
			BinaryTreeNode temp2Node = getBinaryTreeNode(tempNode.getLeftChildNodeUUID());
			
			if(temp2Node == null){
				if(upCount == 1){
					return tempNode;
				}
				else{
					return digDown(tempNode,upCount,Direction.RIGHT,startNode);
				}
			}
			//節點無法使用
			else if(!temp2Node.isCanUse()){
				return digDown(tempNode,upCount,Direction.RIGHT,startNode);
			}
			//節點可以使用
			else{
				if(upCount == 1){
					return digDown(tempNode,upCount,Direction.RIGHT,startNode);
				}
				//繼續往下
				else{
					return digDown(temp2Node,upCount -= 1,Direction.LEFT,startNode);
				}
			}
		}
		else if(direction == Direction.RIGHT){
			BinaryTreeNode temp2Node = getBinaryTreeNode(tempNode.getRightChildNodeUUID());
			
			if(temp2Node == null){
				if(upCount == 1){
					return tempNode;
				}
				else{
					return digDown(tempNode,upCount,Direction.UP,startNode);
				}
			}
			//節點無法使用
			else if(!temp2Node.isCanUse()){
				return digDown(tempNode,upCount,Direction.UP,startNode);
			}
			//節點可以使用
			else{
				if(upCount == 1){
					return digDown(tempNode,upCount,Direction.UP,startNode);
				}
				//繼續往下
				else{
					return digDown(temp2Node,upCount -= 1,Direction.LEFT,startNode);
				}
			}
		}
		//向上走
		else{
			//判斷是從左邊或右邊上來
			if(tempNode.getDirection() == Direction.LEFT){
				tempNode = getBinaryTreeNode(tempNode.getParentNodeUUID());
				
				return digDown(tempNode,upCount += 1,Direction.RIGHT,startNode);
			}
			else{
				tempNode = getBinaryTreeNode(tempNode.getParentNodeUUID());
				
				return digDown(tempNode,upCount += 1,Direction.UP,startNode);
			}
		}
	}
	
	/**
	 * 往下往左找，新的階層開始塞值時會用到
	 * @param tempNode 暫存的節點
	 * @param targetCount 要向下多少節點才會到達可以塞值的母節點，目標值為1，因為要塞值的那個節點尚未存在，最多走到該節點上方的母節點
	 * @return 可以塞值的母節點
	 */
	public BinaryTreeNode findNextNodeAtNewLayer(BinaryTreeNode tempNode,int targetCount){
		//到達目標母節點的階層
		if(targetCount == 1){
			//左邊子節點可以塞值
			if(tempNode.getLeftChildNodeUUID() == null){
				return tempNode;
			}
			//右邊子節點可以塞值
			else if(tempNode.getRightChildNodeUUID() == null){
				return tempNode;
			}
			//這個母節點不對，再繼續找另一個
			else{
				return findNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getParentNodeUUID()),targetCount += 1);
			}
		}
		//尚未到達目標母節點的階層，還要向下走
		else{
			//左邊子節點不存在或不能使用
			if(tempNode.getLeftChildNodeUUID() == null || !getBinaryTreeNode(tempNode.getLeftChildNodeUUID()).isCanUse()){
				//右邊子節點不存在或不能使用
				if(tempNode.getRightChildNodeUUID() == null || !getBinaryTreeNode(tempNode.getRightChildNodeUUID()).isCanUse()){
					//往上走，找另一個
					return findNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getParentNodeUUID()),targetCount += 1);
				}
				//右邊子節點可以走
				else{
					//向下走
					return findNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getRightChildNodeUUID()),targetCount -= 1);
				}
			}
			//左邊子節點可以走
			else{
				//向下走
				return findNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getLeftChildNodeUUID()),targetCount -= 1);
			}
		}
	}
	/**
	 * Level Order Tree Traversal，廣度優先搜尋
	 * @param rootNode 根節點
	 * @return 排序好的節點陣列
	 */
	//測試的時候先傳int
//	public BinaryTreeNode[] breadthFirstTraversal(BinaryTreeNode rootNode){
//		List<BinaryTreeNode> breadthFirstTraversalList = new ArrayList<BinaryTreeNode>();
	public Integer[] breadthFirstTraversal(BinaryTreeNode rootNode){
		List<Integer> breadthFirstTraversalList = new ArrayList<Integer>();
		
		breadthFirstTraversalList.add(rootNode.getValue());
		
		BinaryTreeNode nextNode = rootNode;
		//目前的階層數
		int layerCount = 1;
		//下面這一段怪怪的，要測
		while(true){
			nextNode = breadthFirstFindNextNodeAtSameLayer(0,nextNode);
			
			//同一階層的節點都已被塞值
			if(nextNode == null){
				nextNode = breadthFirstFindNextNodeAtNewLayer(rootNode,layerCount,null);
				layerCount += 1;
				
				//沒有節點了
				if(nextNode == null){
					break;
				}
				else{
					breadthFirstTraversalList.add(nextNode.getValue());
				}
			}
			else{
				breadthFirstTraversalList.add(nextNode.getValue());
			}
			//temp中斷點，之後刪掉
			if(nextNode.getValue() == 8) {
				System.out.println("");
			}
			
		}
		
		return breadthFirstTraversalList.toArray(new Integer[breadthFirstTraversalList.size()]);
//		return breadthFirstTraversalList.toArray(new BinaryTreeNode[breadthFirstTraversalList.size()]);
	}
	/**
	 * BreadthFirst用，向右找同一階層的節點
	 * @param upCount 往上走了幾次
	 * @param baseNode 基準節點
	 * @return 正確的節點
	 */
	public BinaryTreeNode breadthFirstFindNextNodeAtSameLayer(int upCount,BinaryTreeNode baseNode){
		//根節點
		if(baseNode.getParentNodeUUID() == null){
			return null;
		}
		//找出這個節點是在左邊還是右邊
		Direction direction = baseNode.getDirection();
		
		//往上走
		baseNode = getBinaryTreeNode(baseNode.getParentNodeUUID());
		upCount += 1;
		
		//左邊，要往右邊
		if(direction == Direction.LEFT){
			BinaryTreeNode checkNode = getBinaryTreeNode(baseNode.getRightChildNodeUUID());
			upCount -= 1;
			
			//到達目標節點
			if(upCount == 0 && checkNode != null){
				return checkNode;
			}
			//往上走
			else if(checkNode == null){
				return breadthFirstFindNextNodeAtSameLayer(upCount += 1,baseNode);
			}
			//往下走
			else{
				BinaryTreeNode tempNode = breadthFirstDigDown(null,upCount,null,checkNode);
				
				if(tempNode == null){
					return breadthFirstFindNextNodeAtSameLayer(upCount,baseNode);
				}
				else{
					return tempNode;
				}
			}
		}
		//右邊，要往上走
		else{
			return breadthFirstFindNextNodeAtSameLayer(upCount,baseNode);
		}
	}
	/**
	 * BreadthFirst用，往下探可用的節點
	 * @param tempNode 暫存節點，遞迴使用
	 * @param upCount 離目標階層多遠的距離，0的話表示在目標階層
	 * @param direction 從上往下走的方向
	 * @param startNode 開始節點，用來判斷是否回到起點，如果是，要往上再找其他節點
	 * @return 目標節點
	 */
	public BinaryTreeNode breadthFirstDigDown(BinaryTreeNode tempNode,int upCount,Direction direction,BinaryTreeNode startNode){
		//回到起點且下面能走的路都走了
		if(tempNode == startNode && direction == Direction.UP){
			return null;
		}
		
		//往下走
		//第一次進來
		if(direction == null){
			tempNode = getBinaryTreeNode(startNode.getLeftChildNodeUUID());
			
			if(tempNode == null){
				return breadthFirstDigDown(startNode,upCount,Direction.RIGHT,startNode);
			}
			else if(upCount == 1){
				return tempNode;
			}
			//繼續往下
			else{
				return breadthFirstDigDown(tempNode,upCount -= 1,Direction.LEFT,startNode);
			}
		}
		else if(direction == Direction.LEFT){
			BinaryTreeNode temp2Node = getBinaryTreeNode(tempNode.getLeftChildNodeUUID());
			
			if(temp2Node == null){
				return breadthFirstDigDown(tempNode,upCount,Direction.RIGHT,startNode);
			}
			else if(upCount == 1){
				return temp2Node;
			}
			//繼續往下
			else{
				return breadthFirstDigDown(temp2Node,upCount -= 1,Direction.LEFT,startNode);
			}
		}
		else if(direction == Direction.RIGHT){
			BinaryTreeNode temp2Node = getBinaryTreeNode(tempNode.getRightChildNodeUUID());
			
			if(temp2Node == null){
				return breadthFirstDigDown(tempNode,upCount,Direction.UP,startNode);
			}
			else if(upCount == 1){
				return temp2Node;
			}
			//繼續往下
			else{
				return breadthFirstDigDown(temp2Node,upCount -= 1,Direction.LEFT,startNode);
			}
		}
		//向上走
		else{
			//判斷是從左邊或右邊上來
			if(tempNode.getDirection() == Direction.LEFT){
				tempNode = getBinaryTreeNode(tempNode.getParentNodeUUID());
				
				return breadthFirstDigDown(tempNode,upCount += 1,Direction.RIGHT,startNode);
			}
			else{
				tempNode = getBinaryTreeNode(tempNode.getParentNodeUUID());
				
				return breadthFirstDigDown(tempNode,upCount += 1,Direction.UP,startNode);
			}
		}
	}
	/**
	 * BreadthFirst用，往下往左找，新的階層開始塞值時會用到
	 * @param tempNode 暫存的節點
	 * @param targetCount 要向下多少節點才會到達目標節點，目標值為0
	 * @param direction 用來判斷從哪邊的子節點上來
	 * @return 目標節點
	 */
	public BinaryTreeNode breadthFirstFindNextNodeAtNewLayer(BinaryTreeNode tempNode,int targetCount,Direction direction){
		//回到根節點而且是從右邊的子節點上來的
		if(tempNode.getParentNodeUUID() == null && direction == Direction.RIGHT){
			return null;
		}
		
		//到達目標節點的階層
		if(targetCount == 0){
			return tempNode;
		}
		//尚未到達目標節點的階層，還要向下走
		else if(direction == null){
			//左邊子節點不存在
			if(tempNode.getLeftChildNodeUUID() == null){
				//右邊子節點不存在
				if(tempNode.getRightChildNodeUUID() == null){
					//往上走，找另一個
					return breadthFirstFindNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getParentNodeUUID()),targetCount += 1,tempNode.getDirection());
				}
				//右邊子節點存在
				else{
					//向下走
					return breadthFirstFindNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getRightChildNodeUUID()),targetCount -= 1,null);
				}
			}
			//左邊子節點存在
			else{
				//向下走
				return breadthFirstFindNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getLeftChildNodeUUID()),targetCount -= 1,null);
			}
		}
		//從左邊上來
		else if(direction == Direction.LEFT){
			//右邊子節點不存在
			if(tempNode.getRightChildNodeUUID() == null){
				//TODO 這邊額外判斷，如果沒有右邊子節點且本身是根節點，就結束
				if(tempNode.getParentNodeUUID() == null){
					return null;
				}
				
				//往上走，找另一個
				return breadthFirstFindNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getParentNodeUUID()),targetCount += 1,tempNode.getDirection());
			}
			//右邊子節點存在
			else{
				//向下走
				return breadthFirstFindNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getRightChildNodeUUID()),targetCount -= 1,null);
			}
		}
		//從右邊上來
		else{
			//往上走，找另一個
			return breadthFirstFindNextNodeAtNewLayer(getBinaryTreeNode(tempNode.getParentNodeUUID()),targetCount += 1,tempNode.getDirection());
		}
	}
}
