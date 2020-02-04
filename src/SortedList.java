
public class SortedList < T > {
  public class Node {
	  public Node next;
	  public Node previous;
	  public double val;
	  public T object;
	  
	  Node(T obj, double val, Node next, Node previous) {
		  object = obj;
		  this.val = val;
		  this.next = next;
		  this.previous = previous;
	  }
  }
  
  private Node head = null;
  private Node tail = null;
  private int size = 0;
  public int getSize() { return size;}
  public boolean isEmpty(){
	  return head == null;
  }
  public Node getFirstNode() {return head;}
  
  public T peekFirst(){
	  if (head == null) return null;
	  return head.object;
  }
  public T getFirst(){
	  if (head == null) return null;
	  size--;
	  if (head == tail){
		  Node node = head;
		  head = null;
		  tail = null;
		  return node.object;
	  }
	  Node node = head;
	  head = head.next;
	  head.previous = null;
	  
	  return node.object;
  }
  public T getLast(){
	  if (head == null) return null;
	  size--;
	  if (head == tail) {
		  Node node = head;
		  head = null;
		  tail = null;
		  return node.object;
	  }
	  Node node = tail;
	  tail = tail.previous;
	  tail.next = null;
	  
	  return node.object;
  }
  public void add(T obj, double val){
	  size++;
	  if (head == null){
		  head = new Node(obj, val, null, null);
		  tail = head;
		  return;
	  }
	  if (head.val <= val){
		  Node node = new Node(obj, val, head, null);
		  head.previous = node;
		  head = node;
	  } else {
	      Node previous = head;
	      Node it = head.next;
	      if (it == null){
	    	  head.next = tail = new Node(obj, val, null, head);
	      } else {
	          while(it != null){
		          if (it.val <= val){
		        	  previous.next = new Node(obj, val, it, previous);
		        	  it.previous = previous.next;
		        	  return;
		          }
		          previous = it;
		          it = it.next;
	          }
	          Node node = new Node(obj, val, null, tail);
	          tail.next = node;
	          tail = node;
	      }
	  }
  }
  
  @Override
  public String toString(){
	  String out = "";
	  Node it = head;
	  while(it != null){
		  out += "-" + it.val + ", " + it + "-";
		  it = it.next;
	  }
	  out += " head = " + head + ", tail " + tail;
	  return out;
  }
  
}
