

class SumInLeavesVisitor extends TreeVis {
    private ArrayList<TreeNode> nodes = new ArrayList<>();
    private ArrayList<TreeLeaf> leafs = new ArrayList<>();
    public int getResult() {
      	int result = 0;
          for(int i=0;i<leafs.size();i++){
          result+=leafs.get(i).getValue();
        }
        return result;
    }

    public void visitNode(TreeNode node) {
      	nodes.add(node);
    }

    public void visitLeaf(TreeLeaf leaf) {
      	leafs.add(leaf);
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    private ArrayList<TreeNode> nodes = new ArrayList<>();
    private ArrayList<TreeLeaf> leafs = new ArrayList<>();
    public int getResult() {
      	BigInteger result = new BigInteger("1");
        for(int i=0;i<leafs.size();i++){
          Color e = leafs.get(i).getColor();
          switch(e){
            case RED:
              result = result.multiply(BigInteger.valueOf(leafs.get(i).getValue()));
              break;
            case GREEN:
             continue;
            default:
              continue;
          }
        }
        for(int i=0;i<nodes.size();i++){
          Color e = nodes.get(i).getColor();
          switch(e){
            case RED:
              result = result.multiply(BigInteger.valueOf(nodes.get(i).getValue()));
              break;
            case GREEN:
             continue;
            default:
              continue;
          }
        }
        int modulo = 1000000007;
        BigInteger mod = BigInteger.valueOf(modulo);
        result = result.mod(mod);
        return result.intValue();
    }

    public void visitNode(TreeNode node) {
      	nodes.add(node);
    }

    public void visitLeaf(TreeLeaf leaf) {
      	leafs.add(leaf);
    }
}

class FancyVisitor extends TreeVis {
    private ArrayList<TreeNode> nodes = new ArrayList<>();
    private ArrayList<TreeLeaf> leafs = new ArrayList<>();
    public int getResult() {
      	int result1 = 0;
        int result2 = 0;
          for(int i=0;i<nodes.size();i++){
          if(nodes.get(i).getDepth()%2==0){result1+=nodes.get(i).getValue();}
        }
        for(int i=0;i<leafs.size();i++){
          if(leafs.get(i).getColor()==Color.GREEN){result2+=leafs.get(i).getValue();}
        }

        return Math.abs(result1-result2);
    }

    public void visitNode(TreeNode node) {
    	nodes.add(node);
    }

    public void visitLeaf(TreeLeaf leaf) {
    	leafs.add(leaf);
    }
}

public class Solution {
  
    public static Tree solve() {
        Scanner sc = new Scanner(System.in);
        int counter = sc.nextInt();
      int[] valueArray = new int[counter+1];
      int[] rgArray = new int[counter+1];
      for(int i=1;i<=counter;i++){
        valueArray[i] = sc.nextInt();
      }
      for(int i=1;i<=counter;i++){
        rgArray[i] = sc.nextInt();
        
      }
      Tree[] treeArr = new Tree[counter+1];
      Tree treeRoot = new TreeNode(valueArray[1],Color.values()[rgArray[1]],0);
      treeArr[1] = treeRoot;

      List<Integer> arn = new ArrayList<>();
      List<Integer> arl = new ArrayList<>();
      arn.add(0);
      arl.add(0);
      while(sc.hasNext()){
        int forArn = sc.nextInt();
        int forArl = sc.nextInt();
        arn.add(forArn);
        arl.add(forArl);

      }
      Map<Integer,Integer> map = new HashMap<>();
      for(int i=1;i<arn.size();i++){
        Integer tmparn = arn.get(i);
        Integer tmparl = arl.get(i);
        if(map.containsKey(tmparn)){
          Integer tmpval = map.get(tmparn);
          tmpval++;
          map.put(tmparn,tmpval);
        }else{
          map.put(tmparn,1);
        }
        if(map.containsKey(tmparl)){
          Integer tmpval = map.get(tmparl);
          tmpval++;
          map.put(tmparl,tmpval);
        }else{
          map.put(tmparl,1);
        }
      }
      
      int index = 1;
      int modificationsCounter = 0;
      while(true){
          if((index==counter)&&(modificationsCounter<(counter-1))) index=1;
        else if(modificationsCounter==(counter-1)){break;}
        int tmparn = arn.get(index);
        int tmparl = arl.get(index);
        if(treeArr[tmparn]==null&&treeArr[tmparl]==null)
        {
          index++;
          continue;
        }
        if(treeArr[tmparn]==null){
          Integer nodeOrLeaf = map.get(tmparn);
          Tree depthTree = treeArr[tmparl];
          int depth = depthTree.getDepth()+1;
          if(nodeOrLeaf>1){
            Tree node = new TreeNode(valueArray[tmparn],Color.values()[rgArray[tmparn]],depth);
            treeArr[tmparn] = node;
            modificationsCounter++;
            TreeNode addTree = (TreeNode)(treeArr[tmparl]);
            addTree.addChild(node);
          }else{
            Tree leaf = new TreeLeaf(valueArray[tmparn],Color.values()[rgArray[tmparn]],depth);
            treeArr[tmparn] = leaf;
            modificationsCounter++;
            TreeNode addTree = (TreeNode)(treeArr[tmparl]);
            addTree.addChild(leaf);
          }
        }
        else if(treeArr[tmparl]==null){
          Integer nodeOrLeaf = map.get(tmparl);
          Tree depthTree = treeArr[tmparn];
          int depth = depthTree.getDepth()+1;
          if(nodeOrLeaf>1){
            Tree node = new TreeNode(valueArray[tmparl],Color.values()[rgArray[tmparl]],depth);
            treeArr[tmparl] = node;
            modificationsCounter++;
            TreeNode addTree = (TreeNode)(treeArr[tmparn]);
            addTree.addChild(node);
          }else{
            Tree leaf = new TreeLeaf(valueArray[tmparl],Color.values()[rgArray[tmparl]],depth);
            treeArr[tmparl] = leaf;
            modificationsCounter++;
            TreeNode addTree = (TreeNode)(treeArr[tmparn]);
            addTree.addChild(leaf);
          }
        }
        index++;

      }
    sc.close();
    return treeArr[1];
    }

