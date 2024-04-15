package kuku;

import java.io.Serializable;

import lombok.Data;

@Data
public class BinaryTreeNode implements Serializable{
	private static final long serialVersionUID = -4366432501176037243L;
	
	/**
	 * 初始化先建UUID
	 */
	public BinaryTreeNode(){
		setUUID(java.util.UUID.randomUUID().toString());
	}
	
	//UUID
	private String UUID;
	//母節點UUID
	private String parentNodeUUID;
	//節點的數值
	private Integer value;
	//左邊子節點UUID
	private String leftChildNodeUUID;
	//右邊子節點UUID
	private String rightChildNodeUUID;
	//因為數值可能會有-1，如果是-1的情況，此節點雖然會有但是不能使用，用此屬性判斷
	private boolean canUse;
	//是母節點的左邊還是右邊的子節點
	private Direction direction;
	//LEFT和RIGHT是用來判斷母節點的子節點方向或是節點在移動的指示；UP是節點在移動的指示
	public enum Direction{
	    LEFT,RIGHT,UP
	}
}
