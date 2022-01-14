
import java.util.ArrayList;
import java.util.List;

public class WordSearcher {
	
	private  List<String> lines;
	private String wordsearch;

	public WordSearcher(List<String> lines, String wordsearch) {
		super();
		
		
			setLines(lines);
			setWordsearch(wordsearch);
			}
 		
	public List<String> getLines() {
		return lines;
	}


	public void setLines(List<String> lines) {
		this.lines = lines;
	}


	public String getWordsearch() {
		return wordsearch;
	}


	public void setWordsearch(String wordsearch) {
		this.wordsearch = wordsearch;
	}

public  List<Integer> Search(List<String> lines, String word ){
		
	
	
	List<Integer> returnList = new ArrayList<Integer>();
	
	word = word.toUpperCase(); 
	
			for(Integer i = 0; i < lines.size(); ++i) { 
			  String str = lines.get(i); 
			  if(str.indexOf(word) >= 0) 
			   returnList.add(i); 
			  }

			
		return returnList;
	}
}

