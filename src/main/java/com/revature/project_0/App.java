package com.revature.project_0;

import java.io.IOException;
import java.util.Scanner;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.view.ConsoleView;

public class App 
{
    public static void main( String[] args )
    {
    	Scanner scanner = null;
        try {
        	scanner = new Scanner(System.in);
			new ConsoleView(connectToRepository(), scanner).goLive();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Repository is down!");
		} finally {
			scanner.close();
			try {
				closeRepository();
			} catch (IOException ignore) {
			}
		}
    }
    
    private static Repository connectToRepository() throws Exception {
    	return new Repository();
    }
    
    private static void closeRepository() throws IOException {
    	
    }
}
