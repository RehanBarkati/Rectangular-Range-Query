import java.util.*;
import java.io.*;

class Pair<A, B> {
    public A First;
    public B Second;

    public Pair(A _first, B _second) {
        this.First = _first;
        this.Second = _second;
    }

    public A get_first() {
        return First;
    }

    public B get_second() {
        return Second;
    }
}

class TreeNode {
    public TreeNode parent;
    public TreeNode left;
    public TreeNode right;
    public int depth;
    public int count;
    public int val;
    public Pair<Integer, Integer> xrange;
    public Pair<Integer, Integer> yrange;
    public int a,b;

}

class kdt {
    TreeNode rootnode;

    public void sortx(ArrayList<Pair<Integer, Integer>> A, int l, int r) {
        
        for (int i = l; i < r; i++) {
            for (int j = l; j < r-i+l; j++) {
                if (A.get(j).First > A.get(j + 1).First) {
                    Collections.swap(A, j, j + 1);
                }
            }
        }

    }

    public void sorty(ArrayList<Pair<Integer, Integer>> A, int l, int r) {
        
       
       
        
        for (int i = l; i < r; i++) {
            for (int j = l; j < r -i+l; j++) {
                if (A.get(j).Second > A.get(j + 1).Second) {
                    Collections.swap(A, j, j + 1);
                }
            }
        }
        
    }

    public void build(ArrayList<Pair<Integer,Integer>>B,int l,int r,TreeNode p){
            if(this.rootnode==null){
                sortx(B, l, r);
                int m=(l+r)/2;
                p.depth=0;
                p.count=r-l+1;
                p.parent=null;
                p.val=B.get(m).First;
                p.xrange= new Pair<Integer,Integer>(Integer.MIN_VALUE,Integer.MAX_VALUE);
                p.yrange= new Pair<Integer,Integer>(Integer.MIN_VALUE,Integer.MAX_VALUE);
                this.rootnode=p;
                TreeNode lt=new TreeNode();
                TreeNode rt=new TreeNode();
                p.left=lt;
                lt.parent=p;
                p.right=rt;
                rt.parent=p;
                
                build(B,l,m,lt);  
                build(B,m+1,r,rt);
            }
            else if(r>=l){

                p.depth=1+p.parent.depth;
                p.count=r-l+1;
                int m=(l+r)/2;

                if(p.depth %2!=0){
                    sorty(B,l,r);
                    p.yrange=p.parent.yrange;
                    p.val=B.get(m).Second;
                
               
                   if(p.parent.left==p){
                p.xrange= new Pair<Integer,Integer>(Math.max(Integer.MIN_VALUE,p.parent.xrange.First),p.parent.val);
                }
                else{
                    p.xrange=new Pair<Integer,Integer>(p.parent.val+1,Math.min(Integer.MAX_VALUE,p.parent.xrange.Second));
                }
                }

                else{
                   sortx(B,l,r);
                   p.xrange=p.parent.xrange;
                   p.val=B.get(m).First;
                  
        
                if(p.parent.left==p){
                p.yrange=new Pair<Integer,Integer>(Math.max(Integer.MIN_VALUE, p.parent.yrange.First),p.parent.val);
                }
                else{
                p.yrange=new Pair<Integer,Integer>(p.parent.val+1,Math.min(Integer.MAX_VALUE, p.parent.yrange.Second));
                }
               
                }
               
                if(r!=l){
                 TreeNode lt=new TreeNode();
                 TreeNode rt=new TreeNode();
                p.left=lt;
                lt.parent=p;
                p.right=rt;
                rt.parent=p;
                
                build(B,l,m,lt);
                
                build(B,m+1,r,rt);
                }
                else{
                    p.a= B.get(r).First;
                    p.b= B.get(r).Second;
                    p.left=p.right=null;
                }
            }
            else{
                if(p.parent.right==p){
                    p.parent.right=null;
                }
                else{
                    p.parent.left=null;
                }
            }
          
            return ;
    }

    public int rangequery(int x,int y,TreeNode nd){
           int ans=0;
           if(nd.left==null || nd.right==null){
               if(Math.abs(x-nd.a)<=100 && Math.abs(y-nd.b)<=100){
                   return 1;
               }
               else{
                   return 0;
               }
           }

           if(nd.left.xrange.Second <x-100 || nd.left.xrange.First>x+100 || nd.left.yrange.Second<y-100 || nd.left.yrange.First>y+100){
                      ans+=0;              
           }
           else if(nd.left.xrange.First >=x-100 && nd.left.xrange.Second<=x+100 && nd.left.yrange.First>=y-100 && nd.left.yrange.Second<=y+100){
                      ans+=nd.left.count;              
           }
           
           else{
               ans+= rangequery(x, y, nd.left);
           }
           if(nd.right.xrange.Second <x-100 || nd.right.xrange.First>x+100 || nd.right.yrange.Second<y-100 || nd.right.yrange.First>y+100){
                      ans+=0;              
           }
           else if(nd.right.xrange.First >=x-100 && nd.right.xrange.Second<=x+100 && nd.right.yrange.First>=y-100 && nd.right.yrange.Second<=y+100){
                      ans+=nd.right.count;              
           }
           
           else{
               ans+= rangequery(x, y, nd.right);
           }
           
   

        return ans;
    }

}

public class kdtree {

    public static void main(String[] args) {

        ArrayList<Pair<Integer, Integer>> arr = new ArrayList<>();

        try {
            FileInputStream f = new FileInputStream("restaurants.txt");
            Scanner s = new Scanner(f);
            s.nextLine();

            while (s.hasNextLine()) {

                String line = s.nextLine();
                String tmp[] = line.split(",");
                int a = Integer.parseInt(tmp[0]);
                int b = Integer.parseInt(tmp[1]);

                arr.add(new Pair<Integer, Integer>(a, b));
            }

            s.close();

        } catch (Exception e) {
            System.out.println("Error  ");
        }

        ArrayList<Integer>rest=new ArrayList<>();
        kdt tree = new kdt();
        TreeNode nd=new TreeNode();
        tree.build(arr, 0, arr.size() - 1, nd);

        
        try{
            FileInputStream f = new FileInputStream("queries.txt");
            Scanner s = new Scanner(f);
            s.nextLine();

            while (s.hasNextLine()) {

                String line = s.nextLine();
                String tmp[] = line.split(",");
                int a = Integer.parseInt(tmp[0]);
                int b = Integer.parseInt(tmp[1]);

                
                int cnt= tree.rangequery(a,b,nd);
                rest.add(cnt);

            }
        }
        catch(Exception e){
            System.out.println("Error!!");
        }
        

        try{
           FileOutputStream out= new FileOutputStream("output.txt",true);
           PrintStream p=new PrintStream("output.txt");
           for(int i=0;i<rest.size();i++){
               p.println(rest.get(i));
           }
        }
        catch(Exception e){
            System.out.println("Error!");
        }

        
       

    }



}