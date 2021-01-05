package com.mycompany.serverhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;






public class JavaHTTPServer implements Runnable{ 
        static final String ROOTLOCALE="./files/";
        static final String ROOTESTERNA="";
	static final String ROOT = ROOTLOCALE;//CAMBIARE IN BASE DOVE AVVII IL PROGRAMMA
	static final File WEB_ROOT = new File(ROOT);
	static final String DEFAULT_FILE = "index.html";
	static final String FILE_NOT_FOUND = "404.html";
	static final String METHOD_NOT_SUPPORTED = "not_supported.html";
        static final String JSON = "puntiVendita.json";
        static final String XML = "puntiVendita.xml";
        static final String DB_JSON = "database.json";
        static final String DB_XML = "database.xml";
        
	// port to listen connection
	static final int PORT = 3000;
	
	// verbose mode
	static final boolean verbose = true;
	
	// Client Connection via Socket Class
	private Socket connect;
	
	public JavaHTTPServer(Socket c) {
		connect = c;
                
	}
	
	public static void main(String[] args) {
		try {
			ServerSocket serverConnect = new ServerSocket(PORT);
			System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
			
			// we listen until user halts server execution
			while (true) {
				JavaHTTPServer myServer = new JavaHTTPServer(serverConnect.accept());
				
				if (verbose) {
					System.out.println("Connecton opened. (" + new Date() + ")");
				}
				
				// create dedicated thread to manage the client connection
				Thread thread = new Thread(myServer);
				thread.start();
			}
			
		} catch (IOException e) {
			System.err.println("Server Connection error : " + e.getMessage());
		}
	}

	@Override
	public void run() {
		// we manage our particular client connection
		BufferedReader in = null; PrintWriter out = null; BufferedOutputStream dataOut = null;
		String fileRequested = null;
                ArrayList<PuntiVendita> arTMP;
                String content="";
		
		try {
			// we read characters from the client via input stream on the socket
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			// we get character output stream to client (for headers)
			out = new PrintWriter(connect.getOutputStream());
			// get binary output stream to client (for requested data)
			dataOut = new BufferedOutputStream(connect.getOutputStream());
			
			// get first line of the request from the client
			String input = in.readLine();
			// we parse the request with a string tokenizer
			StringTokenizer parse = new StringTokenizer(input);
			String method = parse.nextToken().toUpperCase(); // we get the HTTP method of the client
			// we get file requested
			fileRequested = parse.nextToken().toLowerCase();
			
			// we support only GET and HEAD methods, we check
			if (!method.equals("GET")  &&  !method.equals("HEAD")) {//se non e nessun metodo 
				if (verbose) {
					System.out.println("501 Not Implemented : " + method + " method.");
				}
				
				// we return the not supported file to the client
				File file = new File(WEB_ROOT, METHOD_NOT_SUPPORTED);
				int fileLength = (int) file.length();
				String contentMimeType = "text/html";
				//read content to return to client
				byte[] fileData = readFileData(file, fileLength);
					
				// we send HTTP Headers with data to client
				out.println("HTTP/1.1 501 Not Implemented");
				out.println("Server: Java HTTP Server from SSaurel : 1.0");
				out.println("Date: " + new Date());
				out.println("Content-type: " + contentMimeType);
				out.println("Content-length: " + fileLength);
				out.println(); // blank line between headers and content, very important !
				out.flush(); // flush character output stream buffer
				// file
				dataOut.write(fileData, 0, fileLength);
				dataOut.flush();
				
			} else {
				// GET or HEAD method
                                boolean moved=false;
                                File filetmp;
                                String strTMP;
                                if (!fileRequested.endsWith("/") && !fileRequested.endsWith(".html") && !fileRequested.endsWith(".json") && !fileRequested.endsWith(".xml") ) {//controllo
					fileRequested += "/";
                                        
				}
				if (fileRequested.endsWith("/") && !fileRequested.endsWith("xml/") && !fileRequested.endsWith("json/") ) {//controllo
					fileRequested += DEFAULT_FILE;
                                        
				}
                                
                                if(!fileRequested.endsWith(".html") && !fileRequested.endsWith("/") && !fileRequested.endsWith(".json") && !fileRequested.endsWith(".xml"))
                                {
                                    String fileRequestedTMP;
                                    fileRequestedTMP = fileRequested+".html";
                                    if(controlloEssistenzaFile(fileRequestedTMP) == false)fileRequestedTMP = fileRequested+"/";//CONTROLLO SE E DI TIPO .HTML
                                    
                                    fileRequested=fileRequestedTMP;
                                    moved=true;
                                
                                
                                }
                                
                                if(fileRequested.endsWith(".json") || fileRequested.endsWith(".xml"))
                                {
                                    
                                    
                                    if(fileRequested.endsWith(".json"))
                                    {
                                        String path = "./files/"+fileRequested;
                                        

                                            
                                           
                                        ObjectMapper objectMapper = new ObjectMapper();
                                        
                                        PuntiVendita pv = objectMapper.readValue(new File(WEB_ROOT+"/"+JSON), PuntiVendita.class);//deserializzazione 
                                        objToXML(pv);
                                    }
                                }
                                
                                if(fileRequested.endsWith("/db/xml/") || fileRequested.endsWith("/db/json/"))
                                {
                                    
                                    System.out.println("request :"+fileRequested);
                                            
                                    salvaDB("jdbc:mysql://127.0.0.1:3306/tpsit?user=root&password=Password&serverTimezone=Europe/Rome",fileRequested);
                                    if(fileRequested.endsWith("/db/xml/"))
                                    {
                                        fileRequested += DB_XML;
                                    }
                                    else
                                    {
                                        fileRequested += DB_JSON;
                                    }
                                }
				
				File file = new File(WEB_ROOT, fileRequested);
                                // prende la lungheza del file 
				int fileLength = (int) file.length();
                                // out di controllo per la request 
                                System.out.println("file request: "+fileRequested);
                                
			        content = getContentType(fileRequested);
				
				if (method.equals("GET")) { // GET method so we return content
					byte[] fileData = readFileData(file, fileLength);
					
                                        if(moved==true)
                                        {
                                            out.println("HTTP/1.1 301 moved permamently");
                                            out.println("Server: Java HTTP Server from SSaurel : 1.0");
                                            out.println("Date: " + new Date());
                                            out.println("Content-type: " + content);
                                            out.println("Content-length: " + fileLength);
                                            out.println(); // blank line between headers and content, very important !
                                            out.flush(); // flush character output stream buffer
                                        }
                                        else
                                        {
                                            // send HTTP Headers
                                            out.println("HTTP/1.1 200 OK");
                                            out.println("Server: Java HTTP Server from SSaurel : 1.0");
                                            out.println("Date: " + new Date());
                                            out.println("Content-type: " + content);
                                            out.println("Content-length: " + fileLength);
                                            out.println(); // blank line between headers and content, very important !
                                            out.flush(); // flush character output stream buffer

                                            dataOut.write(fileData, 0, fileLength);
                                            dataOut.flush();
                                        }
				}
                                
				
				if (verbose) {
					System.out.println("File " + fileRequested + " of type " + content + " returned");
				}
				
			}
			
		} catch (FileNotFoundException fnfe) {
			try {
				fileNotFound(out, dataOut, fileRequested);
			} catch (IOException ioe) {
				System.err.println("Error with file not found exception : " + ioe.getMessage());
			}
			
		} catch (IOException ioe) {
			System.err.println("Server error : " + ioe);
		} finally {
			try {
				in.close();
				out.close();
				dataOut.close();
				connect.close(); // we close socket connection
			} catch (Exception e) {
				System.err.println("Error closing stream : " + e.getMessage());
			} 
			
			if (verbose) {
				System.out.println("Connection closed.\n");
			}
		}
		
		
	}
	
	private byte[] readFileData(File file, int fileLength) throws IOException {
		FileInputStream fileIn = null;
		byte[] fileData = new byte[fileLength];
		
		try {
			fileIn = new FileInputStream(file);
			fileIn.read(fileData);
		} finally {
			if (fileIn != null) 
				fileIn.close();
		}
		
		return fileData;
	}
	
	// return supported MIME Types
	private String getContentType(String fileRequested) {
		if (fileRequested.endsWith(".htm")  ||  fileRequested.endsWith(".html"))
			return "text/html";
                else if(fileRequested.endsWith("xml/"))
                {
                    return "text/xml";
                }
                else if(fileRequested.endsWith("json/"))
                {
                    return "text/json";
                }
                else
                {
                    return "text/plain";
                }
			
	}
        
        private boolean controlloEssistenzaFile(String fileD){
            
                List<String> results = new ArrayList<String>();
                File[] filesTMP = new File(ROOT).listFiles();
                String[] StrTmp;
                
                for (File file : filesTMP) {
                    if (file.isFile()) {
                        results.add("/"+file.getName());//salvo tutto dentro un arraylist per comodita
                        
                    }
                }
                
                for(int i=0;i<results.size();i++)
                {
                    
                   
                    if(results.get(i).equals(fileD))
                    {
                        return true;
                    }
                    
                }
                
                            
            return false;
        }
        
        private boolean controlloEssistenzaCartella(String fileD){
            
            List<String> results = new ArrayList<String>();
            String[] filesTMP =  new File(ROOT).list();
                
            for(int i=0;i<filesTMP.length;i++)
            {
                results.add(filesTMP[i]);
            }
            
            for(int i=0;i<results.size();i++)
            {
                    
                    
                if(results.get(i).equals(fileD))
                {
                    return true;
                }
                    
            }
                
                
            return false;
        }
        
        private void objToXML(Object obj)
        {
            if(!obj.getClass().getName().endsWith("PuntiVendita"))
            {
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.writeValue(new File(WEB_ROOT+"/db/xml/"+XML),obj);
                    File file = new File(WEB_ROOT+"/db/xml/"+XML);
                } catch (IOException ex) {
                    Logger.getLogger(JavaHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.writeValue(new File(WEB_ROOT+"/"+XML),obj);
                    File file = new File(WEB_ROOT+"/"+XML);
                } catch (IOException ex) {
                    Logger.getLogger(JavaHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
        
        private void salvaDB(String db,String request)
        {
            
            String urlMyDB = db;
            try 
            {
            
            try {
                
                Class.forName("com.mysql.jdbc.Driver");
            
                String content;
                Connection connessione =DriverManager.getConnection(urlMyDB);
                
                
                Statement statemant = connessione.createStatement();
                
                ResultSet resultset = statemant.executeQuery("SELECT Studenti.* FROM Studenti");
                
                System.out.println("connesso al database");
                
                
                ArrayList<Studente>studenti=new ArrayList<>();
                while(resultset.next())//controllo di tutto sql
                {
                    //salvataggio dei dati di ogni riga 
                    Studente s = new Studente(resultset.getString(3),resultset.getString(2),resultset.getInt(1));
                    //salvo la riga dentro un array
                    studenti.add(s);
                    
                    
                    
                }
                System.out.println("Studenti salvati dentro array"+studenti.toString());
                
                if(request.endsWith("/db/xml/"))
                {
                    
                    XmlMapper xml = new XmlMapper();
                    try {
                        xml.writeValue(new File(WEB_ROOT + "/db/xml/" + DB_XML), studenti);
                    } catch (IOException ex) {
                        Logger.getLogger(JavaHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    File file = new File(WEB_ROOT + "/" + DB_XML);
                }
                if(request.endsWith("/db/json/"))
                {
                    
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        objectMapper.writeValue(new File(WEB_ROOT + "/db/json/" + DB_JSON), studenti);
                    } catch (IOException ex) {
                        Logger.getLogger(JavaHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    File file = new File(WEB_ROOT + "/" + DB_JSON);
                }
                        
                
                
            } catch (SQLException ex) {
                
                Logger.getLogger(JavaHTTPServer.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
        catch(ClassNotFoundException e)
        {
            
                    
        }
        }
        
        
	private void fileNotFound(PrintWriter out, OutputStream dataOut, String fileRequested) throws IOException {
		File file = new File(WEB_ROOT, FILE_NOT_FOUND);
		int fileLength = (int) file.length();
		String content = "text/html";
		byte[] fileData = readFileData(file, fileLength);
		
		out.println("HTTP/1.1 404 File Not Found");
		out.println("Server: Java HTTP Server from SSaurel : 1.0");
		out.println("Date: " + new Date());
		out.println("Content-type: " + content);
		out.println("Content-length: " + fileLength);
		out.println(); // blank line between headers and content, very important !
		out.flush(); // flush character output stream buffer
		
		dataOut.write(fileData, 0, fileLength);
		dataOut.flush();
		
		if (verbose) {
			System.out.println("File " + fileRequested + " not found");
		}
	}
	
}