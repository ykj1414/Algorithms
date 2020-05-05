package Chapter05;

import Chapter01.Bag;
import Chapter01.Stack;
import Chapter04.DiGraph;
import Chapter04.DirectedDFS;
import edu.princeton.cs.algs4.In;

/*正则表达式的原理，使用非确定有限状态机，
以表达式每个字符为节点，构造出一副有向图来表示每个节点的状态转移。
 */
public class NFA {

    private DiGraph g;

    private char[] re;      //用于遍历正则表达式

    private int M;          //状态总数

    public NFA(String regexp){
        re = regexp.toCharArray();
        M = regexp.length();
        g = new DiGraph(M+1);
        Stack<Integer> ops = new Stack<Integer>();

        for(int i = 0;i<M;i++){
            int lp = i;
            if(re[i]=='('||re[i]=='|')
                ops.push(i);
            else if(re[i] == ')'){
                int or = ops.pop();
                if(re[or]=='|'){
                    lp = ops.pop();
                    g.addEdge(lp,or+1);         //左括号链接到|符号下一位，代表可以进行或运算
                    g.addEdge(or,i);                //|符号连接到右括号，代表|符号左侧状态转移的方式
                }
                else
                    lp = or;
            }
            if(i<M-1&&re[i+1]=='*'){
                g.addEdge(lp,i+1);
                g.addEdge(i+1,lp);
            }
            if(re[i]=='(' || re[i]=='*'||re[i]==')')
                g.addEdge(i,i+1);
        }
    }

    public boolean recognize(String txt){
        Bag<Integer> pc = new Bag<Integer>();
        DirectedDFS dfs = new DirectedDFS(g,0);
        for(int v = 0;v<g.V();v++)
            if(dfs.marked(v)) pc.add(v);
        for(int i = 0;i<txt.length();i++){
            Bag<Integer> match = new Bag<Integer>();
            for(int v:pc){
                if(v<M)
                    if(re[v]==txt.charAt(i)||re[v]=='*')
                        match.add(v+1);
            }
            pc = new Bag<Integer>();
            dfs = new DirectedDFS(g,match);
            for(int v = 0;v<g.V();v++)
                if(dfs.marked(v)) pc.add(v);
        }
        for(int v:pc) if(v==M) return true;
        return false;
    }

    public static void main(String[] args){
        NFA nfa = new NFA("(A*B|AC)D");
        System.out.println(nfa.recognize("AAAABD"));
        System.out.println(nfa.recognize("AAAAC"));
        NFA nfa1 = new NFA("(a|(bc)*d)*");
        System.out.println(nfa1.recognize("abcbcd"));
        System.out.println(nfa1.recognize("abcbcbcdaaaabcbcdaaaddd"));
    }
}
