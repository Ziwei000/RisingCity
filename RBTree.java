
public class RBTree<T extends Comparable<T>> {

    private RBTNode<T> myRoot;    //root

    private static final boolean RED   = false;
    private static final boolean BLACK = true;
    private int counter=0;

    public class RBTNode<T extends Comparable<T>> {
        boolean color;	//red or black
        T bnum;	// building number
        int etime;	//executed_time
        int ttime;	//total_time
        RBTNode<T> left;    //left_child
        RBTNode<T> right;   //right_child
        RBTNode<T> parent;	
        
        public RBTNode(T bnum,int etime,int ttime, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right) {
            this.bnum = bnum;
            this.etime = etime;
            this.ttime = ttime;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() {
            return bnum;
        }

        public String toString() {
            return ""+bnum+etime+ttime+(this.color==RED?"R":"B");
        }
        public String toString1() {
            return "("+bnum+","+etime+","+ttime+")";
        }
    }

    public RBTree() {
        myRoot=null;
    }

    private RBTNode<T> parentOf(RBTNode<T> node) {
        return node!=null ? node.parent : null;
    }
    private boolean colorOf(RBTNode<T> node) {
        return node!=null ? node.color : BLACK;
    }
    private boolean isRed(RBTNode<T> node) {
        return ((node!=null)&&(node.color==RED)) ? true : false;
    }
    private boolean isBlack(RBTNode<T> node) {
        return !isRed(node);
    }
    private void setBlack(RBTNode<T> node) {
        if (node!=null)
            node.color = BLACK;
    }
    private void setRed(RBTNode<T> node) {
        if (node!=null)
            node.color = RED;
    }
    private void setParent(RBTNode<T> node, RBTNode<T> parent) {
        if (node!=null)
            node.parent = parent;
    }
    private void setColor(RBTNode<T> node, boolean color) {
        if (node!=null)
            node.color = color;
    }
    
    //In-order traversal of RBT
    private void inOrder(RBTNode<T> tree) {
        if(tree != null) {
            inOrder(tree.left);
            //"("+tree.bnum+","+tree.etime+","+tree.ttime+")"
            System.out.println(tree.toString1());
            inOrder(tree.right);
        }
    }
    public void inOrder() {
        inOrder(myRoot);
    }

    //(Recursive implementation) Find nodes with "key" value in RBTx
    private RBTNode<T> search(RBTNode<T> x, T bnum) {
        if (x==null)
            return x;
        int cmp = bnum.compareTo(x.bnum);
        if (cmp < 0)
            return search(x.left, bnum);
        else if (cmp > 0)
            return search(x.right, bnum);
        else
            return x;
    }
    
    public RBTNode<T> search(T bnum) {
        return search(myRoot, bnum);
    }
    
    private void update_etime(RBTNode<T> node,int etime) {
    	if(node!=null)
    		node.etime=etime;
    }
    public void update_time(T bnum,int etime) {
    	update_etime(search(myRoot,bnum),etime);
    }
        
    public void printNode(T bnum) {
    	RBTNode<T> node; 
    	if ((node = search(myRoot, bnum)) != null) {
//    		System.out.printf("(%2d,%2d,%2d)\n", node.bnum,node.etime,node.ttime);
    		System.out.println(node.toString1()); 
    	}else {
    		System.out.println("(0,0,0)");
    	}
    }   
    
    //Recursively print node between bnum1 and bnum2
    private int printNodeBetween(RBTNode<T> tree,T bnum1,T bnum2) {  
            if(tree != null) {
            printNodeBetween(tree.left,bnum1,bnum2);
            int cmp1 = tree.bnum.compareTo(bnum1);
            int cmp2 = tree.bnum.compareTo(bnum2);
            if (cmp1>=0 && cmp2<=0) {//bnum>=bnum1&&bnum<=bnum2
            	System.out.print(tree.toString1());   
            	counter++;//record if there is any node printed
            }
            printNodeBetween(tree.right,bnum1,bnum2);
        } 
            return counter;
    }

    public void printNodeBetween(T bnum1,T bnum2) {
    	counter=0;//init counter as 0 then to count how many node will be print between bnum1 and bnum2
    	if(printNodeBetween(myRoot,bnum1,bnum2)!=0) {
    		System.out.print('\n');
    	}else//none node between bnum1 and bnum2 
    		System.out.println("(0,0,0)");
    }
    
    //Returns the smallest node of a red-black tree whose tree is the root node.
    private RBTNode<T> minimum(RBTNode<T> tree) {
        if (tree == null)
            return null;

        while(tree.left != null)
            tree = tree.left;
        return tree;
    }

    public T minimum() {
        RBTNode<T> p = minimum(myRoot);
        if (p != null)
            return p.bnum;
        return null;
    }
     
    //Returns the largest node of a RBT whose tree is the root node.
    private RBTNode<T> maximum(RBTNode<T> tree) {
        if (tree == null)
            return null;
        while(tree.right != null)
            tree = tree.right;
        return tree;
    }

    public T maximum() {
        RBTNode<T> p = maximum(myRoot);
        if (p != null)
            return p.bnum;
        return null;
    }

    //find the smallest node which is larger than x in RBT
    public RBTNode<T> successor(RBTNode<T> x) {
        // if x has right child, then x's successor is the smallest node in x's right subtree
        if (x.right != null)
            return minimum(x.right);

        //if x does not have right child, two situations
        //(1) x is a left child, then x's successor is its parent
        //(2) x is a right child, then find x's lowest parent which must have a left child, then this lowest parent is x's successor
        RBTNode<T> y = x.parent;
        while ((y!=null) && (x==y.right)) {
            x = y;
            y = y.parent;
        }
        return y;
    }
     
     //find the largest node which is smaller than x in RBT
    public RBTNode<T> predecessor(RBTNode<T> x) {
    	//if x has left child, then x's pre is the largest node in its left subtree
        if (x.left != null)
            return maximum(x.left);

        //if x doed not have left child, two situations
        //(1)x is a right child, then x's pre is its parent
        //(2)x is a left child, then find x's lowest parent which must have a right child, then this lowest parent is x's pre
        RBTNode<T> y = x.parent;
        while ((y!=null) && (x==y.left)) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /* 
     *      px                              px
     *     /                               /
     *    x                               y                
     *   /  \      --(left rotate)-.     / \                
     *  lx   y                          x  ry     
     *     /   \                       /  \
     *    ly   ry                     lx  ly  
     */
    private void leftRotate(RBTNode<T> x) {
        // Set the right child of x to y
        RBTNode<T> y = x.right;

        // Set "left child of y" to "right child of x"
        //If left child of y is not empty, set "x" to "parent of left child of y"
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        //Set "parent of x" to "parent of y"
        y.parent = x.parent;

        if (x.parent == null) {
            this.myRoot = y;            //If "x's parent" is an empty node, then set y as the root node
        } else {
            if (x.parent.left == x)
                x.parent.left = y;    //If x is the left child of its parent, then set y to "the left child of x's parent"
            else
                x.parent.right = y;    //If x is the left child of its parent, then set y to "the left child of x's parent"
        }
        
        //Set x to "left child of y"
        y.left = x;
        //Set "parent of x" to y
        x.parent = y;
    }

    /* 
     *            py                               py
     *           /                                /
     *          y                                x                  
     *         /  \      --(right rotate)-.     /  \                     
     *        x   ry                           lx   y 
     *       / \                                   / \                   
     *      lx  rx                                rx  ry
     * 
     */
    private void rightRotate(RBTNode<T> y) {
        //Set x to be the left child of the current node.
        RBTNode<T> x = y.left;

        //Set "right child of x" to "left child of y";
        //If "right child of x" is not empty, set "y" to "parent of right child of x"
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        // Set "parent of y" to "parent of x"
        x.parent = y.parent;

        if (y.parent == null) {
            this.myRoot = x;//If "y's parent" is an empty node, then set x as the root node
        } else {
            if (y == y.parent.right)
                y.parent.right = x;//If y is the right child of its parent, then set x to "the right child of y's parent"
            else
                y.parent.left = x; //(y is the left child of its parent) Set x to "the left child of x's parent"
        }

        //Set y to "right child of x"
        x.right = y;

        // Set "y's parent node" to x
        y.parent = x;
    }

    //After inserting nodes into the RBT(out of balance), call this function
    private void insertFixUp(RBTNode<T> node) {
        RBTNode<T> parent, gparent;

        //if parent exist and is Red
        while (((parent = parentOf(node))!=null) && isRed(parent)) {
            gparent = parentOf(parent);

            //if parent is grandparent's left child
            if (parent == gparent.left) {
                // Case 1:uncle is red
                RBTNode<T> uncle = gparent.right;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }
                // Case 2:uncle is black, and "node" is right child
                if (parent.right == node) {
                    RBTNode<T> tmp;
                    leftRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3:uncle is black, and "node" is left child
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {    //if parent is grandparent's right child
                // Case 1：uncle is red
                RBTNode<T> uncle = gparent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2:uncle is black, and "node" is left child
                if (parent.left == node) {
                    RBTNode<T> tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3:uncle is black, and "node" is right child
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }
        setBlack(this.myRoot);
    }

    private void insert(RBTNode<T> node) {
        int cmp;
        RBTNode<T> y = null;
        RBTNode<T> x = this.myRoot;

        //1.Consider RBT as a binary search tree and add nodes to the binary search tree
        while (x != null) {
            y = x;
            cmp = node.bnum.compareTo(x.bnum);
            if (cmp < 0)
                x = x.left;
            else
                x = x.right;
        }
        node.parent = y;
        if (y!=null) {
            cmp = node.bnum.compareTo(y.bnum);
            if (cmp < 0)
                y.left = node;
            else
                y.right = node;
        } else {
            this.myRoot = node;
        }

        // 2.set node as red
        node.color = RED;

        //3.fix the balance
        insertFixUp(node);
    }

    public void insert(T bnum,int etime,int ttime) {
        RBTNode<T> node=new RBTNode<T>(bnum,etime,ttime,BLACK,null,null,null);
        if (node != null)
            insert(node);
    }
    
     //Fix up RBT after remove a node
     //After deleting node from RBT(RBT is out of balance), call this function to fix   
    private void removeFixUp(RBTNode<T> node, RBTNode<T> parent) {
        RBTNode<T> other;

        while ((node==null || isBlack(node)) && (node != this.myRoot)) {
            if (parent.left == node) {
                other = parent.right;
                if (isRed(other)) {
                    // Case 1: x's sibling w is Red 
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                if ((other.left==null || isBlack(other.left)) &&
                    (other.right==null || isBlack(other.right))) {
                    // Case 2: x's sibling w is Black,and w's two children are B 
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.right==null || isBlack(other.right)) {
                        // Case 3: x's sibling w is Black,and w's left child is R, right child is black
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }
                    // Case 4:x's sibling w is Black,and w's right child is red, left child can be any color
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.myRoot;
                    break;
                }
            } else {
                other = parent.left;
                if (isRed(other)) {
                    // Case 1:x's sibling w is R
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }
                if ((other.left==null || isBlack(other.left)) &&
                    (other.right==null || isBlack(other.right))) {
                    // Case 2: x's sibling w is Black,and w's two children are B   
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (other.left==null || isBlack(other.left)) {
                        // Case 3: x's sibling w is Black,and w's left child is R, right child is black 
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    // Case 4: x's sibling w is Black,and w's right child is red, left child can be any color
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.myRoot;
                    break;
                }
            }
        }
        if (node!=null)
            setBlack(node);
    }

    //Delete (node) and return the deleted node
    private void remove(RBTNode<T> node) {
        RBTNode<T> child, parent;
        boolean color;

        //case where "left and right children are not empty" of the deleted node.
        if ( (node.left!=null) && (node.right!=null) ) {
            //The successor node of the deleted node. (Called "replace node")
            //Replace the "deleted node" position, and then remove the "deleted node".
            RBTNode<T> replace = node;

            //Get successor nodes
            replace = replace.right;
            while (replace.left != null)
                replace = replace.left;

            //"node" is not the root node (as only the root node does not have a parent node)
            if (parentOf(node)!=null) {
                if (parentOf(node).left == node)
                    parentOf(node).left = replace;
                else
                    parentOf(node).right = replace;
            } else {
                //node is root, update root
                this.myRoot = replace;
            }

            //child is the right child of the "replace node", as well as the "adjustable node".
            //"Replace node" does not exist left child. as it's a successor node.
            child = replace.right;
            parent = parentOf(replace);
            //save the color of "replace node"
            color = colorOf(replace);

            //"deleted node" is the "suc of its parent node"
            if (parent == node) {
                parent = replace;
            } else {
                if (child!=null)
                    setParent(child, parent);
                parent.left = child;

                replace.right = node.right;
                setParent(node.right, replace);
            }

            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if (color == BLACK)
                removeFixUp(child, parent);
            node = null;
            return ;
        }

        if (node.left !=null) {
            child = node.left;
        } else {
            child = node.right;
        }

        parent = node.parent;
        // save the color of replace node
        color = node.color;

        if (child!=null)
            child.parent = parent;

        //node is not root
        if (parent!=null) {
            if (parent.left == node)
                parent.left = child;
            else
                parent.right = child;
        } else {
            this.myRoot = child;
        }

        if (color == BLACK)
            removeFixUp(child, parent);
        node = null;
    }

    //Delete node (z) and return the deleted node
    public void remove(T bnum) {
        RBTNode<T> node; 

        if ((node = search(myRoot, bnum)) != null)
            remove(node);
    }

    private void destroy(RBTNode<T> tree) {
        if (tree==null)
            return ;
        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);
        tree=null;
    }

    public void clear() {
        destroy(myRoot);
        myRoot = null;
    }

}
