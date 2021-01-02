import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	private class Node {
		public Character value;
		public Node left;
		public Node right;
	}

	Node root = new Node();

	public static void main(String[] args) {
		Main main = new Main();
		main.processFile();
		main.findOldest();
	}

	private void processFile() {
		try (Scanner scan = new Scanner(new File("drzewo.txt"))) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine().toLowerCase();
				Character value = line.charAt(line.length() - 1);
				String path = "";
				if (line.length() > 2)
					path = line.substring(0, line.length() - 2);
				setNodeValue(root, path, value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setNodeValue(Node node, String path, Character value) {
		if (path.length() == 0)
			node.value = value;
		else if (path.charAt(0) == 'l') {
			if (node.left == null)
				node.left = new Node();
			setNodeValue(node.left, path.substring(1), value);
		} else {
			if (node.right == null)
				node.right = new Node();
			setNodeValue(node.right, path.substring(1), value);
		}
	}

	private void findOldest() {
		Set<String> words = new TreeSet<String>();
		bulidWord(root, words, new StringBuilder());
//		System.out.println(words);
        System.out.println(words.toArray()[words.size()-1]);
	}

	private void bulidWord(Node node, Set<String> words, StringBuilder word) {
		word.append(node.value);
		if (node.left == null && node.right == null) {
			words.add(new StringBuilder(word).reverse().toString());
		} else {
			if (node.left != null)
				bulidWord(node.left, words, word);
			if (node.right != null)
				bulidWord(node.right, words, word);
		}
		word.deleteCharAt(word.length() - 1);
	}
}