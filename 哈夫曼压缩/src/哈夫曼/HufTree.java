package 哈夫曼;

public class HufTree{
    public byte Byte; //以8位为单元的字节
    public int weight;//该字节在文件中出现的次数
    public String code; //对应的哈夫曼编码
    public HufTree lchild,rchild;

    /**调试的时候添加的**/
    public String toString(){
        return "Byte:" + Byte + ",weight:" + weight + ",code:" + code + ",+ lchild:" + lchild + ",rchild:" + rchild;
    }
}

//统计字符频度的临时节点
class TmpNode implements Comparable<TmpNode>{
    public byte Byte;
    public int weight;

    @Override
    public int compareTo(TmpNode arg0) {
        if(this.weight < arg0.weight)
            return 1;
        else if(this.weight > arg0.weight)
            return -1;
        return 0;
    }
}
