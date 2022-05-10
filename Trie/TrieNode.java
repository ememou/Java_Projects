public class TrieNode {
	
	TrieNode parent;
	TrieNode children [];
	boolean isTerminal;
	
	public TrieNode () {
		children = new TrieNode[26];
		for(int i=0; i<26; i++){
			children[i]=null;
		}
	}
	
	
	public TrieNode (boolean isTerminal, TrieNode parent) {
		this.isTerminal = isTerminal;
		children = new TrieNode[26];
		this.parent = parent;
	}
	
	public boolean isLeaf(){
		if(isTerminal){
			for(int i=0; i<26; i++){
				if(children[i]!=null){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean hasRightBrothers(int pos){
		for(int i=pos; i<26; i++){
			if(children[i]!=null){
				return true;
			}
		}
		return false;
	}
	
	/*public TrieNode getRightBrother(int pos){
		for(int i=pos; i<26; i++){
			if(children[i]!=null){
				return children[i];
			}
		}
		return null;
	}*/
	
	public TrieNode getChildren(int pos) { return children[pos]; }
	public void setChildren(TrieNode newChildren, int pos) { children[pos] = newChildren; }
	public TrieNode getParent() { return parent; }
	
	public int thisChildren(TrieNode head) { 
		for(int i=0; i<26; i++){
			if(children[i]==head){
				return i;
			}
		}
		return -1; 
	}
	
	public void setIsTerminal() { isTerminal=true; }
	public int getKey() { return hashCode(); }
}
