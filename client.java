
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class Client{
    //Declare a socket and it's i/o streams
    private Socket socket = null; 
    private BufferedReader in = null;
    private DataOutputStream out = null;

    /*
        The client will need an IP and a PORT to communicate 
        with the server and send requests, add them to the 
        constructor parameters
    */
    public Client(String addr, int port){
        try{//Attempt to establish a connection to the server
                
            //by opening a TCP socket connection
            socket = new Socket(addr,port);

            //Let the user know that we have connected to the server on the specified address and port
            System.out.printf("Connected to the server: %s:%d\n\n\n",socket.getInetAddress().toString(),socket.getPort());
            System.out.println("Type: \"Over\" when you want to close the connection to the server.");
            System.out.print("TELL SERVER: ");

            //Set the input stream to point to the System input stream (terminal input)
            // in = new DataInputStream(System.in);
            in = new BufferedReader(new InputStreamReader(System.in));


            //Set the output stream to the socket's output stream
            out = new DataOutputStream(socket.getOutputStream());    

            //You need a string to read the incoming message from the input data stream
            String incomingValue = "";

            //Keep reading until the incomingValue is equal to "Over"
            while(!incomingValue.equals("Over")){
                try{ 
                    incomingValue = in.readLine();
                    out.writeUTF(incomingValue);
                }catch(Exception err){
                    System.out.println(err.getMessage());
                }
            }

            
            //Attempt to close the socket connection   
            //By closing the input data stream
            in.close(); 
            //Closing the output data stream
            out.close();
            //Closing the socket connection itself
            socket.close();
        }catch(Exception err){ //Get the exception message
            System.out.println(err.getMessage()); //Print it out to the screen
        }
    }

    public static void main(String[] args){
        //Open a client socket connection to localhost on port 5000
        Client client = new Client("127.0.0.1",5005);

    }
}
