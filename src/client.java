import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class client  {
	
	static String userString;
	
	
 private static void constructGUI() {
		
		// instantiate new Jframe object and related functions		
	JFrame.setDefaultLookAndFeelDecorated(true);
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setTitle("3rd Eye search index");
	frame.setLayout(new GridLayout(2,2));
	frame.add(new JLabel("Type a word or phrase"));
	JTextField textField = new JTextField();
	frame.add(textField);
	
	 			
	// instantiate result label 
		JLabel result = new JLabel("Result: ");

		// Instantiate button. frame button and result, then add action listener with action to button		
			JButton button = new JButton("Search");
			frame.getContentPane().add(button);
				
			frame.getContentPane().add(result);
				
			button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
						
		// Convert textfield to Strings 
			userString = textField.getText().toString();
			
						
			System.out.println("Input a String: ");
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
							
							
			try
				{
						
					
						Socket connection = new Socket("127.0.0.1",1236);
						InputStream input = connection.getInputStream();
						OutputStream output = connection.getOutputStream();
								
						output.write(userString.length());
						output.write(userString.getBytes());
								
						int n = input.read();
						byte[] data = new byte[n];
						input.read(data);	
								
						String serverResponse = new String(data, StandardCharsets.UTF_8);
						Path filePath = Paths.get(serverResponse);
						String file = filePath.toString();
						//read file path the was sent through inputStream sent by the server. Verify by uncommenting line 87 or so	
						List<String> readToList = new ArrayList<String>(ReadFile(file));
								
						result.setText( readToList.get(0) );
						
							//System.out.println("Server said: " + serverResponse);
							if(!connection.isClosed())
								connection.close();
								
							} catch (IOException e1){
								e1.printStackTrace();
							
							}
			
						}
					});
		// More advance frame settings
				
				
				int frameWidth = 250;
				int frameHeight = 250;
				Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
				frame.setBounds((int) screenSize.getWidth() - frameWidth,0, frameWidth, frameHeight);
				frame.setVisible(true);
		}
	


	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {constructGUI();
			
		}
	});

	}
	


//---ReadFile-----------------------------------------------------------------

	public static List<String>  ReadFile(String file){
		
		
		List<String> readlines = null;
		
		
		try {
			readlines = Files.readAllLines(Paths.get(file));
			
		
			System.out.println(readlines);
			
		} 
	catch (IOException e) {

			e.printStackTrace();
		}
		
		return readlines;
		
	}
	
}
