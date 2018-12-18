package com.revature.project_0;

import java.io.IOException;

import com.revature.project_0.repository.Repository;
import com.revature.project_0.view.ConsoleView;

public class App 
{
    public static void main( String[] args )
    {
        try {
			new ConsoleView(connectToRepository()).goLive();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Repository is down!");
		} finally {
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
