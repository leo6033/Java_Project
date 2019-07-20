package 哈夫曼;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCompress {
	private PriorityQueue<HufTree> queue = null;

	public void compress(File inputFile, File outputFile) {
		Compare cmp = new Compare();
		queue = new PriorityQueue<HufTree>(12, cmp);

		// 映射字节及其对应的哈夫曼编码
		HashMap<Byte, String> map = new HashMap<Byte, String>();

		int i, char_kinds = 0;
		int char_tmp, file_len = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		DataOutputStream oos = null;

		HufTree root = new HufTree();
		String code_buf = null;

		// 临时储存字符频度的数组
		TmpNode[] tmp_nodes = new TmpNode[256];

		for (i = 0; i < 256; i++) {
			tmp_nodes[i] = new TmpNode();
			tmp_nodes[i].weight = 0;
			tmp_nodes[i].Byte = (byte) i;
		}

		try {
			fis = new FileInputStream(inputFile);
			fos = new FileOutputStream(outputFile);
			oos = new DataOutputStream(fos);

			/*
			 * 统计字符频度，计算文件长度
			 */
			while ((char_tmp = fis.read()) != -1) {
				tmp_nodes[char_tmp].weight++;
				file_len++;
			}
			fis.close();
			// 排序，将频度为0的字节放在最后，同时计算除字节的种类，即有多少个不同的字节
			Arrays.sort(tmp_nodes);
			for (i = 0; i < 256; i++) {
				if (tmp_nodes[i].weight == 0) {
					break;
				}
				HufTree tmp = new HufTree();
				tmp.Byte = tmp_nodes[i].Byte;
				tmp.weight = tmp_nodes[i].weight;
				queue.add(tmp);
			}
			char_kinds = i;

			if (char_kinds == 1) {
				oos.writeInt(char_kinds);
				oos.writeByte(tmp_nodes[0].Byte);
				oos.writeInt(tmp_nodes[0].weight);
			} else {
				// 建树
				createTree(queue);
				root = queue.peek();
				// 生成哈夫曼编码
				hufCode(root, "", map);
				// 写入字节种类
				oos.writeInt(char_kinds);
				for (i = 0; i < char_kinds; i++) {
					oos.writeByte(tmp_nodes[i].Byte);
					oos.writeInt(tmp_nodes[i].weight);
				}
				oos.writeInt(file_len);
				fis = new FileInputStream(inputFile);
				code_buf = "";
				while ((char_tmp = fis.read()) != -1) {
					code_buf += map.get((byte) char_tmp);
					while (code_buf.length() >= 8) {
						char_tmp = 0;
						for (i = 0; i < 8; i++) {
							char_tmp <<= 1;
							if (code_buf.charAt(i) == '1')
								char_tmp |= 1;
						}
						oos.writeByte((byte) char_tmp);
						code_buf = code_buf.substring(8);
					}
				}
				// 最后编码长度不够8位的时候，用0补齐
				if (code_buf.length() > 0) {
					char_tmp = 0;
					for (i = 0; i < code_buf.length(); ++i) {
						char_tmp <<= 1;
						if (code_buf.charAt(i) == '1')
							char_tmp |= 1;
					}
					char_tmp <<= (8 - code_buf.length());
					oos.writeByte((byte) char_tmp);
				}
				oos.close();
				fis.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void extract(File inputFile, File outputFile) {
		Compare cmp = new Compare();
		queue = new PriorityQueue<HufTree>(12, cmp);

		int i;
		int file_len = 0;
		int writen_len = 0;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		DataInputStream ois = null;

		int char_kinds = 0;
		HufTree root=new HufTree();
		byte code_tmp;
		try {
			fis = new FileInputStream(inputFile);
			ois = new DataInputStream(fis);
			fos = new FileOutputStream(outputFile);

			char_kinds = ois.readInt();
			// 字节只有一种
			if (char_kinds == 1) {
				code_tmp = ois.readByte();
				file_len = ois.readInt();
				while ((file_len--) != 0) {
					fos.write(code_tmp);
				}
			} else {
				for (i = 0; i < char_kinds; i++) {
					HufTree tmp = new HufTree();
					tmp.Byte = ois.readByte();
					tmp.weight = ois.readInt();
					System.out.println("Byte: "+tmp.Byte+" weight: "+tmp.weight);
					queue.add(tmp);
				}

				createTree(queue);

				file_len = ois.readInt();
				root = queue.peek();
				while (true) {
					code_tmp = ois.readByte();
					for (i = 0; i < 8; i++) {
						if ((code_tmp&128)==128) {
							root = root.rchild;
						} else {
							root = root.lchild;
						}
						if (root.lchild == null && root.rchild == null) {
							fos.write(root.Byte);
							++writen_len;
							if (writen_len == file_len)
								break;
							root = queue.peek();
						}
						code_tmp <<= 1;
					}
					if (writen_len == file_len)
						break;
				}
			}
			fis.close();
			fos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createTree(PriorityQueue<HufTree> queue) {
		while (queue.size() > 1) {
			HufTree min1 = queue.poll();
			HufTree min2 = queue.poll();
			System.out.print(min1.weight + " " + min2.weight + " ");

			HufTree NodeParent = new HufTree();
			NodeParent.weight = min1.weight + min2.weight;
			NodeParent.lchild = min1;
			NodeParent.rchild = min2;

			queue.add(NodeParent);
		}
	}

	public void hufCode(HufTree root, String s, HashMap<Byte, String> map) {
		if (root.lchild == null && root.rchild == null) {
			root.code = s;
			System.out.println("节点" + root.Byte + "编码" + s);
			map.put(root.Byte, root.code);

			return;
		}
		if (root.lchild != null) {
			hufCode(root.lchild, s + '0', map);
		}
		if (root.rchild != null) {
			hufCode(root.rchild, s + '1', map);
		}

	}

}
