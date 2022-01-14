
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class server {

	public static void main(String[] args) {
		
//-----------------------create server	
		ServerSocket server = null;
		boolean shutdown = false;
		try
		{
			server = new ServerSocket(1236);
			System.out.println("port bound. Accepting Connections");
			
		} catch (IOException e){
			e.printStackTrace();
			System.exit(-1);
		}	
		
		while(!shutdown) {
			
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			try
			{
				client = server.accept();
				input =  client.getInputStream();
				output =  client.getOutputStream();
				
				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);
				
				String clientInput = new String(data, StandardCharsets.UTF_8);
				clientInput.replace("\n", "");
				
				
				
				
//---start of wordsearch-------------------------------------------------------------------------
				
				Path filePath = Paths.get("hamlet.txt");
					String hamlet= filePath.toString();
					Path newFilePath = null;
					
					WordSearcher wordsearcher = null;
					try {
						// clientInput for wordsearcher instantiation
						wordsearcher = new WordSearcher(Files.readAllLines(Paths.get(hamlet)), clientInput);
						wordsearcher.getLines().replaceAll(String::toUpperCase); 
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					List<Integer> returnList = new ArrayList<Integer>(wordsearcher.Search(wordsearcher.getLines(), wordsearcher.getWordsearch()));
					
				//creating new file with the index to pass through outPutStream
					try {
						Files.deleteIfExists(Paths.get("hamlet-index.txt"));
						 newFilePath = Files.createFile(Paths.get("hamlet-index.txt"));
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					String newFile = newFilePath.toString();
					WriteFile(returnList, newFile);
//---end of word search
					
				
				System.out.println("Client said: " + clientInput);
				
				String response = "Your input was [" +  clientInput + "]";
		
// sending file path to client who will automatically open the file and store its content into a list				
				output.write(newFile.length());
				
				output.write(newFile.getBytes());
			
				
				client.close();
				
				
				if (clientInput.equalsIgnoreCase("shutdown"))
				{
					System.out.println("Shutting down...");
				}
				
			} catch (IOException e){
				e.printStackTrace();
				System.exit(-1);
			}
					
		}
		
	}	
	
//write file-------------------------------------------------------------------------------------	
	public static  void WriteFile(List<Integer> data, String newFile)
	{
		
		FileWriter FW = null;
		try {
			FW = new FileWriter(newFile);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		PrintWriter PW = new PrintWriter(FW);
		System.out.println(data);
		PW.println(data);
		PW.close();
			
		}
}
