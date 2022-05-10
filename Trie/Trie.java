public class Trie{
	
	TrieNode root;
	public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	int size;
	
	public Trie(String []words){
		
		root = new TrieNode();
		size = 0;
		for(int i=0; i<words.length; i++){
			if(words[i]!=null){
				add(words[i]);
			}
		}
		
	}
	
	boolean add(String word){
		
		TrieNode head = root;
		
		if(contains(word)){
			return false;
		}
		size++;
		
		for(int i=0; i <word.length(); i++){
			
			char ch = word.charAt(i);
			int pos = alphabet.indexOf(ch);
			
			TrieNode newNode;
			
			if(head.getChildren(pos) == null){
				if(i == word.length()-1)
					newNode = new TrieNode(true, head);
				else
					newNode = new TrieNode(false, head);
				
				head.setChildren(newNode, pos);
				head = head.getChildren(pos);
			}
			else{
				if(i == word.length()-1){
					if(head.getChildren(pos).isTerminal){
						return false;
					}
					else{
						head.getChildren(pos).setIsTerminal();
						return true;
					}
				}
				head = head.getChildren(pos);
			}
		}
		return true;
	}
	
	boolean contains(String word){
		
		TrieNode head = root;
		
		for(int i=0; i<word.length(); i++){
			char ch = word.charAt(i);
			int pos = alphabet.indexOf(ch);
			head = head.getChildren(pos);
			if(head == null){
				return false;
			}
		}
		
		if(!head.isTerminal){
			return false;
		}
		
		return true;
	}
	
	int size(){return size;}
	
	char getLetter(int pos){ 
		char ch = alphabet.charAt(pos);
		return ch; 
	}
	
	String[] differByOne(String word){
		
		String []differ = differBy(word, 1);
		
		return differ;
	}
	
	String[] differBy(String word, int max){//differ[1000]
		
		char []str = new char[word.length()];
		String []differ = new String[1000];
		TrieNode head=root;
		TrieNode cur;
		int differCount=0;
		int letter=0;
		int i=0;
		int j=0;
		int jPos=0;
		int s=0;
		boolean in=true;
		char ch='a';
		int pos=0;
		
		while(i<26){
			if(root.getChildren(i)==null){
				i++;
				j=i;
			}
			else{
				while(letter<word.length() && in ){
					while(j<26){
						if(head.getChildren(j)==null){
							j++;
						}
						else{
							head = head.getChildren(j);
							
							ch = word.charAt(letter);
							pos = alphabet.indexOf(ch);
							str[letter]=getLetter(j);
							letter++;
							
							if(differCount==max){
								if(j!=pos){
									in=false;
									break;
								}
							}
							else{
								if(j!=pos){
									differCount++;
								}
							}
							
							//System.out.println(getLetter(j) + "  Differ= " + differCount + "  Letter= " + letter);
							
							if(letter==word.length() || (head.isLeaf() && letter<word.length())){
								break;
							}
							
							j=0;
						}
					}
					if(letter==word.length() || (head.isLeaf() && letter<word.length())){
						break;
					}
				}
				
				if(!in){
					in=true;
					cur=head;
					head=head.getParent();
					letter--;
					if(head.hasRightBrothers(j+1)){
						j++;
						while(true){
							if(head.getChildren(j)!=null){
								break;
							}
							else{
								j++;
							}
						}
					}
					else{
						cur=head;
						head=head.getParent();
						j=head.thisChildren(cur);
						letter--;
						ch = word.charAt(letter);
						pos = alphabet.indexOf(ch);
						if(j!=pos && differCount!=0){
							differCount--;
						}
						
						while(true){
							if(head==root && !head.hasRightBrothers(head.thisChildren(cur)+1)){return differ;}
							
							if(head.hasRightBrothers(j+1)){
								j++;
								while(true){
									if(head.getChildren(j)!=null){
										break;
									}
									else{
										j++;
									}
								}
								break;
							}
							else{
								cur=head;
								head=head.getParent();
								if(head==root && !head.hasRightBrothers(j)){return differ;}
								j=head.thisChildren(cur);
								letter--;
								j=head.thisChildren(cur);
								ch = word.charAt(letter);
								pos = alphabet.indexOf(ch);
								if(j!=pos && differCount!=0){
									differCount--;
								}
							}
						}
					}
				}
				else if(!head.isTerminal || (head.isLeaf() && letter<word.length())){
					if(j!=pos && differCount!=0){
						differCount--;
					}
					
					cur=head;
					head=head.getParent();
					letter--;
					if(head.hasRightBrothers(j+1)){
						j++;
						while(true){
							if(head.getChildren(j)!=null){
								break;
							}
							else{
								j++;
							}
						}
					}
					else{
						cur=head;
						head=head.getParent();
						j=head.thisChildren(cur);
						letter--;
						ch = word.charAt(letter);
						pos = alphabet.indexOf(ch);
						if(j!=pos && differCount!=0){
							differCount--;
						}
						
						while(true){
							if(head==root && !head.hasRightBrothers(head.thisChildren(cur)+1)){return differ;}
							
							if(head.hasRightBrothers(j+1)){
								j++;
								while(true){
									if(head.getChildren(j)!=null){
										break;
									}
									else{
										j++;
									}
								}
								break;
							}
							else{
								cur=head;
								head=head.getParent();
								if(head==root && !head.hasRightBrothers(j)){return differ;}
								j=head.thisChildren(cur);
								letter--;
								j=head.thisChildren(cur);
								ch = word.charAt(letter);
								pos = alphabet.indexOf(ch);
								if(j!=pos && differCount!=0){
									differCount--;
								}
							}
						}
					}
				}
				else{
					if(j!=pos && differCount!=0){
						differCount--;
					}
					
					String string = new String(str);
					differ[s] = string;
					s++;
					
					cur=head;
					head=head.getParent();
					letter--;
					if(head.hasRightBrothers(j+1)){
						j++;
						while(true){
							if(head.getChildren(j)!=null){
								break;
							}
							else{
								j++;
							}
						}
					}
					else{
						cur=head;
						head=head.getParent();
						j=head.thisChildren(cur);
						letter--;
						ch = word.charAt(letter);
						pos = alphabet.indexOf(ch);
						if(j!=pos && differCount!=0){
							differCount--;
						}
						
						while(true){
							if(head==root && !head.hasRightBrothers(head.thisChildren(cur)+1)){return differ;}
							
							if(head.hasRightBrothers(j+1)){
								j++;
								while(true){
									if(head.getChildren(j)!=null){
										break;
									}
									else{
										j++;
									}
								}
								break;
							}
							else{
								cur=head;
								head=head.getParent();
								if(head==root && !head.hasRightBrothers(j)){return differ;}
								j=head.thisChildren(cur);
								letter--;
								j=head.thisChildren(cur);
								ch = word.charAt(letter);
								pos = alphabet.indexOf(ch);
								if(j!=pos && differCount!=0){
									differCount--;
								}
							}
						}
					}
				}
			}
		}
		
		return differ;
	}
	
	public String toString(){
		
		String preOrder = new String();
		TrieNode head=root;
		TrieNode cur;
		int node=0;
		int i=0;
		int j=0;
		
		while(i<26){
			if(root.getChildren(i)==null){
				i++;
				j=i;
			}
			else{
				while(!head.isLeaf()){
					while(j<26){
						if(head.getChildren(j)==null){
							j++;
						}
						else{
							head=head.getChildren(j);
							preOrder += " " + getLetter(j);
							if(head.isTerminal){
								preOrder += "!";
							}
							
							if(head.isLeaf()){
								break;
							}
							j=0;
						}
					}
				}
				if(head.isLeaf()){
					head=head.getParent();
					j++;
					while(true){
						if(head==root && !head.hasRightBrothers(j)){
							return preOrder;
						}
						if(j>=26){
							cur=head;
							head=head.getParent();
							
							j=head.thisChildren(cur);
							j++;
							
						}
						if(j<26){
							if(head.getChildren(j)!=null){
								break;
							}
							else{
								j++;
							}
						}
					}
				}
			}
		}
		
		return preOrder;
	}
	
	String toDotString(){
		
		TrieNode head=root;
		TrieNode cur;
		int node=0;
		int i=0;
		int j=0;
		String dotStr= "graph Trie{\n" + root.getKey() + " [label=\"ROOT\", shape=circle, color=black]\n";
		
		
		while(i<26){
			if(root.getChildren(i)==null){
				i++;
				j=i;
			}
			else{
				while(!head.isLeaf()){
					while(j<26){
						if(head.getChildren(j)==null){
							j++;
						}
						else{
							dotStr= dotStr + head.getKey() + " -- ";
							head=head.getChildren(j);
							
							dotStr= dotStr + head.getKey() + "\n" + head.getKey() + " [label=\"" + getLetter(j) + "\", shape=circle, color=";
							
							node++;
							if(!head.isTerminal){
								dotStr= dotStr + "black]\n";
							}
							else{
								dotStr= dotStr + "red]\n";
							}
							
							if(head.isLeaf()){
								break;
							}
							j=0;
						}
					}
				}
				if(head.isLeaf()){
					head=head.getParent();
					j++;
					while(true){
						if(head==root && !head.hasRightBrothers(j)){
							dotStr= dotStr + "}";
							return dotStr;
						}
						if(j>=26){
							cur=head;
							head=head.getParent();
							
							j=head.thisChildren(cur);
							j++;
							
						}
						if(j<26){
							if(head.getChildren(j)!=null){
								break;
							}
							else{
								j++;
							}
						}
					}
				}
			}
		}
		
		dotStr= dotStr + "}";
		
		return dotStr;
	}
	
	String[] wordsOfPrefix(String prefix){//prefixWords[1000]
	
		String str = new String();
		String []prefixWords = new String[1000];
		TrieNode head=root;
		TrieNode headPrefix;
		TrieNode cur;
		int i;
		int j=0;
		int s=0;
		
		for(i=0; i<prefix.length(); i++){
			char ch = prefix.charAt(i);
			int pos = alphabet.indexOf(ch);
			if(head.getChildren(pos) == null){
				return null;
			}
			else{
				head=head.getChildren(pos);
			}
		}
		
		if(head.isTerminal){
			prefixWords[s] = prefix;
			s++;
		}
		
		str = prefix;
		headPrefix = head;
		i=0;
		
		while(i<26){
			if(headPrefix.getChildren(i)==null){
				i++;
				j=i;
			}
			else{
				while(true){
					while(j<26){
						if(head.getChildren(j)==null){
							j++;
						}
						else{
							head=head.getChildren(j);
							str += getLetter(j);
							
							if(head.isTerminal){
								prefixWords[s] = str;
								s++;
							}
							
							if(head.isLeaf()){
								break;
							}
							j=0;
						}
					}
					if(head.isLeaf()){
						break;
					}
				}
				
				if(head.isLeaf()){
					head=head.getParent();
					str = str.substring(0, str.length()-1);
					j++;
					while(true){
						if(head==headPrefix && !head.hasRightBrothers(j)){
							return prefixWords;
						}
						if(j>=26){
							cur=head;
							head=head.getParent();
							str = str.substring(0, str.length()-1);
							j=head.thisChildren(cur);
							j++;
						}
						if(j<26){
							if(head.getChildren(j)!=null){
								break;
							}
							else{
								j++;
							}
						}
					}
				}
			}
		}
		return prefixWords;
	}
}
