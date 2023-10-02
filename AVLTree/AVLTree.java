/**    name    -    ID
 * alaa haddad - 211706460
 *  siba azab  - 206770281
 */

import java.util.*;

/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	private IAVLNode root;
	private IAVLNode min;
	private IAVLNode max;

	/** public AVLTree ()
	 *  constructs an empty AVL tree.
	 *  **/
	public AVLTree () {
		this.root = new AVLNode();
		this.max = root;
		this.min = root;
	}

	/**
	 * public AVLTree (IAVLNode root)
	 *  constructs an AVL tree by pointing to a root.
	 *  **/
	public AVLTree (IAVLNode root) {
		this.root = root;
		this.min = new AVLNode();
		this.max = new AVLNode();
	}

	/**O(1)
	 * public boolean empty()
	 *
	 * Returns true if and only if the tree is empty.
	 *
	 */
	public boolean empty() {
		return size()==0;
	}


	/**O(log n)
	 * public String search(int k)
	 *
	 * Returns the info of an item with key k if it exists in the tree.
	 * otherwise, returns null.
	 */
	public String search(int k) {
		IAVLNode node = this.root;
		if (this.root == null || !this.root.isRealNode()) {
			return null;
		}
		while (node.getKey() != -1) {
			if (node.getKey() == k) {
				return node.getValue();
			}
			if (node.getKey() < k) {
				node = node.getRight();
			}
			if (node.getKey() > k) {
				node = node.getLeft();
			}
		}
		return null;
	}

	/** O(log n)
	 * public IAVLNode search_key(int k)
	 * returns the AVL node of an item with key == k in AVL tree.
	 * if tree is empty returns null
	 */
	public IAVLNode search_key(int k) {
		IAVLNode node = this.root;
		if (empty() || !root.isRealNode()) {
			return null;
		}
		while (node.getKey() != -1) {
			if (node.getKey() == k) {
				return node;
			}
			if (node.getKey() < k) {
				node = node.getRight();
			}
			if (node.getKey() > k) {
				node = node.getLeft();
			}
		}
		return null;
	}

	/**O(log n)
	 * public IAVLNode position(int k)
	 * promotes all nodes's sizes between this.root and @returned.
	 * returns the node that would be the parent of an AVL node with key == k.
	 * returns the node if an AVL node with key == k already exists.
	 */
	public IAVLNode position(int k) {
		IAVLNode node = this.root;
		while (node.isRealNode()) {
			if(node.getKey() == k) {
				return node;
			}
			if (node.getKey() > k) {
				node.setSize(node.getSize()+1);
				node = node.getLeft();
			}
			else {
				node.setSize(node.getSize()+1);
				node = node.getRight();
			}
		}
		return node.getParent();
	}

	/**O(1)
	 * public void right_rotation(IAVLNode node)
	 * performs a right rotation on edge(node,node.left).
	 */
	public void right_rotation(IAVLNode node) {
		IAVLNode left = node.getLeft();
		IAVLNode left_right = node.getLeft().getRight();
		if (node.getParent() != null && node.getParent().isRealNode()) {
			if (node.getParent().getLeft() == (node)) {
				node.getParent().setLeft(left);
			}
			else if(node.getParent().getRight() == (node)) {
				node.getParent().setRight(left);
			}
		}
		left.setParent(node.getParent());
		node.setParent(left);
		left_right.setParent(node);
		node.setLeft(left_right);
		left.setRight(node);
		node.setHeight(1+Math.max(node.getLeft().getHeight(),node.getRight().getHeight()));
		left.setHeight(1+Math.max(left.getLeft().getHeight(),left.getRight().getHeight()));
		if(node == getRoot()) {
			this.root = node.getParent();
			this.root.setParent(null);
		}
		node.setSize(node.getLeft().getSize()+node.getRight().getSize()+1);
		node.getParent().setSize(node.getParent().getLeft().getSize()+node.getParent().getRight().getSize()+1);
	}

	/**O(1)
	 * public void left_rotation(IAVLNode node)
	 * performs left rotation on edge(node,node.right)
	 */
	public void left_rotation(IAVLNode node) {
		IAVLNode right = node.getRight();
		IAVLNode right_left = node.getRight().getLeft();
		if (node.getParent() != null && node.getParent().isRealNode()) {
			if (node.getParent().getLeft()==(node)) {
				node.getParent().setLeft(right);
			}
			else if(node.getParent().getRight()==(node)) {
				node.getParent().setRight(right);
			}
		}
		right.setParent(node.getParent());
		node.setParent(right);
		node.setRight(right_left);
		right.setLeft(node);
		right_left.setParent(node);
		node.setHeight(1+Math.max(node.getLeft().getHeight(),node.getRight().getHeight()));
		right.setHeight(1+Math.max(right.getLeft().getHeight(),right.getRight().getHeight()));
		if(node == getRoot()) {
			this.root = node.getParent();
			this.root.setParent(null);
		}
		node.setSize(node.getLeft().getSize()+node.getRight().getSize()+1);
		node.getParent().setSize(node.getParent().getLeft().getSize()+node.getParent().getRight().getSize()+1);
	}

	/**O(log n)
	 * private IAVLNode predeccessor(IAVLNode node)
	 * returns the predeccessor of an AVL node in an AVL tree.
	 * predeccessor: the node with a maximum key that is smaller than node.key
	 */
	private IAVLNode predeccessor(IAVLNode node){
		IAVLNode pre = node;
		if(node.getLeft().isRealNode()) {
			pre = node.getLeft();
			if(!pre.getRight().isRealNode()) {
				return pre;
			} else {
				while(pre.getRight().isRealNode()) {
					pre = pre.getRight();
				}
				return pre;
			}
		} else {
			while(pre.getParent().isRealNode() && pre.getParent().getRight() != pre) {
				pre = pre.getParent();
			}
			return pre.getParent();
		}
	}

	/**O(log n)
	 *  private IAVLNode successor(IAVLNode node)
	 *  returns the successor of an AVL node in an AVL tree.
	 *  successor: the node with a minimum key that is greater than node.key.
	 */
	private IAVLNode successor(IAVLNode node,boolean deleted){
		IAVLNode suc = node;
		if(node.getRight().isRealNode()){
			if(deleted) {
				suc.setSize(suc.getSize()-1);
			}
			suc = node.getRight();
			if (!suc.getLeft().isRealNode()) {
				return suc;
			} else{
				while(suc.getLeft().isRealNode()){
					suc.setSize(suc.getSize()-1);
					suc = suc.getLeft();
				}
				return suc;
			}
		} else {
			while (suc.getParent().isRealNode() && suc.getParent().getLeft() != suc) {
				suc = suc.getParent();
			}
			return suc.getParent();
		}
	}

	/**O(log n)
	 * performs a series of rebalancing actions to make the tree a true AVL tree.
	 * returns the number of rebalancing actions made.
	 */

	public int rebalance(IAVLNode node,boolean inserted) {
		int balance = 0;
		if (inserted) {
			if(node.getParent() == null) {
				return 0;
			}
			IAVLNode parent = node.getParent();
			IAVLNode left = node.getLeft();
			IAVLNode right = node.getRight();
			// 0-1
			if(parent.getHeight() - parent.getLeft().getHeight() == 0 && parent.getHeight() - parent.getRight().getHeight() == 1) {
				parent.setHeight(parent.getHeight() + 1);
				return 1 + balance + rebalance(parent, inserted);
			}
			// 1-0
			if(parent.getHeight() - parent.getLeft().getHeight() == 1 && parent.getHeight() - parent.getRight().getHeight() == 0) {
				parent.setHeight(parent.getHeight() + 1);
				return 1 + balance + rebalance(parent,inserted);
			}
			// 0-2
			if(parent.getHeight() - parent.getLeft().getHeight() == 0 && parent.getHeight() - parent.getRight().getHeight() == 2) {
				if(node.getHeight() - left.getHeight() == 1 && node.getHeight() - right.getHeight() == 2) {
					right_rotation(parent);
					return 2 + balance;
				} else if (node.getHeight() - left.getHeight() == 2 && node.getHeight() - right.getHeight() == 1) {
					left_rotation(node);
					right_rotation(node.getParent().getParent());
					return 5+balance;
				} else if(node.getHeight()- left.getHeight() == 1 && node.getHeight()- right.getHeight() == 1) {
					right_rotation(parent);
					return 2+balance;
				}
			}
			// 2-0
			if(parent.getHeight() - parent.getLeft().getHeight() == 2 && parent.getHeight() - parent.getRight().getHeight() == 0) {
				if(node.getHeight() - left.getHeight() == 1 && node.getHeight() - right.getHeight() == 2) {
					right_rotation(node);
					left_rotation(node.getParent().getParent());
					return 5 + balance;
				} else if(node.getHeight() - left.getHeight() == 2 && node.getHeight() - right.getHeight() == 1) {
					left_rotation(parent);
					return 2+balance;
				} else if(node.getHeight()- left.getHeight() == 1 && node.getHeight()- right.getHeight() == 1) {
					left_rotation(parent);
					return 2+balance;
				}
			}
		} else {
			if(node == null) {
				return 0;
			}
			//2-2
			if(node.getHeight() - node.getRight().getHeight() == 2 && node.getHeight() - node.getLeft().getHeight() == 2){
				node.setHeight(node.getHeight()-1);
				node = node.getParent();
				return 1+balance+rebalance(node,inserted);
			}
			//1-3
			if(node.getHeight()-node.getRight().getHeight() == 3){
				IAVLNode left_left = node.getLeft().getLeft();
				IAVLNode left_right = node.getLeft().getRight();
				IAVLNode left = node.getLeft();
				//1-1
				if(left.getHeight() - left_right.getHeight() == 1 && left.getHeight() - left_left.getHeight() == 1){
					right_rotation(node);
					return 3+balance;
				}
				//2-1
				if(left.getHeight() - left_right.getHeight() == 1 && node.getLeft().getHeight() - left_left.getHeight() ==2){
					left_rotation(left);
					right_rotation(node);
					return 6+balance+rebalance(node.getParent(),inserted);
				}
				//1-2
				if(node.getLeft().getHeight()- left_left.getHeight() == 1 && node.getLeft().getHeight() - left_right.getHeight() == 2){
					right_rotation(node);
					return 3+balance+rebalance(node.getParent(),inserted);
				}
				//3-1
			} else if(node.getHeight() - node.getLeft().getHeight() == 3){
				IAVLNode right_left = node.getRight().getLeft();
				IAVLNode right = node.getRight();
				IAVLNode right_right = node.getRight().getRight();
				//1-1
				if(right.getHeight() - right_left.getHeight() == 1 && right.getHeight() - right_right.getHeight() == 1){
					left_rotation(node);
					return 3+balance;
				}
				//2-1
				if(right.getHeight() - right_left.getHeight() == 2 && right.getHeight() - right_right.getHeight() == 1){
					left_rotation(node);
					return 3+balance+rebalance(node.getParent(),inserted);
				}
				//1-2
				if(right.getHeight()- right_left.getHeight() == 1 && right.getHeight()- right_right.getHeight() == 2){
					right_rotation(right);
					left_rotation(node);
					return 6+balance+rebalance(node.getParent(),inserted);
				}
			}
		}
		return balance;
	}
	/**O(log n)
	 * public int insert(int k, String i)
	 *
	 * Inserts an item with key k and info i to the AVL tree.
	 * The tree must remain valid, i.e. keep its invariants.
	 * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	 * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	 * Returns -1 if an item with key k already exists in the tree.
	 */
	public int insert(int k, String i) {
		if (search(k) != null) {
			return -1;
		}
		if (empty()) {
			IAVLNode root = new AVLNode(k,i,null);
			IAVLNode child1 = new AVLNode();
			IAVLNode child2 = new AVLNode();
			child1.setParent(root);
			child2.setParent(root);
			root.setLeft(child1);
			root.setRight(child2);
			root.setHeight(0);
			this.root = root;
			this.min = root;
			this.max = root;
			this.root.setSize(1);
			return 0;
		}
		IAVLNode new_node = new AVLNode(k,i,new AVLNode());
		IAVLNode child1 = new AVLNode();
		IAVLNode child2 = new AVLNode();
		new_node.setLeft(child1);
		new_node.setRight(child2);
		child1.setParent(new_node);
		child2.setParent(new_node);
		new_node.setHeight(0);
		if (k<min.getKey()) {
			min = new_node;
		}
		if (k>max.getKey()) {
			max = new_node;
		}
		if(size()==1) {
			IAVLNode root = this.root;
			if (k < root.getKey()) {
				root.setLeft(new_node);
			} else {
				root.setRight(new_node);
			}
			root.setHeight(1);
			root.setSize(2);
			new_node.setSize(1);
			new_node.setParent(root);
			this.root = root;
			return 1;
		}
		IAVLNode parent = position(k);
		new_node.setParent(parent);
		if(k<parent.getKey()) {
			parent.setLeft(new_node);
		} else {
			parent.setRight(new_node);
		}
		new_node.setParent(parent);
		new_node.setSize(1);
		return rebalance(new_node,true);
	}

	/**O(log n)
	 * public int delete(int k)
	 *
	 * Deletes an item with key k from the binary tree, if it is there.
	 * The tree must remain valid, i.e. keep its invariants.
	 * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
	 * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
	 * Returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) {
		IAVLNode node1 = search_key(k);
		if (node1 == null || empty()) {
			return -1;
		}
		this.root.setSize(this.root.getSize()-1);
		if(k == this.min.getKey()) {
			this.min = successor(this.min,false);
		}
		if(k == this.max.getKey()) {
			this.max = predeccessor(this.max);
		}
		IAVLNode right = node1.getRight();
		IAVLNode left = node1.getLeft();
		boolean right_child = false;
		IAVLNode parent = node1.getParent();
		if(parent!=null)
			if(node1 != this.root) {
				if (parent.getRight() == node1) {
					right_child = true;
				}
				if (node1.getHeight() == 0) {
					if (right_child && !parent.getLeft().isRealNode()) {
						node1.setParent(null);
						parent.setRight(new AVLNode());
						parent.setHeight(parent.getHeight() - 1);
						return 1+ rebalance(parent.getParent(), false);
					}
					if (!right_child && !parent.getRight().isRealNode()) {
						node1.setParent(null);
						parent.setLeft(new AVLNode());
						parent.setHeight(parent.getHeight() - 1);
						return 1+ rebalance(parent.getParent(), false);
					}
					if (parent.getRight().isRealNode() && parent.getLeft().isRealNode()) {
						if (right_child) {
							node1.setParent(null);
							parent.setRight(new AVLNode(-1,null,parent));
							return rebalance(parent, false);
						} else {
							node1.setParent(null);
							parent.setLeft(new AVLNode(-1,null,parent));
							return rebalance(parent, false);
						}
					}
				} else if (node1.getLeft().isRealNode() && !node1.getRight().isRealNode()) {
					if (right_child) {
						node1.setParent(null);
						parent.setRight(left);
						left.setParent(parent);
						return rebalance(parent, false);
					} else {
						node1.setParent(null);
						parent.setLeft(left);
						left.setParent(parent);
						return rebalance(parent, false);
					}
				}
				if (!node1.getLeft().isRealNode() && node1.getRight().isRealNode()) {
					if (right_child) {
						node1.setParent(null);
						parent.setRight(right);
						right.setParent(parent);
						return rebalance(parent, false);
					} else {
						node1.setParent(null);
						parent.setLeft(right);
						right.setParent(parent);
						return rebalance(parent, false);
					}
				}
			}
		IAVLNode suc = successor(node1,true);
		IAVLNode node_right = node1.getRight();
		IAVLNode node_left = node1.getLeft();
		IAVLNode suc_right = suc.getRight();
		IAVLNode suc_parent = suc.getParent();
		suc.setLeft(node_left);
		node_left.setParent(suc);
		IAVLNode rebalanced;
		if (node1.getRight() == suc) {
			if (suc.getRight().isRealNode()) {
				node_right.setParent(parent);
			}
		} else {
			if (!suc_right.isRealNode()) {
				suc_parent.setLeft(new AVLNode(-1,null,suc.getParent()));
			} else {
				suc_parent.setLeft(suc_right);
				suc_right.setParent(suc_parent);
			}
			suc.setRight(node_right);
			node_right.setParent(suc);
		}
		if(node1 != this.root) {
			suc.setParent(parent);
			if (right_child) {
				parent.setRight(suc);
			} else {
				parent.setLeft(suc);
			}
		} else {
			suc.setParent(null);
			node_right.setParent(suc);
			this.root = suc;
		}
		suc.setHeight(Math.max(suc.getLeft().getHeight(), suc.getRight().getHeight()) + 1);
		if(suc.getHeight()> suc_parent.getHeight()) {
			rebalanced = suc_parent;
		} else {
			rebalanced = suc;
		}
		return rebalance(rebalanced,false);
	}

	/**O(1)
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty.
	 */
	public String min()
	{
		if (empty())
			return null;
		return this.min.getValue();
	}

	/**O(1)
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty.
	 */
	public String max()
	{
		if(empty())
			return null;
		return this.max.getValue();
	}

	/**O(n)
	 * private int inOrder(IAVLNode root, int[] l, int index)
	 * helping function to return a sorted array which contains all keys in the tree.
	 */
	private int inOrder(IAVLNode root, int[] l, int index){
		if (!root.isRealNode())
			return index;
		else{
			index=inOrder(root.getLeft(),l,index);
			l[index] = root.getKey();
			index++;
			index=inOrder(root.getRight(),l,index);
		}
		return index;
	}

	/**O(n)
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree,
	 * or an empty array if the tree is empty.
	 */
	public int[] keysToArray()
	{
		int[] keys = new int[size()];
		inOrder(this.root,keys,0);
		return keys;
	}

	/**O(n)
	 *private int inOrderInfo(IAVLNode root,String[] l, int index)
	 * a helping function to return an array which contains all infos of nodes in the tree.
	 */
	private int inOrderInfo(IAVLNode root,String[] l, int index) {
		if (!root.isRealNode()) {
			return index;
		} else {
			index = inOrderInfo(root.getLeft(), l, index);
			l[index] = root.getValue();
			index++;
			index = inOrderInfo(root.getRight(), l, index);
		}
		return index;
	}
	/**O(n)
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree,
	 * sorted by their respective keys,
	 * or an empty array if the tree is empty.
	 */
	public String[] infoToArray()
	{
		String[] values = new String[this.root.getSize()];
		inOrderInfo(this.root, values,0);
		return values;
	}

	/**O(1)
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 */
	public int size()
	{
		return this.root.getSize();
	}

	/**O(1)
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 */
	public IAVLNode getRoot()
	{
		if(empty())
			return null;
		return this.root;
	}

	/**O(log n)
	 * public AVLTree[] split(int x)
	 *
	 * splits the tree into 2 trees according to the key x.
	 * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	 *
	 * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
	 * postcondition: none
	 */
	public AVLTree[] split(int x) {
		IAVLNode node = search_key(x);
		if(node == this.root) {
			AVLTree smaller = new AVLTree(node.getLeft());
			AVLTree bigger = new AVLTree(node.getRight());
			AVLTree[] result = new AVLTree[2];
			result[0] = smaller;
			result[1] = bigger;
			smaller.min = this.min;
			bigger.max = this.max;
			IAVLNode a = smaller.root;
			IAVLNode b = bigger.root;
			while(a.getRight().isRealNode()) {
				a = a.getRight();
			}
			smaller.max = a;
			while(b.getLeft().isRealNode()) {
				b = b.getLeft();
			}
			bigger.min = b;
			return result;
		}
		AVLTree bigger = new AVLTree();
		AVLTree smaller = new AVLTree();
		if(node.isRealNode()) {
			bigger.root = node.getRight();
			smaller.root = node.getLeft();
		}
		if(node.getParent()!=null) {
			IAVLNode parent = node.getParent();
			while (parent != null) {
				IAVLNode left = parent.getLeft();
				IAVLNode right = parent.getRight();
				if (node == right) {
					IAVLNode temp = new AVLNode(parent.getKey(), parent.getValue(), null);
					temp.setHeight(parent.getHeight());
					smaller.join(temp, new AVLTree(left));
				} else {
					IAVLNode temp = new AVLNode(parent.getKey(), parent.getValue(), null);
					temp.setHeight(parent.getHeight());
					bigger.join(temp, new AVLTree(right));
				}
				node = parent;
				parent = parent.getParent();
			}
		}
		AVLTree tree1 = new AVLTree();
		AVLTree tree2 = new AVLTree();
		tree1.root.setHeight(0);
		tree2.root.setHeight(0);
		AVLTree[] result = new AVLTree[2];
		if(smaller.empty() && bigger.empty()) {
			result[0] = tree1;
			result[1] = tree2;
			return result;
		}
		if(smaller.empty()) {
			result[0] = tree1;
		}
		if(bigger.empty()) {
			result[1] = tree1;
		}
		IAVLNode c = smaller.root;
		IAVLNode c1 = smaller.root;
		IAVLNode d = bigger.root;
		IAVLNode d1 = bigger.root;
		while(c.getRight().isRealNode()) {
			c = c.getRight();
		}
		while(c1.getLeft().isRealNode()) {
			c1 = c1.getLeft();
		}
		smaller.max = c;
		smaller.min = c1;
		while(d.getLeft().isRealNode()) {
			d = d.getLeft();
		}
		while(d1.getRight().isRealNode()) {
			d1 = d1.getRight();
		}
		bigger.max = d1;
		bigger.min = d;
		result[0] = smaller;
		result[1] = bigger;
		return result;
	}

	/**O(log n)
	 * public int join(IAVLNode x, AVLTree t)
	 *
	 * joins t and x with the tree.
	 * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	 *
	 * precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
	 * postcondition: none
	 */
	public int join(IAVLNode x, AVLTree t) {
		int result = Math.abs(this.root.getHeight()-t.root.getHeight())+1;
		if(this.empty() && t.empty()) {
			return 1;
		}
		if(this.empty()) {
			t.insert(x.getKey(),x.getValue());
			this.root = t.root;
			return t.root.getHeight();
		}
		if(t.root.getHeight() == this.root.getHeight()) {
			if(this.root.getKey()>x.getKey()) {
				x.setRight(this.root);
				x.setLeft(t.root);
			} else{
				x.setRight(t.root);
				x.setLeft(this.root);
			}
			x.setHeight(Math.max(x.getLeft().getHeight(),x.getRight().getHeight())+1);
			x.setSize(x.getLeft().getSize()+x.getRight().getSize()+1);
			x.getRight().setParent(x);
			x.getLeft().setParent(x);
			this.root = x;
			return 1;
		}
		if(t.root.getHeight()>this.root.getHeight()) {
			IAVLNode node = t.root;
			while(node.getHeight()>this.root.getHeight()) {
				if(this.root.getKey()>t.root.getKey()) {
					node = node.getRight();
				}
				if(this.root.getKey()<t.root.getKey()) {
					node = node.getLeft();
				}
			}
			IAVLNode parent = node.getParent();
			if(this.root.getKey()>t.root.getKey()) {
				this.max = this.max;
				this.min = t.min;
				x.setRight(this.root);
				x.setLeft(node);
				parent.setRight(x);
			} else {
				this.min = this.min;
				this.max = t.max;
				x.setLeft(this.root);
				x.setRight(node);
				parent.setLeft(x);
			}
			this.root.setParent(x);
			node.setParent(x);
			x.setParent(parent);
			x.setHeight(1+Math.max(x.getLeft().getHeight(),x.getRight().getHeight()));
			this.root = t.root;
		} else if(t.root.getHeight()<this.root.getHeight()) {
			return t.join(x,this);
		}
		IAVLNode node1 = x;
		x.setSize(x.getLeft().getSize()+x.getRight().getSize()+1);
		while(x.getParent() != null && x.getParent().isRealNode()) {
			x = x.getParent();
			x.setSize(x.getLeft().getSize()+x.getRight().getSize()+1);
		}
		rebalance(node1,true);
		return result;
	}

	/**
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
		public void setHeight(int height); // Sets the height of the node.
		public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
		public int getSize();
		public void setSize(int size);
	}

	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree
	 * (for example AVLNode), do it in this file, not in another file.
	 *
	 * This class can and MUST be modified (It must implement IAVLNode).
	 */
	public static class AVLNode implements IAVLNode{
		private int key;
		private String info;
		private int rank;
		private IAVLNode left;
		private IAVLNode right;
		private IAVLNode parent;
		private int size;

		/** public AVLNode()
		 * constructs a vertual node.
		 * vertual node: a node with key = -1, height = -1, info = null, with null as parent and children.
		 */
		public AVLNode(){
			this.size = 0;
			this.key= -1;
			this.info=null;
			this.rank=-1;
			this.left=null;
			this.right=null;
			this.parent=null;
		}

		/** public AVLNode(int key,String info,IAVLNode parent)
		 *constructs an AVL node with key as key, info as info, parent as parent.
		 */
		public AVLNode(int key,String info,IAVLNode parent){
			size = 1;
			this.key=key;
			this.info=info;
			this.rank=0;
			this.left=new AVLNode();
			this.right=new AVLNode();
			this.parent=parent;
			if(key == -1) {
				this.rank = -1;
			}
		}

		/**O(1)
		 *public int getSize()
		 * returns size of this node.
		 * size: number of cildren.
		 */
		public int getSize() {return this.size;}

		/** O(1)
		 * public void setSize(int size)
		 *sets size of this node to size
		 */
		public void setSize(int size) {this.size = size;}

		/**O(1)
		 *  public int getKey()
		 *returns the key of this node.
		 */
		public int getKey() {return this.key;}

		/** O(1)
		 * public String getValue()
		 *returns info of this node
		 */
		public String getValue() {return this.info;}

		/**O(1)
		 * public void setLeft(IAVLNode node)
		 * sets node as left child of this node
		 */
		public void setLeft(IAVLNode node) {this.left=node;}

		/**O(1)
		 * public IAVLNode getLeft()
		 *returns left child of this node
		 */
		public IAVLNode getLeft() {return this.left;}

		/**O(1)
		 * public void setRight(IAVLNode node)
		 * sets node as right child of this node
		 */
		public void setRight(IAVLNode node) {this.right=node;}

		/**O(1)
		 * public IAVLNode getRight()
		 * returns the right child of this node
		 */
		public IAVLNode getRight() {return this.right;}

		/**O(1)
		 * public void setParent(IAVLNode node)
		 * sets node as parent of this node
		 */
		public void setParent(IAVLNode node) {this.parent = node;}

		/**O(1)
		 * public IAVLNode getParent()
		 * returns the parent of this node
		 */
		public IAVLNode getParent() {return this.parent;}

		/**O(1)
		 * public boolean isRealNode()
		 * checks if this node is a vertual node or real node.
		 * returns false if this node is vertual.
		 * returns true if this node is real.
		 */
		public boolean isRealNode() {
			if(this == null) {
				return false;
			}
			return this.key != -1;
		}

		/**O(1)
		 * public void setHeight(int height)
		 * sets the height/rank of this node to height.
		 */
		public void setHeight(int height) {this.rank = height;}

		/**O(1)
		 * public int getHeight()
		 * returns the height/rank of this node.
		 */
		public int getHeight() {return this.rank;}
	}
}
  
